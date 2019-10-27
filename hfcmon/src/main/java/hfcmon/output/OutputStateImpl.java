package hfcmon.output;

import java.io.IOException;
import java.util.List;

import hfcmon.modem.model.EventLog;
import hfcmon.modem.model.ModemState;
import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.UnexpectedException;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.CellWriterUtils;
import hfcmon.utils.io.JsonWriter;
import hfcmon.utils.io.JsonWriterUtils;

public final class OutputStateImpl implements OutputState {

    private final Logger logger;
    private final OutputConfig config;

    private CellWriter statusCsv;
    private JsonWriter statusJson;

    private CellWriter eventLogCsv;
    private JsonWriter eventLogJson;

    public OutputStateImpl(LoggerFactory factory, OutputConfig config) {
        this.logger = factory.getLogger(this);
        this.config = config;
    }

    @Override
    public void start() {
        assert statusCsv == null && statusJson == null
                && eventLogCsv == null && eventLogJson == null;

        long now = System.currentTimeMillis();
        statusCsv = CellWriterUtils.getCellWriterOrNull(config.getStatusCsv(now), ModemState::writeHeading);
        statusJson = JsonWriterUtils.getJsonWriterOrNull(config.getStatusJson(now));

        eventLogCsv = CellWriterUtils.getCellWriterOrNull(config.getEventLogCsv(now), EventLog::writeHeading);
        eventLogJson = JsonWriterUtils.getJsonWriterOrNull(config.getEventLogJson(now));
    }

    @Override
    public void close() {
        Logger logger = this.logger;
        {
            CellWriter statusCsv = this.statusCsv;
            if (statusCsv != null) {
                this.statusCsv = null;
                statusCsv.close(logger);
            }
        }
        {
            JsonWriter statusJson = this.statusJson;
            if (statusJson != null) {
                this.statusJson = null;
                statusJson.close(logger);
            }
        }
        {
            CellWriter eventLogCsv = this.eventLogCsv;
            if (eventLogCsv != null) {
                this.eventLogCsv = null;
                eventLogCsv.close(logger);
            }
        }
        {
            JsonWriter eventLogJson = this.eventLogJson;
            if (eventLogJson != null) {
                this.eventLogJson = null;
                eventLogJson.close(logger);
            }
        }
    }

    @Override
    public void output(ModemState state) {
        assert state != null;
        outputStatus(state);
        outputEventLog(state);
    }

    private void outputStatus(ModemState state) {
        {
            CellWriter statusCsv = this.statusCsv;
            if (statusCsv != null) {
                try {
                    state.write(statusCsv);
                    statusCsv.newline();
                } catch (IOException e) {
                    throw new UnexpectedException("Failed to write csv row to \"" + statusCsv.getFilename() + "\"", e);
                }
            }
        }
        {
            JsonWriter statusJson = this.statusJson;
            if (statusJson != null) {
                try {
                    state.write(statusJson);
                    statusJson.nextRecord();
                } catch (IOException e) {
                    throw new UnexpectedException("Failed to write json row to \"" + statusJson.getFilename() + "\"", e);
                }
            }
        }
    }

    private void outputEventLog(ModemState state) {
        {
            CellWriter eventLogCsv = this.eventLogCsv;
            if (eventLogCsv != null) {
                try {
                    List<EventLog> eventLogs = state.eventLogs;
                    int count = eventLogs.size();
                    while (count > 0) {
                        EventLog eventLog = eventLogs.get(--count);
                        eventLog.write(eventLogCsv);
                        eventLogCsv.newline();
                    }
                } catch (IOException e) {
                    throw new UnexpectedException("Failed to write csv row to \"" + eventLogCsv.getFilename() + "\"", e);
                }
            }
        }
        {
            JsonWriter eventLogJson = this.eventLogJson;
            if (eventLogJson != null) {
                try {
                    List<EventLog> eventLogs = state.eventLogs;
                    int count = eventLogs.size();
                    while (count > 0) {
                        EventLog eventLog = eventLogs.get(--count);
                        eventLog.write(eventLogJson);
                        eventLogJson.nextRecord();
                    }
                } catch (IOException e) {
                    throw new UnexpectedException("Failed to write json row to \"" + eventLogJson.getFilename() + "\"", e);
                }
            }
        }
    }

}
