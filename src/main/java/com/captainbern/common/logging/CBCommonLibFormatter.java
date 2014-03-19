package com.captainbern.common.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CBCommonLibFormatter extends Formatter {

    private final String line = "======================================================================";

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");

    @Override
    public String format(LogRecord record) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(getDate(record.getMillis()))
                .append(" ").append("[").append(record.getLevel()).append("] ").append(formatMessage(record)).append("\r\n");
        return buffer.toString();
    }

    private String getDate(long millis) {
        return dateFormat.format(new Date(millis));
    }

    @Override
    public String getHead(Handler handler) {
        return line + "\r\nLog report made on: " + getDate(System.currentTimeMillis()) + "\r\n" + line + "\r\n";
    }

    @Override
    public String getTail(Handler handler) {
        return line + "\\n";
    }
}
