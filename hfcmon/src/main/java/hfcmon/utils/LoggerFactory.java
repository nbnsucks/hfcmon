package hfcmon.utils;

public interface LoggerFactory {

    Logger getLogger(Class<?> clazz);

    Logger getLogger(Object object);

}
