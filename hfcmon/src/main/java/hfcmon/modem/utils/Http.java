package hfcmon.modem.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import hfcmon.Constants;
import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.Utils;

/**
 * Very Simple Http Client.
 * 
 * Has the following unusual features;
 *   (a) Always keeps a socket connected to the cable modem - always creates a new/spare socket before using the old one
 *        (this is done so that the cable modem doesn't shutdown the webserver).
 *   (b) Will try indefinately to connect to the cable modem until it successfully connects.
 */
public final class Http implements Closeable {

    private final Logger logger;

    private final String host;
    private final int connectTimeout;
    private final int connectAttempts;
    private final int readTimeout;

    private final String urlPrefix;

    private Socket socket; // Always a socket here
    private final InetSocketAddress address;
    private char[] buffer = !Constants.PRINT_RESPONSE_TO_STDOUT_WHILE_READ ? new char[Constants.BUFFER_SIZE] : null;

    /**
     * @param connectTimeout Keep this pretty short - because the create socket method will keep trying constantly.
     * @param readTimeout Needs to be at least 4000-5000ms - pages typically take this long to load.
     */
    public Http(LoggerFactory factory, String host, int port, int connectTimeout, int connectAttempts, int readTimeout) {
        this.logger = factory.getLogger(this);

        this.host = host;
        this.connectTimeout = connectTimeout;
        this.connectAttempts = connectAttempts;
        this.readTimeout = readTimeout;

        if (port == 80) {
            this.urlPrefix = "http://" + host;
        } else {
            this.urlPrefix = "http://" + host + ":" + port;
        }

        this.address = new InetSocketAddress(host, port);
    }

    public String getUrl() {
        return urlPrefix;
    }

    public String getUrl(String path) {
        assert path != null && path.length() > 0 && path.startsWith("/");
        return urlPrefix + path;
    }

    public HttpRequest getRequest(String path) {
        String url = getUrl(path);
        String request = "GET " + path + " HTTP/1.1\r\n"
                + "Host: " + host + "\r\n"
                //+ "Connection: keep-alive\r\n" <-- Note: tried keep-alive, but the cable modem doesn't respect it.
                + "\r\n";
        return new HttpRequest(logger, this, url, request.getBytes(StandardCharsets.UTF_8));
    }

    public void init() {
        assert socket == null;

        // Create new socket to cable modem - trying indefinately
        this.socket = createSocket_UnlimitedAttempts();
    }

    @Override
    public void close() {
        Socket socket = this.socket;
        if (socket != null) {
            close(socket);
            this.socket = null;
        }
    }

    protected void get(byte[] request, StringBuilder response) throws IOException, HttpConnectException {
        // Clear response buffer
        response.setLength(0);

        // Send request
        Socket socket = getSocket();
        try {
            // Write request
            OutputStream out = socket.getOutputStream();
            out.write(request);
            out.flush();

            // Read response
            if (!Constants.PRINT_RESPONSE_TO_STDOUT_WHILE_READ) { // More efficient way to read
                int read;
                char[] buffer = this.buffer;
                InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                while ((read = reader.read(buffer, 0, buffer.length)) >= 0) {
                    response.append(buffer, 0, read);
                }
            } else { // Less efficient to read line by line - more memory allocation/work ;-)
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    System.out.println("Response: " + line);
                }
            }
        } finally {
            close(socket);
        }
    }

    private Socket getSocket() throws HttpConnectException {
        Socket socket = this.socket;
        assert socket != null;
        this.socket = createSocket_LimitedAttempts(connectAttempts);
        return socket;
    }

    private Socket createSocket_UnlimitedAttempts() {
        for (;;) {
            long startTime = System.currentTimeMillis();
            Socket socket = createSocketOrNull();
            if (socket != null) {
                return socket;
            }

            // Wait if timeout time not reached (don't want to fill up log!)
            long totalTime = System.currentTimeMillis() - startTime;
            if (totalTime < connectTimeout) {
                Utils.sleep(logger, connectTimeout - totalTime);
            }
        }
    }

    private Socket createSocket_LimitedAttempts(final int maxAttempts) throws HttpConnectException {
        int attempt = 0;
        for (;;) {
            long startTime = System.currentTimeMillis();
            Socket socket = createSocketOrNull();
            if (socket != null) {
                return socket;
            } else if (attempt++ >= maxAttempts) {
                throw new HttpConnectException("Failed to connect to \"" + urlPrefix + "\" after " + maxAttempts);
            }

            // Wait if timeout time not reached (don't want to fill up log!)
            long totalTime = System.currentTimeMillis() - startTime;
            if (totalTime < connectTimeout) {
                Utils.sleep(logger, connectTimeout - totalTime);
            }
        }
    }

    private Socket createSocketOrNull() {
        Socket socket = new Socket();
        try {
            socket.setSoTimeout(readTimeout);
            socket.connect(address, connectTimeout);
        } catch (Throwable e) {
            logger.warn("Failed to connect to \"" + urlPrefix + "\"; " + e);

            // Close socket
            close(socket);
            return null;
        }
        return socket;
    }

    private void close(Socket socket) {
        assert socket != null;
        if (socket != null) {
            try {
                socket.close();
            } catch (Throwable e) {
                logger.warn("Failed to close socket", e);
            }
        }
    }

}