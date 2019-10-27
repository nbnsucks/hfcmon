package hfcmon.utils;

import java.io.IOException;
import java.util.List;

import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.CellWriterUtils;
import hfcmon.utils.io.JsonWriter;
import hfcmon.utils.io.JsonWriterUtils;

public abstract class ObjectBase {

    public abstract void write(CellWriter writer) throws IOException;

    public abstract void write(JsonWriter writer) throws IOException;

    @Override
    public final String toString() {
        StringBuilder buffer = new StringBuilder().append(getClass().getSimpleName()).append('(');
        try {
            write(CellWriterUtils.getCellWriter(buffer));
        } catch (IOException e) {
            throw new UnexpectedException("Unexpected IOException", e);
        }
        return buffer.append(')').toString();
    }

    public final String toCsv() {
        StringBuilder buffer = new StringBuilder();
        try {
            write(CellWriterUtils.getCellWriter(buffer));
        } catch (IOException e) {
            throw new UnexpectedException("Unexpected IOException", e);
        }
        return buffer.toString();
    }

    public final String toJson() {
        StringBuilder buffer = new StringBuilder();
        try {
            JsonWriter writer = JsonWriterUtils.getJsonWriter(buffer);
            write(writer);
            writer.ensureDone();
        } catch (IOException e) {
            throw new UnexpectedException("Unexpected IOException", e);
        }
        return buffer.toString();
    }

    public static <T extends ObjectBase> String toCsv(List<T> list) {
        StringBuilder buffer = new StringBuilder();
        try {
            CellWriter writer = CellWriterUtils.getCellWriter(buffer);
            boolean isFirst = true;
            for (T item : list) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    writer.newline();
                }
                item.write(writer);
            }
        } catch (IOException e) {
            throw new UnexpectedException("Unexpected IOException", e);
        }
        return buffer.toString();
    }

    public static <T extends ObjectBase> String toJson(List<T> list) {
        StringBuilder buffer = new StringBuilder();
        try {
            JsonWriter writer = JsonWriterUtils.getJsonWriter(buffer);
            for (T item : list) {
                item.write(writer);
                writer.nextRecord();
            }
        } catch (IOException e) {
            throw new UnexpectedException("Unexpected IOException", e);
        }
        return buffer.toString();
    }

}
