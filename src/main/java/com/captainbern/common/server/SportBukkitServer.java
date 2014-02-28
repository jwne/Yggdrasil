package com.captainbern.common.server;

import org.bukkit.Bukkit;

public class SportBukkitServer extends CraftBukkitServer {

    @Override
    public boolean init() {
        if(!super.init()) {
            return false;
        }

        return Bukkit.getVersion().contains("SportBukkit");
    }

    @Override
    public String getName() {
        return "SportBukkit";
    }
}
