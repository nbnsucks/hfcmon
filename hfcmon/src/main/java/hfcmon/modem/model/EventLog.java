package hfcmon.modem.model;

import java.io.IOException;

import hfcmon.utils.ObjectBase;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.JsonWriter;

public final class EventLog extends ObjectBase {

    public final long timeSeen; // Internal value (doesn't come from cable modem)

    public final String date;
    public final long eventId;
    public final long eventLevel;
    public final String description;

    public EventLog(long timeSeen, String date, long eventId, long eventLevel, String description) {
        this.timeSeen = timeSeen;
        this.date = date;
        this.eventId = eventId;
        this.eventLevel = eventLevel;
        this.description = description;
    }

    public void write(StringBuilder buffer) {
        buffer.append(description).append(" (date=").append(date)
                .append(", eventId=").append(eventId)
                .append(", eventLevel=").append(eventLevel).append(")");
    }

    public static void writeHeading(CellWriter writer) throws IOException {
        writer.write("Time Seen").write("Date").write("Event ID").write("Event Level").write("Description");
    }

    @Override
    public void write(CellWriter writer) throws IOException {
        writer.writeTimestamp(timeSeen).write(date).write(eventId).write(eventLevel).write(description);
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.openObject();
        writer.write("timeSeen", timeSeen);
        writer.write("date", date);
        writer.write("eventId", eventId);
        writer.write("eventLevel", eventLevel);
        writer.write("description", description);
        writer.closeObject();
    }

}