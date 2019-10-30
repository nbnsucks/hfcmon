package hfcmon;

import java.io.File;

import hfcmon.internetcheck.InternetCheck;
import hfcmon.internetcheck.InternetCheckConfig;
import hfcmon.internetcheck.InternetState;
import hfcmon.modem.ModemCheck;
import hfcmon.modem.ModemConfig;
import hfcmon.output.OutputConfig;
import hfcmon.output.OutputOutage;
import hfcmon.output.OutputOutageImpl;
import hfcmon.output.OutputState;
import hfcmon.output.OutputStateImpl;
import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.LoggerUtils;
import hfcmon.utils.PropertiesHelper;
import hfcmon.utils.UnexpectedException;

public final class Main {

    public static void main(String[] args) {
        LoggerFactory factory = LoggerUtils.getStdOutLoggerFactory();
        Logger logger = factory.getLogger(Main.class);
        try {
            // Load properties/config
            ModemConfig modemConfig = new ModemConfig();
            InternetCheckConfig internetCheckConfig = new InternetCheckConfig();
            OutputConfig outputConfig = new OutputConfig();
            loadProperties(logger, args, modemConfig, internetCheckConfig, outputConfig);

            // Create logger as requested by user
            factory = LoggerUtils.getLoggerFactory(outputConfig.getLogFile(), outputConfig.logToStdOut, logger);
            logger = factory.getLogger(Main.class);

            // Start internet connection check (will create separate thread to run in)
            InternetState shared;
            if (internetCheckConfig.enabled) {
                shared = new InternetState();
                OutputOutage outputOutage = new OutputOutageImpl(factory, outputConfig);
                InternetCheck.start(internetCheckConfig, outputOutage, shared, factory);
            } else {
                shared = null; // Leave as null so ModemCheck knowns we're not checking the status of the internet connection.
            }

            // Start checking cable modem (if enabled)
            if (modemConfig.enabled) {
                OutputState outputState = new OutputStateImpl(factory, outputConfig);
                ModemCheck.start(modemConfig, outputState, shared, factory);
            }
        } catch (Throwable e) {
            logger.error("Unexpected failure", e);
            System.exit(1);
        }
    }

    private static void loadProperties(Logger logger, String[] args,
            ModemConfig checkConfig, InternetCheckConfig internetCheckConfig, OutputConfig outputConfig) {
        File file;
        if (args == null || args.length == 0) {
            file = new File(Constants.DEFAULT_PROPERTIES_FILE_NAME);
        } else if (args.length == 1) {
            file = new File(args[0]);
        } else {
            throw new UnexpectedException("Expected either no arguments "
                    + "(in which case the properties file \"" + Constants.DEFAULT_PROPERTIES_FILE_NAME
                    + "\" will be loaded) or a single argument which is the configuration file to use."
                    + " If this configuration file doesn't exist it will be created");
        }

        // Load or create properties file
        PropertiesHelper properties = new PropertiesHelper(file);
        if (!file.exists()) {
            // Write default properties
            checkConfig.save(properties);
            internetCheckConfig.save(properties);
            outputConfig.save(properties);

            // Save to configuration file to load next time
            properties.save();
            logger.info("Created default properties file \"" + file.getAbsolutePath() + "\"");
        } else {
            // Load configuration file from file system
            properties.load();

            // Parse properties
            try {
                checkConfig.load(properties);
                internetCheckConfig.load(properties);
                outputConfig.load(properties);
            } catch (Throwable e) {
                throw new UnexpectedException("Invalid property in properties file \"" + file.getAbsolutePath() + "\"", e);
            }
            logger.info("Successfully loaded properties file \"" + file.getAbsolutePath() + "\"");
        }
    }

}
