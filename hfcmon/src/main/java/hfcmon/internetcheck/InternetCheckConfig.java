package hfcmon.internetcheck;

import hfcmon.utils.PropertiesHelper;
import hfcmon.utils.UnexpectedException;

public final class InternetCheckConfig {

    private static final String PREFIX = "internetcheck.";

    // Default values for properties
    private static final boolean ENABLED = true;
    private static final int PING_TIMEOUT = 500;
    private static final String[] PING_DOMAINS = new String[] {
            "www.google.com", "www.google.com.au",
            "commbank.com.au", "www.commbank.com.au",
            "cloudflare.com", "www.cloudflare.com"
    };
    private static final boolean BEEP = true;

    // Actual values of properties
    public boolean enabled = ENABLED;

    public int pingTimeout = PING_TIMEOUT;
    public String[] pingDomains = PING_DOMAINS;
    public boolean beep = BEEP;

    public int checkIntervalMin = 1000;
    public int checkIntervalIncrement = 1000;
    public int checkIntervalMax = 10000;

    /**
     * If something completely unexpected happens in the event loop, wait this time before trying again.
     * 
     * Note: this should never happen. If it does happen it implies an application bug.
     */
    public int unexpectedExceptionRetryInterval = 60000; // Milliseconds

    public void save(PropertiesHelper properties) {
        properties.setProperty(PREFIX + "enabled", enabled);

        properties.setProperty(PREFIX + "pingTimeout", pingTimeout);
        properties.setProperty(PREFIX + "pingDomains", pingDomains);
        properties.setProperty(PREFIX + "beep", beep);

        properties.setProperty(PREFIX + "checkIntervalMin", checkIntervalMin);
        properties.setProperty(PREFIX + "checkIntervalIncrement", checkIntervalIncrement);
        properties.setProperty(PREFIX + "checkIntervalMax", checkIntervalMax);

        properties.setProperty(PREFIX + "unexpectedExceptionRetryInterval", unexpectedExceptionRetryInterval);
    }

    public void load(PropertiesHelper properties) {
        enabled = properties.getBooleanProperty(PREFIX + "enabled", ENABLED);

        pingTimeout = properties.getIntProperty(PREFIX + "pingTimeout", PING_TIMEOUT);
        pingDomains = properties.getStringArrayProperty(PREFIX + "pingDomains", PING_DOMAINS);
        beep = properties.getBooleanProperty(PREFIX + "beep", BEEP);

        checkIntervalMin = properties.getIntProperty(PREFIX + "checkIntervalMin", 1000);
        checkIntervalIncrement = properties.getIntProperty(PREFIX + "checkIntervalIncrement", 1000);
        checkIntervalMax = properties.getIntProperty(PREFIX + "checkIntervalMax", 10000);

        unexpectedExceptionRetryInterval = properties.getIntProperty(PREFIX + "unexpectedExceptionRetryInterval", 60000);

        if (pingTimeout < 500) {
            throw new UnexpectedException("Timeout must be 500ms or more");
        } else if (pingDomains == null || pingDomains.length < 3) {
            throw new UnexpectedException("Need 3 or more domains");
        }
    }

}
