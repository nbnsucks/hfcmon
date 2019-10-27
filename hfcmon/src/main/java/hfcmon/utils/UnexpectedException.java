package hfcmon.utils;

public final class UnexpectedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(String message, Throwable e) {
        super(message, e);
    }

}
