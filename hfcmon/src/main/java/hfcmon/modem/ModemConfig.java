package hfcmon.modem;

import hfcmon.utils.PropertiesHelper;

public final class ModemConfig {

    private static final String PREFIX = "modem.";

    public boolean enabled = true;

    public String host = "192.168.100.1";
    public int port = 80;

    /**
     * Keep this pretty short - because the create socket method will keep trying constantly.
     */
    public int connectTimeout = 2000; // Milliseconds

    /**
     * How many connect attempts the application should make before breaking out of the event loop.
     */
    public int connectAttempts = 3;

    /**
     * Needs to be at least 4000-5000ms - pages typically take this long to load.
     */
    public int readTimeout = 10000; // Milliseconds

    /**
     * Most frequent interval that the cable modem should be queried.
     */
    public int checkInterval = 15000; // Milliseconds

    /**
     * If something completely unexpected happens in the event loop, wait this time before trying again.
     * 
     * Note: this should never happen. If it does happen it implies an application bug.
     */
    public int unexpectedExceptionRetryInterval = 60000; // Milliseconds

    /**
     * Status/Connection path.
     * 
     * This page includes the "Startup Procedure", "Downstream Bonded Channels" and "Upstream Bonded Channels" sections.
     * 
     * Should usually be set to "/" or "/cmstatus.html"
     */
    public String statusPath = "/";

    /**
     * Event Log path.
     */
    public String eventLogPath = "/cmeventlog.html";

    public void save(PropertiesHelper properties) {
        properties.setProperty(PREFIX + "enabled", enabled);

        properties.setProperty(PREFIX + "host", host);
        properties.setProperty(PREFIX + "port", port);
        properties.setProperty(PREFIX + "connectTimeout", connectTimeout);
        properties.setProperty(PREFIX + "connectAttempts", connectAttempts);
        properties.setProperty(PREFIX + "readTimeout", readTimeout);
        properties.setProperty(PREFIX + "checkInterval", checkInterval);

        properties.setProperty(PREFIX + "unexpectedExceptionRetryInterval", unexpectedExceptionRetryInterval);

        properties.setProperty(PREFIX + "statusPath", statusPath);
        properties.setProperty(PREFIX + "eventLogPath", eventLogPath);
    }

    public void load(PropertiesHelper properties) {
        enabled = properties.getBooleanProperty(PREFIX + "enabled", true);

        host = properties.getProperty(PREFIX + "host", "192.168.100.1");
        port = properties.getIntProperty(PREFIX + "port", 80);
        connectTimeout = properties.getIntProperty(PREFIX + "connectTimeout", 2000);
        connectAttempts = properties.getIntProperty(PREFIX + "connectAttempts", 3);
        readTimeout = properties.getIntProperty(PREFIX + "readTimeout", 10000);
        checkInterval = properties.getIntProperty(PREFIX + "checkInterval", 15000);

        unexpectedExceptionRetryInterval = properties.getIntProperty(PREFIX + "unexpectedExceptionRetryInterval", 60000);

        statusPath = properties.getProperty(PREFIX + "statusPath", "/");
        eventLogPath = properties.getProperty(PREFIX + "eventLogPath", "/cmeventlog.html");
    }

}
