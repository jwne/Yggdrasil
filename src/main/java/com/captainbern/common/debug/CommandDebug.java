package com.captainbern.common.debug;

import com.captainbern.common.command.CommandContext;
import com.captainbern.common.command.core.Command;
import com.captainbern.common.command.core.CommandPermissions;
import com.captainbern.common.protocol.PacketType;
import com.captainbern.common.protocol.Protocol;
import com.captainbern.common.protocol.event.PacketAdapter;
import com.captainbern.common.protocol.event.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandDebug {

    @Command(
            name = "debug",
            aliases = {"deb", "d"},
            description = "A command which allows you to debug various stuff",
            anyFlags = true
    )
    @CommandPermissions("cbcommonlib.debug")
    public static void debug(final CommandContext context, final CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "I failed at debugging");

        if(context.getString(0).equalsIgnoreCase("protocol")) {
            // debug protocol
            if(context.hasFlag('c')) {
                // debug client packets
            } else if(context.hasFlag('s')) {
                //debug server packets
            }
        }
    }

    private class DebugPacketAdapter extends PacketAdapter {

        public DebugPacketAdapter(Plugin plugin, PacketType... packetTypes) {
            super(plugin, packetTypes);
        }

        @Override
        public void onPacketSending(PacketEvent event) {

        }

        @Override
        public void onPacketReceiving(PacketEvent event) {

        }
    }
}
