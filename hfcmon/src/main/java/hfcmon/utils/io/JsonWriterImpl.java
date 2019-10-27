package hfcmon.utils.io;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import hfcmon.utils.Logger;
import hfcmon.utils.UnexpectedException;

/**
 * Adaption of "org.glassfish.json.JsonGeneratorImpl" written by Jitendra Kotamraju.
 */
public abstract class JsonWriterImpl implements JsonWriter {

    private static final char[] NULL = "null".toCharArray();
    private static final char[] TRUE = "true".toCharArray();
    private static final char[] FALSE = "false".toCharArray();

    private Context currentContext = new Context(Scope.IN_NONE);
    private final Deque<Context> stack = new ArrayDeque<>();

    // Using own buffering mechanism as JDK's BufferedWriter uses synchronized methods.
    private final char[] buffer; // capacity >= INT_MIN_VALUE_CHARS.length
    private int index;

    protected JsonWriterImpl(int bufferSize) {
        this.buffer = new char[bufferSize];
    }

    /* *********************************************** */
    // Extending implementation must implement these methods

    protected abstract void _flush() throws IOException;

    protected abstract void _close(Logger logger);

    protected abstract void _write(char[] array, int length) throws IOException;

    /* *********************************************** */
    // General methods

    @Override
    public void nextRecord() throws IOException {
        // Check in valid state
        if (currentContext.scope != Scope.IN_NONE || currentContext.first) {
            throw new UnexpectedException("Incomplete json (either nothing written or start of object/array)");
        }

        // Append comma followed by newline and flush
        appendChar(',');
        appendChar('\r');
        appendChar('\n');
        flushBuffer();
        _flush();

        // Reset for next call
        currentContext = new Context(Scope.IN_NONE);
        stack.clear();
    }

    @Override
    public void ensureDone() throws IOException {
        if (currentContext.scope != Scope.IN_NONE || currentContext.first) {
            throw new UnexpectedException("Incomplete json (either nothing written or start of object/array)");
        }
        flushBuffer();
    }

    @Override
    public void flush() throws IOException {
        flushBuffer();
        _flush();
    }

    @Override
    public void close(Logger logger) {
        // Note: "flushBuffer()" not called on purpose - "ensureDone()" must be called before close.
        _close(logger);
    }

    /* *********************************************** */
    // Methods called while in an object

    @Override
    public void openObject(String name) throws IOException {
        writeNameStart(name, Scope.IN_OBJECT);
        appendChar('{');
        stack.push(currentContext);
        currentContext = new Context(Scope.IN_OBJECT);
    }

    @Override
    public void openArray(String name) throws IOException {
        writeNameStart(name, Scope.IN_ARRAY);
        appendChar('[');
        stack.push(currentContext);
        currentContext = new Context(Scope.IN_ARRAY);
    }

    @Override
    public void closeObject() throws IOException {
        if (currentContext.scope != Scope.IN_OBJECT) {
            throw wrongScopeException("Expected to be in object");
        }
        appendChar('}');
        currentContext = stack.pop();
    }

    @Override
    public void write(String name, String value) throws IOException {
        writeNameStart(name, null);
        appendQuotedAndEscapedStringOrNull(value);
    }

    @Override
    public void write(String name, long value) throws IOException {
        writeNameStart(name, null);
        appendSafeString(Long.toString(value));
    }

    @Override
    public void write(String name, double value) throws IOException {
        writeNameStart(name, null);
        appendSafeString(Double.toString(value));
    }

    @Override
    public void write(String name, boolean value) throws IOException {
        writeNameStart(name, null);
        if (value) {
            appendTrue();
        } else {
            appendFalse();
        }
    }

    private void writeNameStart(String name, Scope newScope) throws IOException {
        assert name != null;
        if (currentContext.scope != Scope.IN_OBJECT) {
            throw wrongScopeException("Expected to be in object");
        }
        appendComma(newScope);
        appendQuotedAndEscapedString(name);
        appendChar(':');
    }

    /* *********************************************** */
    // Methods called while in an array

    @Override
    public void openObject() throws IOException {
        writeArrayStartEitherStartOfObjectOrArray(Scope.IN_OBJECT);
        appendChar('{');
        stack.push(currentContext);
        currentContext = new Context(Scope.IN_OBJECT);
    }

    @Override
    public void openArray() throws IOException {
        writeArrayStartEitherStartOfObjectOrArray(Scope.IN_ARRAY);
        appendChar('[');
        stack.push(currentContext);
        currentContext = new Context(Scope.IN_ARRAY);
    }

