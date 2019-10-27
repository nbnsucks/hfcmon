package hfcmon.modem.utils;

import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;

public final class HttpRequest {

    private final Logger logger;

    private final Http http;
    private final String url;
    private final byte[] request;

    protected HttpRequest(LoggerFactory factory,
            Http http, String url, byte[] request) {
        this.logger = factory.getLogger(this);

        this.http = http;
        this.url = url;
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public boolean get(StringBuilder response) throws HttpConnectException {
        try {
            http.get(request, response);
        } catch (HttpConnectException e) {
            throw e;
        } catch (Throwable e) {
            logger.warn("Failed to read \"" + url + "\"", e);
            return false;
        }
        return true;
    }

}
