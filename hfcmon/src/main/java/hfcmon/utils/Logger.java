package hfcmon.utils;

public interface Logger extends LoggerFactory {

    void info(String message);

    void warn(String message);

    void error(String message);

    void info(String message, Throwable e);

    void warn(String message, Throwable e);

    void error(String message, Throwable e);

}
