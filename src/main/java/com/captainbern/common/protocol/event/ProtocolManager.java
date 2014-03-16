package com.captainbern.common.protocol.event;

import com.captainbern.common.protocol.event.PacketEvent;
import com.captainbern.common.protocol.event.PacketListener;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class ProtocolManager {

    abstract public void registerPacketListener(PacketListener packetListener);

    abstract public void removePacketListener(PacketListener packetListener);

    abstract public void removePacketListeners(Plugin plugin);

    abstract public PacketEvent handleSend(Player player, Object packet);

    abstract public PacketEvent handleReceived(Player player, Object packet);

    abstract public ImmutableList<PacketListener> getPacketListeners();

    abstract public boolean isClosed();

}
