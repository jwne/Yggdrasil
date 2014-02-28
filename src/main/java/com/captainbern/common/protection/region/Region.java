package com.captainbern.common.protection.region;

import org.bukkit.Location;

import java.util.Set;

public abstract class Region<T> {

    abstract public String getId();

    abstract public Location getMinLocation();

    abstract public Location getMaxLocation();

    abstract public Set<RegionFlag> getFlags();

    abstract public void addOwner(String playerName);

    abstract public void addMember(String playerName);

    abstract public void removeOwner(String playerName);

    abstract public void removeMember(String playerName);

    abstract public boolean isOwner(String playerName);

    abstract public boolean isMember(String playerName);

    abstract public Set<String> getOwners();

    abstract public Set<String> getMembers();

    abstract public T getHandlerType();
}
