package hfcmon.output;

import java.io.Closeable;

import hfcmon.modem.model.InternetOutage;

public interface OutputOutage extends Closeable {

    void output(InternetOutage outage);

}
