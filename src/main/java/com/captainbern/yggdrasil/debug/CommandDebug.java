package com.captainbern.yggdrasil.debug;

import com.captainbern.yggdrasil.command.CommandContext;
import com.captainbern.yggdrasil.command.core.Command;
import com.captainbern.yggdrasil.command.core.CommandPermissions;
import com.captainbern.yggdrasil.reflection.utility.CommonReflection;
import org.bukkit.command.CommandSender;

public class CommandDebug {

    @Command(
            name = "debug",
            aliases = {"deb", "d"},
            description = "A command which allows you to debug various stuff",
            anyFlags = true
    )
    @CommandPermissions("yggdrasil.debug")
    public static void debug(final CommandContext context, final CommandSender sender) {
        if(context.hasFlag('r')) {
            sender.sendMessage("Reflection debug values:");
            sender.sendMessage("Version tag: " + CommonReflection.getVersionTag());
            sender.sendMessage("Craftbukkit: " + CommonReflection.getCraftBukkitPackage());
            sender.sendMessage("NMS: " + CommonReflection.getMinecraftPackage());

            // test class stuff:
            sender.sendMessage("CraftServer: " + CommonReflection.getCraftServerClass().getCanonicalName());
            sender.sendMessage("MinecraftServer: " + CommonReflection.getMinecraftClass("MinecraftServer").getCanonicalName());
            sender.sendMessage("done");
            return;
        }
        //use this :p
        letsTestMethodAddStuff();
        sender.sendMessage("Please enter a valid flag: " + "-r, ");
    }

    public static void letsTestMethodAddStuff() {
        System.out.println("test?");
    }
}
