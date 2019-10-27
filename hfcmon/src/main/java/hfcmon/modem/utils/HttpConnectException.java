package hfcmon.modem.utils;

public final class HttpConnectException extends Exception {

    private static final long serialVersionUID = 1L;

    public HttpConnectException(String message) {
        super(message);
    }

    public HttpConnectException(String message, Throwable e) {
        super(message, e);
    }

}
