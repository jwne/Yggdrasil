package com.captainbern.yggdrasil.server;

public class SpigotServer extends CraftBukkitServer {

    @Override
    public boolean init() {
        if(!super.init()) {
            return false;
        }
        try{
            Class.forName("org.spigotmc.SpigotConfig");
            return true;
        }catch(ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "Spigot";
    }

    @Override
    public ServerBrand getServerBrand() {
        return ServerBrand.SPIGOT;
    }
}
