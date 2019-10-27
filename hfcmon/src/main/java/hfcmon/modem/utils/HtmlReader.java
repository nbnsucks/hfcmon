package hfcmon.modem.utils;

import hfcmon.utils.UnexpectedException;

/**
 * Simple class to make it easier to parse HTML files.
 */
public final class HtmlReader {

    private final StringBuilder buffer;
    private int index;
    private int indexEnd = -1;

    public HtmlReader(StringBuilder buffer) {
        this.buffer = buffer;
    }

    public void startSection(String start1, String start2, String end) {
        StringBuilder buffer = this.buffer;

        // Find start1 string
        int start1Index = buffer.indexOf(start1, this.index);
        if (start1Index < 0) {
            throw new UnexpectedException("Failed to find string \"" + start1 + "\" in html from \"" + buffer.substring(this.index) + "\"");
        }
        start1Index += start1.length();

        // Find start2 string
        int start2Index = buffer.indexOf(start2, start1Index);
        if (start2Index < 0) {
            throw new UnexpectedException("Failed to find string \"" + start2 + "\" in html from \"" + buffer.substring(start1Index) + "\"");
        }
        start2Index += start2.length();

        // Find end string after start string
        int endIndex = buffer.indexOf(end, start2Index);
        if (endIndex < 0) {
            throw new UnexpectedException("Failed to find string \"" + end + "\" in html from \"" + buffer.substring(start2Index) + "\"");
        }

        // Update indexes
        this.index = start2Index;
        this.indexEnd = endIndex;
    }

    public void finishSection() {
        assert index >= 0 && indexEnd >= 0 && index <= indexEnd;
        index = indexEnd;
        indexEnd = -1;
    }

    public String readBetween(String start, String end) {
        String value = readBetweenOrNull(start, end);
        if (value == null) {
            throw new UnexpectedException("Expected value between \"" + start + "\" and \"" + end + "\"");
        }
        return value;
    }

    public long readLongBetween(String start, String end) {
        String value = readBetweenOrNull(start, end);
        if (value == null) {
            throw new UnexpectedException("Expected number between \"" + start + "\" and \"" + end + "\"");
        }
        try {
            return Long.parseLong(value);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            throw new UnexpectedException("Invalid number \"" + value + "\" between \"" + start + "\" and \"" + end + "\"");
        }
    }

    public double readDoubleBetween(String start, String end) {
        String value = readBetweenOrNull(start, end);
        if (value == null) {
            throw new UnexpectedException("Expected double between \"" + start + "\" and \"" + end + "\"");
        }
        try {
            return Double.parseDouble(value);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            throw new UnexpectedException("Invalid double \"" + value + "\" between \"" + start + "\" and \"" + end + "\"");
        }
    }

    public String readBetweenOrNull(String start, String end) {
        StringBuilder buffer = this.buffer;
        int indexEnd = this.indexEnd;

        // Find start string
        int startIndex = buffer.indexOf(start, this.index);
        if (startIndex < 0 || indexEnd >= 0 && startIndex >= indexEnd) {
            return null;
        }
        startIndex += start.length();

        // Find end string after start string
        int endIndex = buffer.indexOf(end, startIndex);
        if (endIndex < 0) {
            throw new UnexpectedException("Failed to find string \"" + end + "\" in html from \"" + buffer.substring(startIndex) + "\"");
        } else if (indexEnd >= 0 && endIndex >= indexEnd) {
            return null;
        }

        // Update index
        this.index = endIndex + end.length();
        return buffer.substring(startIndex, endIndex).trim();
    }

}
