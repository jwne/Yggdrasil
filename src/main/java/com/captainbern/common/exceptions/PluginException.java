package com.captainbern.common.exceptions;

import org.bukkit.plugin.Plugin;

public class PluginException extends RuntimeException {

    public PluginException() {
        super();
    }

    public PluginException(String message) {
        super(message);
    }

    public PluginException(Plugin plugin, String message) {
        this("Plugin: " + plugin.getName() + " generated an exception: " + message);
    }

    public PluginException(Plugin plugin, Throwable throwable) {
        this(plugin, "");
        throwable.printStackTrace();
    }
}
