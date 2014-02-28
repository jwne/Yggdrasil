package com.captainbern.common.server;

import org.bukkit.command.CommandMap;

public interface CommonServer {

    public boolean init();

    public boolean postInit();

    public Class getClass(String name);

    public Class getNMSClass(String name);

    public Class getCBClass(String name);

    public String getName();

    public int getVersion();

    public String getMCVersion();

    public boolean isCompatible();

    public CommandMap getCommandMap();
}
