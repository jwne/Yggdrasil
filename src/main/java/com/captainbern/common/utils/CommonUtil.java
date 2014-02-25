package com.captainbern.common.utils;

import com.captainbern.common.CommonPlugin;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.SafeField;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

public class CommonUtil {

    private static final FieldAccessor<CommandMap> COMMAND_MAP_FIELD_ACCESSOR = new SafeField<CommandMap>(SimplePluginManager.class, "commandMap");

    public static Class<?> getNMSClass(String className){
        return CommonPlugin.COMMON_SERVER.getNMSClass(className);
    }

    public static Class<?> getCBClass(String className){
        return CommonPlugin.COMMON_SERVER.getCBClass(className);
    }

    public static Class<?> getClass(String className) {
        return CommonPlugin.COMMON_SERVER.getClass(className);
    }

    public static CommandMap getCommandMap() {
        return COMMAND_MAP_FIELD_ACCESSOR.get(Bukkit.getPluginManager());
    }
}
