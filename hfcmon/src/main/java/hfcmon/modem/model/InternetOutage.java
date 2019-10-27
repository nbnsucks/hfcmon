package hfcmon.modem.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hfcmon.Constants;
import hfcmon.utils.ObjectBase;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.JsonWriter;

public final class InternetOutage extends ObjectBase {

    public final long startTime;
    public long endTime;

    public final List<EventLog> eventLogs = new ArrayList<>(); // In correct order

    public InternetOutage(long startTime) {
        this.startTime = startTime;
    }

    public void addEventLogs(ModemState state) {
        assert state != null;
        List<EventLog> src = state.eventLogs;
        int count = src.size();
        if (count > 0) {
            List<EventLog> dst = this.eventLogs;
            do {
                dst.add(src.get(--count));
            } while (count > 0);
        }
    }

    public String getEventLogsAsStringOrNull() { // Used to write CSV cell below.
        List<EventLog> eventLogs = this.eventLogs;
        int count = eventLogs.size();
        if (count <= 0) {
            return null;
        }

        // Output event logs
        StringBuilder buffer = new StringBuilder();
        eventLogs.get(0).write(buffer);
        int index = 1;
        while (index < count) {
            buffer.append(Constants.NEW_LINE);
            eventLogs.get(index++).write(buffer);
        }
        return buffer.toString();
    }

    public static void writeHeading(CellWriter writer) throws IOException {
        writer.write("Outage Start").write("Outage End").write();

        writer.write("Length (min)").write("Length (seconds)").write();

        writer.write("Event Logs");
    }

    @Override
    public void write(CellWriter writer) throws IOException {
        long startTime = this.startTime;
        long endTime = this.endTime;
        long seconds = startTime <= endTime ? (endTime - startTime) / 1000 : -1;

        writer.writeTimestamp(startTime).writeTimestamp(endTime).write();

        writer.write(seconds >= 0 ? seconds / 60.0 : -1).write(seconds).write();

        writer.write(getEventLogsAsStringOrNull());
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.openObject();
        writer.write("startTime", startTime);
        writer.write("endTime", endTime);

        writer.openArray("eventLogs");
        for (EventLog eventLog : eventLogs) {
            eventLog.write(writer);
        }
        writer.closeArray();

        writer.closeObject();
    }

}