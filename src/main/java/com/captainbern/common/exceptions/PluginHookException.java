package com.captainbern.common.exceptions;

import org.bukkit.plugin.Plugin;

public class PluginHookException extends PluginException {

    public PluginHookException(Plugin toHook) {
        super("Failed to hook plugin: " + toHook.getName() + "!");
    }

    public PluginHookException(Plugin plugin, Throwable throwable) {
        this(plugin);
        throwable.printStackTrace();
    }

    public PluginHookException(Plugin plugin, String message) {
        super("Failed to hook plugin: " + plugin.getName() + ": " + message);
    }
}
