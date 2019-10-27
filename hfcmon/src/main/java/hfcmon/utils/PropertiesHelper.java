package hfcmon.utils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

public final class PropertiesHelper {

    private final File file;
    private final Properties properties = new Properties();

    public PropertiesHelper(File file) {
        this.file = file;
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void setProperty(String key, int value) {
        properties.setProperty(key, Integer.toString(value));
    }

    public void setProperty(String key, boolean value) {
        properties.setProperty(key, value ? "true" : "false");
    }

    public String getProperty(String key, String defaultValue) {
        return Utils.trimToNull(properties.getProperty(key, defaultValue));
    }

    public void setProperty(String key, String[] value) {
        StringBuilder buffer = new StringBuilder();
        if (value != null && value.length > 0) {
            buffer.append(value[0]);
            for (int index = 1; index < value.length; index++) {
                buffer.append(", ");
                buffer.append(value[index]);
            }
        }
        properties.setProperty(key, buffer.toString());
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = Utils.trimToNull(properties.getProperty(key));
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            throw new UnexpectedException("Invalid value \"" + value + "\" for key \"" + key + "\" (default value is " + defaultValue + ")");
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = Utils.trimToNull(properties.getProperty(key));
        if (value == null) {
            return defaultValue;
        } else if ("true".equals(value)) {
            return true;
        } else if ("false".equals(value)) {
            return false;
        } else {
            throw new UnexpectedException("Expected \"true\" or \"false\", but given value \""
                    + value + "\" for key \"" + key + "\" (default value is " + defaultValue + ")");
        }
    }

    public String[] getStringArrayProperty(String key, String[] defaultValue) {
        String str = Utils.trimToNull(properties.getProperty(key));
        if (str == null) {
            return defaultValue;
        }
        String[] values = Utils.parseCommaSeperatedString(str);
        if (values == null || values.length <= 0) {
            return defaultValue;
        }
        return values;
    }

    public void save() {
        try (Writer writer = Utils.getWriter(file)) {
            properties.store(writer, "Properties file for hfcmon utility");
        } catch (IOException e) {
            throw new UnexpectedException("Failed to write default properties to file \"" + file.getAbsolutePath() + "\"", e);
        }
    }

    public void load() {
        try (Reader reader = Utils.getReader(file)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new UnexpectedException("Failed to read properties file \"" + file.getAbsolutePath() + "\"", e);
        }
    }

}
