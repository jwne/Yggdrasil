package com.captainbern.yggdrasil.error;

import org.bukkit.plugin.Plugin;

public interface ErrorReporter {

    public abstract void report(Plugin plugin, String method, Throwable throwable);

    public abstract void report(Plugin plugin, String method, Throwable throwable, Object... args);

    public abstract void debug(Plugin plugin, Report report);

    public abstract void reportDetailed(Plugin plugin, Report report);

}
