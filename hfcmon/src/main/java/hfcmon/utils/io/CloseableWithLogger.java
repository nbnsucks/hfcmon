package hfcmon.utils.io;

import hfcmon.utils.Logger;

public interface CloseableWithLogger {

    /**
     * @return Filename being written to.
     */
    String getFilename();

    /**
     * Close method that never throws exception.
     * 
     * If exception must be thrown, should be logged to the logger provided.
     */
    void close(Logger logger);

}
