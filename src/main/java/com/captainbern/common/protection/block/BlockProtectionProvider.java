package com.captainbern.common.protection.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BlockProtectionProvider<T> {

    abstract public String getName();

    abstract public T getProviderClass();

    abstract public boolean isHooked();

    abstract public boolean isProtected(Block block);

    abstract public String getOwner(Block block);

    abstract public boolean setOwner(Block block, Player owner);

    abstract public boolean removeProtection(Block block);

}
