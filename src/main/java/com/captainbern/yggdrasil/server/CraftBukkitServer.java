package com.captainbern.yggdrasil.server;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.reflection.FieldAccessor;
import com.captainbern.yggdrasil.reflection.SafeField;
import com.captainbern.yggdrasil.reflection.utility.CommonReflection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

public class CraftBukkitServer implements CommonServer {

    private static final FieldAccessor<CommandMap> COMMAND_MAP_FIELD_ACCESSOR = new SafeField<CommandMap>(SimplePluginManager.class, "commandMap");

    public String MC_VERSION;

    public int MC_VERSION_NUMERIC;

    @Override
    public boolean init() {
        String serverPath = Bukkit.getServer().getClass().getName();

        if(!serverPath.startsWith(Yggdrasil.getCraftBukkitRoot())) {
            return false;
        }

        final String PACKAGE_NAME = Bukkit.getServer().getClass().getPackage().getName();
        MC_VERSION = PACKAGE_NAME.substring(PACKAGE_NAME.lastIndexOf('.') + 1);

        MC_VERSION_NUMERIC = Integer.valueOf(MC_VERSION.replaceAll("[^0-9]", ""));

        return true;
    }

    @Override
    public boolean postInit() {
        return false;
    }

    @Override
    public Class getClass(String name) {
        try{
            return CommonReflection.getClass(name);
        }catch(Exception e){
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to find matching class for: " + name);
            return null;
        }
    }

    @Override
    public Class getNMSClass(String name) {
        return CommonReflection.getMinecraftClass(name);
    }

    @Override
    public Class getCBClass(String name) {
        return CommonReflection.getCraftBukkitClass(name);
    }

    @Override
    public String getName() {
        return "CraftBukkit";
    }

    @Override
    public int getVersion() {
        return MC_VERSION_NUMERIC;
    }

    @Override
    public String getMCVersion() {
        return MC_VERSION;
    }

    @Override
    public boolean isCompatible() {
        return (Yggdrasil.getSupportedVersionNumeric() == MC_VERSION_NUMERIC) ? true : false;
    }

    @Override
    public CommandMap getCommandMap() {
        return COMMAND_MAP_FIELD_ACCESSOR.get(Bukkit.getPluginManager());
    }

    @Override
    public ServerBrand getServerBrand() {
        return ServerBrand.CRAFTBUKKIT;
    }
}
