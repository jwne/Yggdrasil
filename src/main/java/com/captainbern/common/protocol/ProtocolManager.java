package com.captainbern.common.protocol;

import com.captainbern.common.protocol.event.PacketListener;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class ProtocolManager {

    abstract public void registerPacketListener(PacketListener packetListener, Plugin plugin);

    abstract public void removePacketListener(PacketListener packetListener);

    abstract public void removePacketListeners(Plugin plugin);

    abstract public void sendPacket(Packet packet, Player player);

    abstract public void receivePacket(Packet packet, Player player);

    abstract public ImmutableList<PacketListener> getPacketListeners();

    abstract public boolean isClosed();

}
