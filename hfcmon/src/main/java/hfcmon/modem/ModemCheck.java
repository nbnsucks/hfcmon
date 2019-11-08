package hfcmon.modem;

import java.util.List;

import hfcmon.Constants;
import hfcmon.internetcheck.InternetState;
import hfcmon.modem.model.EventLog;
import hfcmon.modem.model.ModemState;
import hfcmon.modem.utils.Http;
import hfcmon.modem.utils.HttpConnectException;
import hfcmon.modem.utils.HttpRequest;
import hfcmon.output.OutputState;
import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.Utils;

public final class ModemCheck {

    public static void start(ModemConfig config, OutputState output, InternetState shared, LoggerFactory factory) {
        Logger logger = factory.getLogger(ModemCheck.class);
        for (;;) {
            try (Http http = new Http(factory, config.host, config.port, config.connectTimeout, config.connectAttempts, config.readTimeout)) {
                // Attempt to connect
                logger.info("Attempting to connect to \"" + http.getUrl() + "\"...");
                http.init();

                // If successfully connected, start regular event loop
                output.start();
                try {
                    eventLoop(config, output, shared, factory, logger, http);
                } catch (@SuppressWarnings("unused") HttpConnectException e) {
                    // Ignore exception - just loop to try again
                } finally {
                    output.close();
                }
            } catch (Throwable e) {
                int milliseconds = config.unexpectedExceptionRetryInterval;
                logger.error("Unexpected exception - waiting " + milliseconds + "ms before trying again", e);
                Utils.sleep(logger, milliseconds);
            }
        }
    }

    private static void eventLoop(ModemConfig config, OutputState output, InternetState shared, LoggerFactory factory,
            Logger logger, Http http) throws HttpConnectException {
        int checkInterval = config.checkInterval;
        HttpRequest statusPage = http.getRequest(config.statusPath);
        HttpRequest eventLogPage = http.getRequest(config.eventLogPath);
        StringBuilder response = new StringBuilder(Constants.BUFFER_SIZE);
        ModemState state = new ModemState();

        // Start periodically collecting status/event log pages and outputting to csv files
        boolean isFirst = true;
        long packetErrors = Long.MIN_VALUE;
        for (;;) {
            final long startTime = System.currentTimeMillis();
            long timeTakenToGetStatusPage, timeTakenToGetEventLog;
            boolean hasSuccess = false;
            state.actualTime = startTime;

            // Get status page (i.e. downstream/upstream dBmV/db values)
            if (statusPage.get(response)) {
                timeTakenToGetStatusPage = System.currentTimeMillis() - startTime;
                state.timeTakenToGetStatusPage = timeTakenToGetStatusPage;
                try {
                    ParseStatusPage.parse(response, state);
                    hasSuccess = true;
                } catch (Throwable e) {
                    logger.error("Failed to parse status page returned from \"" + statusPage.getUrl() + "\"", e);
                    ParseStatusPage.clear(state);
                }
            } else {
                state.timeTakenToGetStatusPage = -1;
                ParseStatusPage.clear(state);
            }

            { // Get event log
                long startTime2 = System.currentTimeMillis();
                if (eventLogPage.get(response)) {
                    long now = System.currentTimeMillis();
                    timeTakenToGetEventLog = now - startTime2;
                    state.timeTakenToGetEventLog = timeTakenToGetEventLog;
                    try {
                        ParseEventLog.parse(response, state, now);
                        hasSuccess = true;
                    } catch (Throwable e) {
                        logger.error("Failed to parse event log page returned from \"" + statusPage.getUrl() + "\"", e);
                        ParseEventLog.clear(state);
                    }
                } else {
                    state.timeTakenToGetEventLog = -1;
                    ParseEventLog.clear(state);
                }
            }

            // Check if connected
            Boolean isConnected = shared != null ? Boolean.valueOf(shared.isInternetConnected(state)) : null;
            state.isConnected = isConnected;

            // Write state if something succeeded
            if (hasSuccess) {
                try {
                    output.output(state);
                } catch (Throwable e) {
                    logger.error("Failed to output state", e);
                }
            }

            // Compare correct/uncorrectable total shift to last loop
            long packetErrorsTotal;
            {
                long packetErrors2 = state.corrected + state.uncorrectables;
                if (isFirst) { // If first request, always report 0 packet errors.
                    packetErrorsTotal = 0;
                } else {
                    packetErrorsTotal = packetErrors2 - packetErrors;
                }
                packetErrors = packetErrors2;
            }

            { // Print very rough single line summary
                StringBuilder buffer = new StringBuilder();
                if (isConnected != null) {
                    buffer.append(isConnected.booleanValue() ? "Internet connected. " : "Internet disconnected. ");
                }
                buffer.append("Downstream ");
                appendDownstreamPower(buffer, state);
                buffer.append(" dBmV/");
                appendDownstreamSnr(buffer, state);
                buffer.append(" dB. Upstream ");
                appendUpstreamPower(buffer, state);
                buffer.append(" dBmV.");
                if (packetErrorsTotal != 0) {
                    buffer.append(" ").append(packetErrorsTotal).append(" packet errors. ");
                }
                long timeTaken = (System.currentTimeMillis() - startTime);
                if (timeTaken > checkInterval) {
                    buffer.append(" Took ").append(timeTaken - checkInterval).append("ms longer than check interval (")
                            .append(state.timeTakenToGetStatusPage).append("ms/")
                            .append(state.timeTakenToGetEventLog).append("ms to request pages).");
                }
                logger.info(buffer.toString());
            }

            // Print event log if anything added
            if (!isFirst) {
                List<EventLog> eventLogs = state.eventLogs;
                int count = eventLogs.size();
                if (count > 0) {
                    String newEventLogs = state.getEventLogsAsStringOrNull();
                    assert newEventLogs != null;
                    if (count == 1) {
                        logger.info("Cable modem has new event log:" + Constants.NEW_LINE + newEventLogs);
                    } else {
                        logger.info("Cable modem has new event logs:" + Constants.NEW_LINE + newEventLogs);
                    }
                }
            }

            // Pause for a bit if required
            long totalTime = System.currentTimeMillis() - startTime;
            if (totalTime < checkInterval) {
                Utils.sleep(logger, checkInterval - totalTime);
            }
            isFirst = false;
        }
    }

