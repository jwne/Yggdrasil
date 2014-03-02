package com.captainbern.common.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CBCommonLibFormatter extends Formatter {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");

    @Override
    public String format(LogRecord record) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getDate(record.getMillis()));
        buffer.append(" ");
        buffer.append("[" + record.getLevel() + "] ");
        buffer.append(formatMessage(record) + "\n");
        return buffer.toString();
    }

    private String getDate(long millis) {
        return dateFormat.format(new Date(millis));
    }

    @Override
    public String getHead(Handler handler) {
        return "Log report from: " + getDate(System.currentTimeMillis()) + "\n";
    }

    @Override
    public String getTail(Handler handler) {
        return "\n";
    }
}
