package com.captainbern.common.logging;

import com.captainbern.common.utils.LogicUtil;
import com.captainbern.common.utils.StringUtil;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ModuleLogger extends Logger implements ILogger {

    private final String[] modulePath;
    private final String prefix;

    protected ModuleLogger(String... name) {
        this(Bukkit.getLogger(), name);
    }

    public ModuleLogger(Logger rootLogger, String... name) {
        super(StringUtil.join(name, "."), null);
        this.setParent(rootLogger);
        this.setLevel(Level.ALL);
        this.modulePath = name;
        StringBuilder sbuf = new StringBuilder();
        for(String module : name) {
            sbuf.append("[" + module + "] ");
        }
        this.prefix = sbuf.toString();
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {

    }

    @Override
    public void trace(String format, Object arg) {

    }

    @Override
    public void trace(String format, Object... args) {

    }

    @Override
    public void trace(String message, Throwable throwable) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String message) {

    }

    @Override
    public void debug(String format, Object arg) {

    }

    @Override
    public void debug(String format, Object... args) {

    }

    @Override
    public void debug(String message, Throwable throwable) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String format, Object arg) {

    }

    @Override
    public void info(String format, Object... args) {

    }

    @Override
    public void info(String message, Throwable throwable) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String message) {

    }

    @Override
    public void warn(String format, Object arg) {

    }

    @Override
    public void warn(String format, Object... args) {

    }

    @Override
    public void warn(String message, Throwable throwable) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String message) {

    }

    @Override
    public void error(String format, Object arg) {

    }

    @Override
    public void error(String format, Object... args) {

    }

    @Override
    public void error(String message, Throwable throwable) {

    }

    @Override
    public boolean isSevereEnabled() {
        return false;
    }

    @Override
    public void severe(String format, Object arg) {

    }

    @Override
    public void severe(String format, Object... args) {

    }

    @Override
    public void severe(String message, Throwable throwable) {

    }

    @Override
    public ILogger getModuleLogger(String... name) {
        ModuleLogger moduleLogger = new ModuleLogger(LogicUtil.appendArray(this.modulePath, name));
        return moduleLogger;
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(this.prefix + logRecord.getMessage());
        super.log(logRecord);
    }
}
