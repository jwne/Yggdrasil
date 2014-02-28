package com.captainbern.common.logging;

public interface ILogger {

    public final String ROOT_LOGGER_NAME = "ROOT";

    public String getName();

    /**
     * Trace functions
     */
    public boolean isTraceEnabled();

    public void trace(String msg);

    public void trace(String format, Object arg);

    public void trace(String format, Object... args);

    public void trace(String message, Throwable throwable);


    /**
     * Debug functions
     */
    public boolean isDebugEnabled();

    public void debug(String message);

    public void debug(String format, Object arg);

    public void debug(String format, Object... args);

    public void debug(String message, Throwable throwable);


    /**
     * Info functions
     */
    public boolean isInfoEnabled();

    public void info(String message);

    public void info(String format, Object arg);

    public void info(String format, Object... args);

    public void info(String message, Throwable throwable);


    /**
     * Warn functions
     */
    public boolean isWarnEnabled();

    public void warn(String message);

    public void warn(String format, Object arg);

    public void warn(String format, Object... args);

    public void warn(String message, Throwable throwable);

    /**
     * Error functions
     */
    public boolean isErrorEnabled();

    public void error(String message);

    public void error(String format, Object arg);

    public void error(String format, Object... args);

    public void error(String message, Throwable throwable);

    /**
     * Severe functions
     */
    public boolean isSevereEnabled();

    public void severe(String message);

    public void severe(String format, Object arg);

    public void severe(String format, Object... args);

    public void severe(String message, Throwable throwable);

    /**
     * Module logger functions
     */
    public ILogger getModuleLogger(String... name);

}
