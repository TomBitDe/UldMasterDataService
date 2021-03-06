package uldmasterdataloader.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Format a LogRecord into a brief format. The formatter creates a shorter
 * output format for java logging. The output looks like this (single line):
 * 
 *     2009.12.30 10:10:06:732 root[INFO|main]: This is the log message 
 */
public class BriefLogFormatter extends Formatter {
    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd h:mm:ss.SSS";
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final DateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT_DEFAULT);

    /**
     * A custom format implementation that is designed for brevity.
     *
     * @return formatted string
     */
    @Override
    public String format(LogRecord record) {
        String loggerName = record.getLoggerName();
        if (loggerName == null) {
            loggerName = "root";
        }
        String output = String.valueOf(String.valueOf(String.valueOf(FORMAT.format(new Date(record.getMillis())) + " " + loggerName + "[") + String.valueOf(record.getLevel())) + Character.toString('|') + Thread.currentThread().getName() + "]: " + formatMessage(record)) + Character.toString(' ') + LINE_SEP;
        return output;
    }
}
