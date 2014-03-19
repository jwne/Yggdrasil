package com.captainbern.yggdrasil.permissions;

import org.bukkit.plugin.Plugin;

public abstract class PermissionsProvider<T> {

    protected Plugin plugin;

    abstract public String getName();

    abstract public T getProviderClass();

    abstract public boolean isHooked();
}
