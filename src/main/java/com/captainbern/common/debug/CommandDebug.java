package com.captainbern.common.debug;

import com.captainbern.common.command.CommandContext;
import com.captainbern.common.command.core.Command;
import com.captainbern.common.command.core.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandDebug {

    @Command(
            name = "debug",
            aliases = {"deb", "d"},
            description = "A command which allows you to debug various stuff"
    )
    @CommandPermissions("cbcommonlib.debug")
    public static void debug(final CommandContext context, final CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Worked :D");
    }
}