    @Override
    public void closeArray() throws IOException {
        if (currentContext.scope != Scope.IN_ARRAY) {
            throw wrongScopeException("Expected to be in array");
        }
        appendChar(']');
        currentContext = stack.pop();
    }

    private void writeArrayStartEitherStartOfObjectOrArray(Scope newScope) throws IOException {
        Scope scope = currentContext.scope;
        if (scope != Scope.IN_ARRAY) {
            if (scope == Scope.IN_OBJECT) {
                throw wrongScopeException("Expected to be in array");
            } else if (scope == Scope.IN_NONE && !currentContext.first) {
                throw new UnexpectedException("Expected to be first object/array opened");
            }
        }
        appendComma(newScope);
    }

    private UnexpectedException wrongScopeException(String message) {
        Scope scope = currentContext.scope;
        return new UnexpectedException(message + ", but actually " + scope.getDescription());
    }

    /* *********************************************** */
    // Helper Methods

    private void appendComma(Scope newScope) throws IOException {
        if (currentContext.first) {
            currentContext.first = false;
        } else {
            appendChar(',');
        }
    }

    private void appendSafeString(String str) throws IOException {
        appendSafeString(str, 0, str.length());
    }

    private void appendSafeString(String str, int strIndex, int strIndexMax) throws IOException {
        while (strIndex < strIndexMax) {
            // Flush buffer if required
            if (index >= buffer.length) {
                flushBuffer();
            }

            // Append to buffer
            int lengthToWrite = Math.min(buffer.length - index, strIndexMax - strIndex);
            str.getChars(strIndex, strIndex + lengthToWrite, buffer, index);

            // Update state
            strIndex += lengthToWrite;
            index += lengthToWrite;
        }
    }

    private void appendNull() throws IOException {
        appendCharArrayDirectly(NULL, 0, NULL.length);
    }

    private void appendTrue() throws IOException {
        appendCharArrayDirectly(TRUE, 0, TRUE.length);
    }

    private void appendFalse() throws IOException {
        appendCharArrayDirectly(FALSE, 0, FALSE.length);
    }

    private void appendCharArrayDirectly(char[] output, int outputIndex, int outputIndexMax) throws IOException {
        while (outputIndex < outputIndexMax) {
            // Flush buffer if required
            if (index >= buffer.length) {
                flushBuffer();
            }

            // Append to buffer
            int lengthToWrite = Math.min(buffer.length - index, outputIndexMax - outputIndex);
            System.arraycopy(output, outputIndex, buffer, index, lengthToWrite);

            // Update state
            index += lengthToWrite;
            outputIndex += lengthToWrite;
        }
    }

    private void appendChar(char c) throws IOException {
        if (index >= buffer.length) {
            flushBuffer();
        }
        buffer[index++] = c;
    }

    private void flushBuffer() throws IOException {
        if (index > 0) {
            _write(buffer, index);
            index = 0;
        }
    }

    private void appendQuotedAndEscapedStringOrNull(CharSequence str) throws IOException {
        if (str == null) {
            appendNull();
        } else {
            appendQuotedAndEscapedString(str);
        }
    }

    private void appendQuotedAndEscapedString(CharSequence str) throws IOException {
        assert str != null;
        appendChar('"');
        int length = str.length();
        for (int index = 0; index < length; index++) {
            char tmp = str.charAt(index);
            if (isCharSafe(tmp)) {
                appendChar(tmp);
            } else {
                appendCharEscaped(tmp);
            }

        }
        appendChar('"');
    }

    private void appendCharEscaped(char tmp) throws IOException {
        switch (tmp) {
            case '"':
            case '\\':
                appendChar('\\');
                appendChar(tmp);
                break;
            case '\b':
                appendChar('\\');
                appendChar('b');
                break;
            case '\f':
                appendChar('\\');
                appendChar('f');
                break;
            case '\n':
                appendChar('\\');
                appendChar('n');
                break;
            case '\r':
                appendChar('\\');
                appendChar('r');
                break;
            case '\t':
                appendChar('\\');
                appendChar('t');
                break;
            default:
                String hex = "000" + Integer.toHexString(tmp);
                appendSafeString("\\u" + hex.substring(hex.length() - 4));
        }
    }

    private boolean isCharSafe(char tmp) {
        return tmp >= 0x20 && tmp != 0x22 && tmp != 0x5c;
    }

    /* *********************************************** */
    // Internal enums/classes

    private static enum Scope {
        IN_NONE("in none"),
        IN_OBJECT("in object"),
        IN_ARRAY("in array");

        private final String description;

        Scope(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private static final class Context {
        protected final Scope scope;
        protected boolean first = true;

        protected Context(Scope scope) {
            this.scope = scope;
        }
    }

    /* *********************************************** */
}
