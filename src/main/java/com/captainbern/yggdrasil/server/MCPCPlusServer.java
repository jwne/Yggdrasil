package com.captainbern.yggdrasil.server;

import org.bukkit.Bukkit;

public class MCPCPlusServer extends SpigotServer {

    @Override
    public boolean init() {
        if(!super.init() || !Bukkit.getServer().getVersion().contains("MCPC-Plus")) {
            return false;
        }

        return true;
    }

    @Override
    public String getName() {
        return "MCPC+";
    }
}
