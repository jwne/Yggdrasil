package com.captainbern.common.protection.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class BlockProtectionProvider<T> {

    protected Plugin plugin = null;

    abstract public String getName();

    abstract public Class<T> getProviderClass();

    abstract boolean isHooked();

    abstract boolean isProtected(Block block);

    abstract String getOwner(Block block);

    abstract boolean setOwner(Block block, Player owner);

    abstract boolean removeProtection(Block block);

}
