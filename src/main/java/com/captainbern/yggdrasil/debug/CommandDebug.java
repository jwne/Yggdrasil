package com.captainbern.yggdrasil.debug;

import com.captainbern.yggdrasil.command.CommandContext;
import com.captainbern.yggdrasil.command.core.Command;
import com.captainbern.yggdrasil.command.core.CommandPermissions;
import com.captainbern.yggdrasil.protocol.event.PacketAdapter;
import org.bukkit.command.CommandSender;

public class CommandDebug {

    @Command(
            name = "debug",
            aliases = {"deb", "d"},
            description = "A command which allows you to debug various stuff",
            anyFlags = true
    )
    @CommandPermissions("cbcommonlib.debug")
    public static void debug(final CommandContext context, final CommandSender sender) {

    }
}
