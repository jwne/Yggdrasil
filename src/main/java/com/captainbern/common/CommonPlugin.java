package com.captainbern.common;

import com.captainbern.common.command.CommandManager;
import com.captainbern.common.command.DefaultCommandExecutor;
import com.captainbern.common.command.ObjectInstantiator;
import com.captainbern.common.debug.CommandDebug;
import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.server.*;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class CommonPlugin extends BukkitPlugin {

    public static final String SUPPORTED_VERSION = "v1_7_R1";
    public static final int SUPPORTED_VERSION_NUMERIC = 171;

    public static final String CRAFBUKKIT_ROOT = "org.bukkit.craftbukkit";
    public static final String MINECRAFT_ROOT = "net.minecraft.server";

    public static CommonServer COMMON_SERVER;

    public CommonPlugin() {
        List<CommonServer> servers = new ArrayList<CommonServer>();
        servers.add(new MCPCPlusServer());
        servers.add(new SpigotServer());
        servers.add(new CraftBukkitServer());
        servers.add(new UnknownServer());

        for(CommonServer server : servers) {
            if(server.init()) {
                COMMON_SERVER = server;
                break;
            }
        }

        if(COMMON_SERVER == null) {
            CBCommonLib.LOGGER.warning("Failed to identify the server brand! The plugin" +
                    "may not work correctly now! -> disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            if(!COMMON_SERVER.isCompatible()) {
                CBCommonLib.LOGGER.warning("The plugin is not compatible with this version of Minecraft!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }
    }

    @Override
    public void onEnable() {
        new CommonPlugin();
        new CBCommonLib(COMMON_SERVER, this);

        CommandManager commandManager = new CommandManager(this);
        commandManager.setExecutor(new DefaultCommandExecutor(commandManager));
        commandManager.register(CommandDebug.class);
        commandManager.getCommandRegistrationService().setObjectInstantiator(new ObjectInstantiator());
    }
}
