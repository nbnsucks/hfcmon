package hfcmon.output;

import java.io.Closeable;

import hfcmon.modem.model.ModemState;

public interface OutputState extends Closeable {

    /**
     * Must be called before first call to output method.
     */
    void start();

    void output(ModemState state);

    @Override
    void close();

}
