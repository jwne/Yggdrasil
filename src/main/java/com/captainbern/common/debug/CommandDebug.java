package com.captainbern.common.debug;

import com.captainbern.common.command.CommandContext;
import com.captainbern.common.command.core.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandDebug {

    @Command(
            name = "cbdebug",
            aliases = {"cbd", "d"},
            description = "A command which allows you to debug various stuff"
    )
    public static void cbdebug(final CommandContext context, final CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Worked :D");
    }
}
