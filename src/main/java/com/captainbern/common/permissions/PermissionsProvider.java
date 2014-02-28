package com.captainbern.common.permissions;

import org.bukkit.plugin.Plugin;

public abstract class PermissionsProvider<T> {

    protected Plugin plugin;

    abstract public String getName();

    abstract public Class<T> getProviderClass();

    abstract public boolean isHooked();
}
