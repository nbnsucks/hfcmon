package hfcmon.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import hfcmon.Constants;
import hfcmon.utils.Logger;
import hfcmon.utils.UnexpectedException;
import hfcmon.utils.Utils;

public final class CellWriterUtils {

    public static CellWriter getCellWriter(StringBuilder buffer) {
        return new CellWriterImpl(null, buffer);
    }

    public static CellWriter getCellWriterOrNull(String path, CellHeadingWriter heading) {
        path = Utils.trimToNull(path);
        if (path == null) {
            return null;
        }
        File file = new File(path);
        boolean exists = file.exists();
        try {
            Writer writer = Utils.getWriter(file, true);
            CellWriter cellWriter = new CellWriterImpl(file.getAbsolutePath(), writer);

            // Output heading row if creating file
            if (!exists) {
                heading.writeHeading(cellWriter);
                cellWriter.newline();
            }
            return cellWriter;
        } catch (IOException e) {
            throw new UnexpectedException("Failed to create/open file \"" + path + "\"", e);
        }
    }

    public static interface CellHeadingWriter {
        void writeHeading(CellWriter writer) throws IOException;
    }

    private static final class CellWriterImpl implements CellWriter {
        private Appendable out;
        private final String filename;

        private boolean isFirst = true;
        private SimpleDateFormat dateFormat;

        protected CellWriterImpl(String filename, Appendable out) {
            assert out != null;
            this.out = out;
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        public void close(Logger logger) {
            Appendable out = this.out;
            if (out != null && out instanceof Closeable) {
                this.out = null;
                try {
                    ((Closeable) out).close();
                } catch (Throwable e) {
                    logger.warn("Failed to cleanly close writer used by " + getClass().getName(), e);
                }
            }
        }

        private void comma() throws IOException {
            if (isFirst) {
                isFirst = false;
            } else {
                out.append(',');
            }
        }

        @Override
        public CellWriter write() throws IOException {
            comma();
            return this;
        }

        @Override
        public CellWriter write(String value) throws IOException {
            comma();
            append(value, out);
            return this;
        }

        @Override
        public CellWriter writeTimestamp(long timestamp) throws IOException {
            comma();

            SimpleDateFormat dateFormat = this.dateFormat;
            if (dateFormat == null) {
                this.dateFormat = dateFormat = new SimpleDateFormat(Constants.CSV_DATE_FORMAT);
            }

            out.append(dateFormat.format(new Date(timestamp)));
            return this;
        }

        @Override
        public CellWriter write(long value) throws IOException {
            comma();
            out.append(Long.toString(value));
            return this;
        }

        @Override
        public CellWriter write(double value) throws IOException {
            comma();
            out.append(Double.toString(value));
            return this;
        }

        @Override
        public void newline() throws IOException {
            isFirst = true;
            out.append(Constants.NEW_LINE);
            if (out instanceof Flushable) {
                ((Flushable) out).flush();
            }
        }

        private static void append(String value, Appendable out) throws IOException {
            if (value == null) {
                return;
            }
            int length = value.length();
            if (length <= 0) {
                return;
            }

            // Check if need to quote
            boolean isQuote = false;
            int index = 0;
            do {
                char c = value.charAt(index);
                if (!Character.isLetterOrDigit(c) && c != ' ') {
                    isQuote = true;
                    break;
                }
            } while (++index < length);

            // Append string
            if (!isQuote) {
                out.append(value);
            } else {
                out.append('"');
                index = 0;
                do {
                    char c = value.charAt(index);
                    if (c == '"') {
                        out.append("\"\"");
                    } else {
                        out.append(c);
                    }
                } while (++index < length);
                out.append('"');
            }
        }
    }

}
