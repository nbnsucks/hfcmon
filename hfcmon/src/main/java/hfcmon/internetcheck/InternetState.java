package hfcmon.internetcheck;

import hfcmon.modem.model.InternetOutage;
import hfcmon.modem.model.ModemState;

/**
 * State shared between the Modem Check Thread and the Internet Check Thread.
 */
public final class InternetState {

    private InternetOutage outage;

    public synchronized boolean isInternetConnected(ModemState state) {
        assert state != null;
        InternetOutage outage = this.outage;
        if (outage == null) {
            return true;
        } else {
            outage.addEventLogs(state);
            return false;
        }
    }

    public synchronized void onInternetDisconnected(InternetOutage outage) {
        assert this.outage == null && outage != null;
        this.outage = outage;
    }

    public synchronized void onInternetConnected() {
        assert this.outage != null;
        this.outage = null;
    }

}
