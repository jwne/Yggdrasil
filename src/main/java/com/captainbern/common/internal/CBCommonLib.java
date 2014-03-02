package com.captainbern.common.internal;

import com.captainbern.common.BukkitPlugin;
import com.captainbern.common.ModuleLogger;
import com.captainbern.common.command.ObjectInstantiator;
import com.captainbern.common.debug.CommandDebug;
import com.captainbern.common.logging.CBCommonLibFormatter;
import com.captainbern.common.protocol.PacketType;
import com.captainbern.common.server.*;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class CBCommonLib extends BukkitPlugin {

    public static final String VERSION = "0.0.1";

    public static final String CODENAME = "Megatron";

    /**
     * Several loggers
     */
    public static final ModuleLogger LOGGER = new ModuleLogger("CBCommonLib");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");
    public static final ModuleLogger LOGGER_PROTOCOL = LOGGER.getModule("Protocol");

    /**
     * The supported minecraft version (packaged)
     */
    private static final String SUPPORTED_VERSION = "v1_7_R1";

    /**
     * The supported numeric version
     */
    private static final int SUPPORTED_VERSION_NUMERIC = 171;

    /**
     * The craftbukkit root
     */
    private static final String CRAFBUKKIT_ROOT = "org.bukkit.craftbukkit";

    /**
     * The NMS-root
     */
    private static final String MINECRAFT_ROOT = "net.minecraft.server";

    /**
     * The current instance
     */
    private static CBCommonLib instance;


    /**
     * The server brand
     */
    private static CommonServer commonServer;


    /**
     * Returns the current instance
     * @return
     */
    public static CBCommonLib getInstance() {
        if(instance == null) {
            throw new RuntimeException("Instance is NULL!");
        }
        return instance;
    }


    @Override
    public void load() {
        long start = System.currentTimeMillis();

        try {

            File logsFolder = new File(getDataFolder().getAbsolutePath() + File.separator + "logs");
            if (!logsFolder.exists()) {
                logsFolder.mkdirs();
            }
            File logFile = new File(getDataFolder().getAbsolutePath() + File.separator + "logs" + File.separator + "lib.log");
            if (logFile.exists()) {
                if (logFile.length() > 5242880L) {
                    logFile.delete();
                }
            }

            FileHandler fileHandler = new FileHandler(getDataFolder() + File.separator + "logs" + File.separator + "lib.log", true);
            CBCommonLibFormatter formatter = new CBCommonLibFormatter();
            fileHandler.setFormatter(formatter);

            LOGGER.addHandler(fileHandler);

        } catch (Exception e) {
            LOGGER.warning("Failed to initialize the logger correctly! (Logging still works but won't be saved to a file!)");
        }

        instance = this;
        commonServer = getCommonServer();

        if(commonServer == null) {
            LOGGER.warning("Failed to identify the server brand! (Cannot run on an unknown server! "
                    + "Report this error at: http://trouble.captainbern.com/");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            if(!commonServer.isCompatible()) {
                LOGGER.warning("It seems like this server is not compatible with " +
                        "this version of CBCommonLib! Let's try anyways...");
            }
        }

        // register commands
        getCommandManager().getCommandRegistrationService().setObjectInstantiator(new ObjectInstantiator());
        registerCommand(CommandDebug.class);

        LOGGER.info("Version: " + getLibVersion());
        LOGGER.info("Codename: " + getCodename());

        long done = System.currentTimeMillis();
        LOGGER.info("Enabled in: " + (done - start) + "ms");
    }


    /**
     * Returns a CommonServer object which contains several operations to define the server brand, version etc...
     * @return
     */
    public static CommonServer getCommonServer() {
        if(commonServer != null) {
            return commonServer;
        }

        CommonServer serverBrand = null;

        List<CommonServer> servers = new ArrayList<CommonServer>();
        servers.add(new MCPCPlusServer());
        servers.add(new SpigotServer());
        servers.add(new SportBukkitServer());
        servers.add(new CraftBukkitServer());
        servers.add(new UnknownServer());

        for(CommonServer server : servers) {
            if(server.init()) {
                serverBrand = server;
                break;
            }
        }
        return commonServer = serverBrand;
    }


    @Override
    public void unLoad() {

    }

    public static final String getCraftBukkitRoot() {
        return CRAFBUKKIT_ROOT;
    }

    public static final String getNMSRoot() {
        return MINECRAFT_ROOT;
    }

    public static final String getSupportedVersion() {
        return SUPPORTED_VERSION;
    }

    public static final int getSupportedVersionNumeric() {
        return SUPPORTED_VERSION_NUMERIC;
    }

    public static final String getLibVersion() {
        return VERSION;
    }

    public static final String getCodename() {
        return CODENAME;
    }
}

