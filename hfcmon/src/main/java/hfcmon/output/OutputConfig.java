package hfcmon.output;

import java.text.SimpleDateFormat;
import java.util.Date;

import hfcmon.Constants;
import hfcmon.utils.PropertiesHelper;

public final class OutputConfig {

    private static final String PREFIX = "output.";
    private static final String DATETIME_VAR = "${datetime}";

    private static final boolean LOG_TO_STDOUT = true;
    private static final String LOG_FILE = DATETIME_VAR + "-hfcmon.log";
    private static final String STATUS_CSV = DATETIME_VAR + "-cmstatus.csv";
    private static final String STATUS_JSON = DATETIME_VAR + "-cmstatus.json";
    private static final String EVENTLOG_CSV = DATETIME_VAR + "-cmeventlog.csv";
    private static final String EVENTLOG_JSON = DATETIME_VAR + "-cmeventlog.json";
    private static final String OUTAGE_CSV = "internet-outages.csv";
    private static final String OUTAGE_JSON = "internet-outages.json";

    public boolean logToStdOut = LOG_TO_STDOUT;
    private String logFile = LOG_FILE;

    private String statusCsv = STATUS_CSV;
    private String statusJson = STATUS_JSON;

    private String eventLogCsv = EVENTLOG_CSV;
    private String eventLogJson = EVENTLOG_JSON;

    private String outageCsv = OUTAGE_CSV;
    private String outageJson = OUTAGE_JSON;

    public void save(PropertiesHelper properties) {
        properties.setProperty(PREFIX + "logToStdOut", logToStdOut);
        properties.setProperty(PREFIX + "logFile", logFile);

        properties.setProperty(PREFIX + "statusCsv", statusCsv);
        properties.setProperty(PREFIX + "statusJson", statusJson);

        properties.setProperty(PREFIX + "eventLogCsv", eventLogCsv);
        properties.setProperty(PREFIX + "eventLogJson", eventLogJson);

        properties.setProperty(PREFIX + "outageCsv", outageCsv);
        properties.setProperty(PREFIX + "outageJson", outageJson);
    }

    public void load(PropertiesHelper properties) {
        logToStdOut = properties.getBooleanProperty(PREFIX + "logToStdOut", LOG_TO_STDOUT);
        logFile = properties.getProperty(PREFIX + "logFile", LOG_FILE);

        statusCsv = properties.getProperty(PREFIX + "statusCsv", STATUS_CSV);
        statusJson = properties.getProperty(PREFIX + "statusJson", STATUS_JSON);

        eventLogCsv = properties.getProperty(PREFIX + "eventLogCsv", EVENTLOG_CSV);
        eventLogJson = properties.getProperty(PREFIX + "eventLogJson", EVENTLOG_JSON);

        outageCsv = properties.getProperty(PREFIX + "outageCsv", OUTAGE_CSV);
        outageJson = properties.getProperty(PREFIX + "outageJson", OUTAGE_JSON);
    }

    public String getLogFile() {
        return format(System.currentTimeMillis(), logFile);
    }

    public String getStatusCsv(long now) {
        return format(now, statusCsv);
    }

    public String getStatusJson(long now) {
        return format(now, statusJson);
    }

    public String getEventLogCsv(long now) {
        return format(now, eventLogCsv);
    }

    public String getEventLogJson(long now) {
        return format(now, eventLogJson);
    }

    public String getOutageCsv(long now) {
        return format(now, outageCsv);
    }

    public String getOutageJson(long now) {
        return format(now, outageJson);
    }

    private static String format(long now, String filename) {
        if (filename == null) {
            return null;
        }
        String date = new SimpleDateFormat(Constants.FILE_DATE_FORMAT).format(new Date(now));
        return filename.replace(DATETIME_VAR, date);
    }

}
