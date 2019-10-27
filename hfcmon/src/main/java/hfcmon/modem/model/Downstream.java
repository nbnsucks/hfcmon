package hfcmon.modem.model;

import java.io.IOException;

import hfcmon.modem.utils.HtmlReader;
import hfcmon.utils.ObjectBase;
import hfcmon.utils.UnexpectedException;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.JsonWriter;

public final class Downstream extends ObjectBase implements Comparable<Downstream> {

    public final long channelId;
    public final String lock;
    public final String modulation;
    public final long frequency;
    public final double power;
    public final double snr;
    public final long corrected;
    public final long uncorrectables;

    public Downstream(String channelIdStr, HtmlReader reader) {
        try {
            channelId = Long.parseLong(channelIdStr);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            throw new UnexpectedException("Invalid channelId \"" + channelIdStr + "\"");
        }
        lock = reader.readBetween("<td>", "</td>");
        modulation = reader.readBetween("<td>", "</td>");
        frequency = reader.readLongBetween("<td>", " Hz</td>");
        power = reader.readDoubleBetween("<td>", " dBmV</td>");
        snr = reader.readDoubleBetween("<td>", " dB</td>");
        corrected = reader.readLongBetween("<td>", "</td>");
        uncorrectables = reader.readLongBetween("<td>", "</td>");
    }

    @Override
    public int compareTo(Downstream obj) {
        return Long.compare(channelId, obj.channelId);
    }

    @Override
    public void write(CellWriter writer) throws IOException {
        writer.write(channelId).write(lock).write(modulation).write(frequency)
                .write(power).write(snr).write(corrected).write(uncorrectables);
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.openObject();
        writer.write("channelId", channelId);
        writer.write("lock", lock);
        writer.write("modulation", modulation);
        writer.write("frequency", frequency);
        writer.write("power", power);
        writer.write("snr", snr);
        writer.write("corrected", corrected);
        writer.write("uncorrectables", uncorrectables);
        writer.closeObject();
    }

}