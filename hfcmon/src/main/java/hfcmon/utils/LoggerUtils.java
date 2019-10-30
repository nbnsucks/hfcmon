package hfcmon.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import hfcmon.Constants;

public final class LoggerUtils {

    public static LoggerFactory getStdOutLoggerFactory() {
        return new LoggerFactoryImpl_StdOut();
    }

    public static LoggerFactory getLoggerFactory(String path, boolean isLogToStdOut, Logger defaultLogger) {
        assert defaultLogger != null;
        path = Utils.trimToNull(path);
        if (path == null) {
            return isLogToStdOut ? new LoggerFactoryImpl_StdOut() : new LoggerFactoryImpl_DevNull();
        }
        File file = new File(path);
        Writer writer;
        try {
            writer = Utils.getWriter(file, true);
        } catch (FileNotFoundException e) {
            String message = "Failed to open log file \"" + file.getAbsolutePath() + "\"";
            defaultLogger.error(message, e);
            throw new UnexpectedException(message, e);
        }
        return new LoggerFactoryImpl_Writer(writer, isLogToStdOut, defaultLogger, file.getAbsolutePath());
    }

    private static abstract class LoggerFactoryImpl implements LoggerFactory {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private final StringBuilder buffer = new StringBuilder();

        @Override
        public Logger getLogger(Class<?> clazz) {
            return new LoggerImpl(clazz, this);
        }

        @Override
        public Logger getLogger(Object object) {
            assert object != null;
            return new LoggerImpl(object.getClass(), this);
        }

        protected abstract void outputLog(StringBuilder buffer);

        private synchronized void log(Class<?> clazz, String level, String message, Throwable e) {
            String date = dateFormat.format(new Date());
            String logger = clazz != null ? clazz.getSimpleName() : "";

            StringBuilder buffer = this.buffer;
            buffer.setLength(0);
            buffer.append('[')
                    .append(level).append('|')
                    .append(date).append('|')
                    .append(logger).append("] ").append(message);
            if (e != null) {
                buffer.append(Constants.NEW_LINE);
                append(e, buffer);
            }

            outputLog(buffer);
        }

        private static void append(Throwable e, StringBuilder buffer) {
            assert e != null;
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            buffer.append(writer.getBuffer());
        }
    }

    private static final class LoggerFactoryImpl_StdOut extends LoggerFactoryImpl {
        @Override
        protected void outputLog(StringBuilder buffer) {
            System.out.println(buffer);
        }
    }

    private static final class LoggerFactoryImpl_DevNull extends LoggerFactoryImpl {
        @Override
        protected void outputLog(StringBuilder buffer) {
            // Do nothing
        }
    }

    private static final class LoggerFactoryImpl_Writer extends LoggerFactoryImpl {
        private final Writer writer;
        private final boolean isLogToStdOut;

        private final Logger defaultLogger;
        private final String filename;

        protected LoggerFactoryImpl_Writer(Writer writer, boolean isLogToStdOut,
                Logger defaultLogger, String filename) {
            this.writer = writer;
            this.isLogToStdOut = isLogToStdOut;

            this.defaultLogger = defaultLogger;
            this.filename = filename;
        }

        @Override
        protected void outputLog(StringBuilder buffer) {
            if (isLogToStdOut) {
                System.out.println(buffer);
            }
            buffer.append(Constants.NEW_LINE);
            try {
                writer.append(buffer);
                writer.flush();
            } catch (Throwable e) {
                defaultLogger.error("Failed to log file \"" + filename + "\"; log=" + buffer, e);
            }
        }
    }

    private static final class LoggerImpl implements Logger {
        private final Class<?> clazz;
        private final LoggerFactoryImpl factory;

        protected LoggerImpl(Class<?> clazz, LoggerFactoryImpl factory) {
            assert clazz != null && factory != null;
            this.clazz = clazz;
            this.factory = factory;
        }

        @Override
        public Logger getLogger(Class<?> clazz) {
            return factory.getLogger(clazz);
        }

        @Override
        public Logger getLogger(Object object) {
            return factory.getLogger(object);
        }

        @Override
        public void info(String message) {
            factory.log(clazz, "INFO ", message, null);
        }

        @Override
        public void warn(String message) {
            factory.log(clazz, "WARN ", message, null);
        }

        @Override
        public void error(String message) {
            factory.log(clazz, "ERROR", message, null);
        }

        @Override
        public void info(String message, Throwable e) {
            factory.log(clazz, "INFO ", message, e);
        }

        @Override
        public void warn(String message, Throwable e) {
            factory.log(clazz, "WARN ", message, e);
        }

        @Override
        public void error(String message, Throwable e) {
            factory.log(clazz, "ERROR", message, e);
        }
    }

}
