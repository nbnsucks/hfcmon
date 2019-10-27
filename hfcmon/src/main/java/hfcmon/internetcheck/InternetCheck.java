package hfcmon.internetcheck;

import hfcmon.modem.model.InternetOutage;
import hfcmon.output.OutputOutage;
import hfcmon.utils.BeepHelper;
import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.Utils;

public final class InternetCheck {

    public static void start(InternetCheckConfig config, OutputOutage output, InternetState shared, LoggerFactory factory) {
        Logger logger = factory.getLogger(InternetCheck.class);
        ConnectionCheck check = new ConnectionCheck(factory, config.pingTimeout, config.pingDomains);
        BeepHelper beep = config.beep ? new BeepHelper(factory) : null;

        // Connection loop
        new Thread() {
            @Override
            public void run() {
                for (;;) {
                    logger.info("Started internet connection check loop...");
                    try {
                        eventLoop(config, logger, check, shared, beep, output);
                    } catch (Throwable e) {
                        int milliseconds = config.unexpectedExceptionRetryInterval;
                        logger.error("Unexpected exception - waiting " + milliseconds + "ms before trying again", e);
                        Utils.sleep(logger, config.unexpectedExceptionRetryInterval);
                    }
                }
            }
        }.start();
    }

    private static void eventLoop(InternetCheckConfig config,
            Logger logger, ConnectionCheck check, InternetState shared, BeepHelper beep, OutputOutage output) throws Exception {
        final long checkIntervalMin = config.checkIntervalMin;
        final long checkIntervalIncrement = config.checkIntervalIncrement;
        final long checkIntervalMax = config.checkIntervalMax;

        long checkInterval = checkIntervalMax;
        for (;;) {
            // Wait and check still connected
            do {
                if (checkInterval != checkIntervalMax) {
                    if (checkInterval < checkIntervalMax) {
                        checkInterval += checkIntervalIncrement;
                    } else {
                        checkInterval = checkIntervalMax;
                    }
                }

                // Sleep for interval
                Utils.sleep(logger, checkInterval);
            } while (check.isConnected());

            // Internet disconnected
            logger.info("Internet disconnected");
            long startTime = System.currentTimeMillis();
            InternetOutage outage = new InternetOutage(startTime);
            shared.onInternetDisconnected(outage);
            if (beep != null) {
                beep.beepReverse();
            }

            // Wait util connected again
            do {
                Utils.sleep(logger, checkIntervalMin);
            } while (!check.isConnected());
            long endTime = System.currentTimeMillis();
            outage.endTime = endTime;
            long seconds = (endTime - startTime) / 1000;

            // Internet reconnected
            logger.info("Internet reconnected after " + (seconds / 60.0) + " minutes / " + seconds + " seconds");
            shared.onInternetConnected();
            output.output(outage);
            if (beep != null) {
                beep.beep();
            }
            checkInterval = checkIntervalMin;
        }
    }

}
