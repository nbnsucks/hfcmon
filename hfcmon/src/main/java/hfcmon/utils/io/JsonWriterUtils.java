package hfcmon.utils.io;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import hfcmon.Constants;
import hfcmon.utils.Logger;
import hfcmon.utils.UnexpectedException;
import hfcmon.utils.Utils;

public final class JsonWriterUtils {

    public static JsonWriter getJsonWriter(StringBuilder buffer) {
        return new JsonWriterImpl_StringBuilder(buffer, Constants.BUFFER_SIZE);
    }

    public static JsonWriter getJsonWriterOrNull(String path) {
        path = Utils.trimToNull(path);
        if (path == null) {
            return null;
        }
        File file = new File(path);
        try {
            Writer writer = Utils.getWriter(file, true);
            return new JsonWriterImpl_Writer(file.getAbsolutePath(), writer, Constants.BUFFER_SIZE);
        } catch (IOException e) {
            throw new UnexpectedException("Failed to create/open file \"" + path + "\"", e);
        }
    }

    private static final class JsonWriterImpl_StringBuilder extends JsonWriterImpl {
        private final StringBuilder buffer;

        protected JsonWriterImpl_StringBuilder(StringBuilder buffer, int bufferSize) {
            super(bufferSize);
            this.buffer = buffer;
        }

        @Override
        public String getFilename() {
            return null;
        }

        @Override
        protected void _write(char[] array, int length) {
            buffer.append(array, 0, length);
        }

        @Override
        protected void _flush() {
            // Do nothing
        }

        @Override
        protected void _close(Logger logger) {
            // Do nothing
        }
    }

    private static final class JsonWriterImpl_Writer extends JsonWriterImpl {
        private final String filename;

        private Writer writer;

        protected JsonWriterImpl_Writer(String filename, Writer writer, int bufferSize) {
            super(bufferSize);
            assert writer != null;
            this.writer = writer;
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        protected void _write(char[] array, int length) throws IOException {
            writer.write(array, 0, length);
        }

        @Override
        protected void _flush() throws IOException {
            writer.flush();
        }

        @Override
        protected void _close(Logger logger) {
            Writer writer = this.writer;
            if (writer != null) {
                this.writer = null;
                try {
                    writer.close();
                } catch (Throwable e) {
                    logger.warn("Failed to cleanly close writer used by " + getClass().getName(), e);
                }
            }
        }
    }

}
