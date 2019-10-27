package hfcmon.utils.io;

import java.io.IOException;

public interface CellWriter extends CloseableWithLogger {

    /**
     * Writes an empty cell.
     */
    public CellWriter write() throws IOException;

    public CellWriter write(String value) throws IOException;

    public CellWriter writeTimestamp(long timestamp) throws IOException;

    public CellWriter write(long value) throws IOException;

    public CellWriter write(double value) throws IOException;

    public void newline() throws IOException;

}
