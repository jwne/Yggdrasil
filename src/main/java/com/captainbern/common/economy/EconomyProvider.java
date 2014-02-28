package com.captainbern.common.economy;

import org.bukkit.plugin.Plugin;

public abstract class EconomyProvider<T> {

    protected Plugin plugin = null;

    abstract public String getName();

    abstract public Class<T> getProviderClass();

    abstract public boolean isHooked();
}
