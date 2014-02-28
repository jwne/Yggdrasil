package com.captainbern.common.error;

import org.bukkit.plugin.Plugin;

import java.io.PrintStream;

public class BasicErrorReporter implements ErrorReporter {

    private PrintStream stream;

    public BasicErrorReporter() {
        this(System.err);
    }

    public BasicErrorReporter(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void report(Plugin plugin, String method, Throwable throwable) {
        stream.println(method + " generated an unhandled exception for: " + plugin.getName());
        throwable.printStackTrace();
    }

    @Override
    public void report(Plugin plugin, String method, Throwable throwable, Object... args) {
         report(plugin, method, throwable);
    }

    @Override
    public void debug(Plugin plugin, Report report) {
         stream.println("[DEBUG] [" + plugin.getName() + "] " + report.toString());
    }

    @Override
    public void reportDetailed(Plugin plugin, Report report) {

    }

    protected void printArgs(Object... args) {

    }
}
