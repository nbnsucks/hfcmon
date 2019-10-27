package hfcmon.utils.io;

import java.io.IOException;

public interface JsonWriter extends CloseableWithLogger {

    /* *********************************************** */
    // General methods

    /**
     * Outputs ",\r\n" and flushes this - maybe not the best overall approach, but it's simple and easy to manually then construct valid JSON from.
     */
    void nextRecord() throws IOException;

    void ensureDone() throws IOException;

    void flush() throws IOException;

    /* *********************************************** */
    // Json open/close methods

    void openObject() throws IOException;

    void openObject(String name) throws IOException;

    void closeObject() throws IOException;

    void openArray() throws IOException;

    void openArray(String name) throws IOException;

    void closeArray() throws IOException;

    /* *********************************************** */
    // Json methods

    void write(String name, String value) throws IOException;

    void write(String name, long value) throws IOException;

    void write(String name, double value) throws IOException;

    void write(String name, boolean value) throws IOException;

    /* *********************************************** */
}
