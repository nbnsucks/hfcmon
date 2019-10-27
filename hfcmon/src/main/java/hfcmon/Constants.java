package hfcmon;

import java.nio.charset.Charset;

public final class Constants {

    public static final String DEFAULT_PROPERTIES_FILE_NAME = "hfcmon.conf";

    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

    public static final boolean PRINT_RESPONSE_TO_STDOUT_WHILE_READ = false;
    public static final int BUFFER_SIZE = 65536;

    public static final String NEW_LINE = "\r\n";

    public static final String CSV_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FILE_DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss.SSS";

}
