package hfcmon.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import hfcmon.Constants;

public final class Utils {

    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        if (str.length() <= 0) {
            return null;
        }
        return str;
    }

    public static void sleep(Logger logger, long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Throwable e) {
            logger.warn("Failed to sleep for " + milliseconds + "ms", e);
        }
    }

    public static Reader getReader(File file) throws FileNotFoundException {
        return new InputStreamReader(new BufferedInputStream(
                new FileInputStream(file), Constants.BUFFER_SIZE), Constants.DEFAULT_CHARSET);
    }

    public static Writer getWriter(File file) throws FileNotFoundException {
        return getWriter(file, false);
    }

    public static Writer getWriter(File file, boolean append) throws FileNotFoundException {
        return new OutputStreamWriter(new BufferedOutputStream(
                new FileOutputStream(file, append), Constants.BUFFER_SIZE), Constants.DEFAULT_CHARSET);
    }

    public static String getClasspathResourceAsString(String classpath) {
        assert classpath != null && classpath.length() > 0 && classpath.startsWith("/");
        InputStream input = Utils.class.getResourceAsStream(classpath);
        if (input == null) {
            throw new UnexpectedException("Classpath resource \"" + classpath + "\" not found");
        }
        try {
            return read(input).toString(Constants.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            throw new UnexpectedException("Failed to read classpath resource \"" + classpath + "\"", e);
        }
    }

    private static ByteArrayOutputStream read(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[Constants.BUFFER_SIZE];
        int read;
        while ((read = input.read(buffer)) >= 0) {
            output.write(buffer, 0, read);
        }
        return output;
    }

    public static String[] parseCommaSeperatedString(String str) {
        assert str != null;
        List<String> values = new ArrayList<>();
        int index = 0;
        for (;;) {
            int index2 = str.indexOf(',', index);
            if (index2 < 0) {
                break;
            }
            String value = Utils.trimToNull(str.substring(index, index2));
            if (value != null) {
                values.add(value);
            }
            index = index2 + 1;
        }
        String value = Utils.trimToNull(str.substring(index));
        if (value != null) {
            values.add(value);
        }
        return values.toArray(new String[values.size()]);
    }

}