    private static void appendDownstreamPower(StringBuilder buffer, ModemState state) {
        assert state.downPowerMin <= state.downPowerAvg && state.downPowerAvg <= state.downPowerMax;
        double min = state.downPowerMin;
        double max = state.downPowerMax;
        appendRange(buffer, min, max);
        if (min < -15 || max > 15) {
            buffer.append(" (very bad!)");
        } else if (min < -6 || max > 9) {
            buffer.append(" (bad!)");
        }
    }

    private static void appendDownstreamSnr(StringBuilder buffer, ModemState state) {
        assert state.downSnrMin <= state.downSnrAvg && state.downSnrAvg <= state.downSnrMax;
        double min = state.downSnrMin;
        double max = state.downSnrMax;
        appendRange(buffer, min, max);
        if (min < 35) {
            buffer.append(" (very bad!)");
        } else if (min < 40) {
            buffer.append(" (bad!)");
        }
    }

    private static void appendUpstreamPower(StringBuilder buffer, ModemState state) {
        assert state.upstreamPowerMin <= state.upstreamPowerAvg && state.upstreamPowerAvg <= state.upstreamPowerMax;
        double min = state.upstreamPowerMin;
        double max = state.upstreamPowerMax;
        appendRange(buffer, min, max);
        if (min < 40 || max > 54) {
            buffer.append(" (bad!)");
        }
    }

    private static void appendRange(StringBuilder buffer, double min, double max) {
        if (min == max) {
            buffer.append(min);
        } else if (min >= 0 && max >= 0) {
            buffer.append(min).append("-").append(max);
        } else {
            buffer.append(min).append(" to ").append(max);
        }
    }

}
