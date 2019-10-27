package hfcmon.modem.model;

import java.io.IOException;

import hfcmon.modem.utils.HtmlReader;
import hfcmon.utils.ObjectBase;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.JsonWriter;

public final class Upstream extends ObjectBase implements Comparable<Upstream> {

    public final long channelId;
    public final String lock;
    public final String channelType;
    public final long frequency;
    public final long width;
    public final double power;

    public Upstream(HtmlReader reader) {
        channelId = reader.readLongBetween("<td>", "</td>");
        lock = reader.readBetween("<td>", "</td>");
        channelType = reader.readBetween("<td>", "</td>");
        frequency = reader.readLongBetween("<td>", " Hz</td>");
        width = reader.readLongBetween("<td>", " Hz</td>");
        power = reader.readDoubleBetween("<td>", " dBmV</td>");
    }

    @Override
    public int compareTo(Upstream obj) {
        return Long.compare(channelId, obj.channelId);
    }

    @Override
    public void write(CellWriter writer) throws IOException {
        writer.write(channelId).write(lock).write(channelType)
                .write(frequency).write(width).write(power);
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.openObject();
        writer.write("channelId", channelId);
        writer.write("lock", lock);
        writer.write("channelType", channelType);
        writer.write("frequency", frequency);
        writer.write("width", width);
        writer.write("power", power);
        writer.closeObject();
    }

}