package com.captainbern.common.protocol;

import com.captainbern.common.protocol.event.PacketAdapter;
import com.captainbern.common.protocol.event.PacketListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class ProtocolManager {

    abstract public void registerPacketListener(PacketListener packetListener, Plugin plugin);

    abstract public void registerPacketAdapter(PacketAdapter adapter, Plugin plugin);

    abstract public void removePacketListener(PacketListener packetListener);

    abstract public void removePacketAdapter(PacketAdapter packetAdapter);

    abstract public Collection<PacketListener> getPacketListenersFor(Plugin plugin);

    abstract public Collection<PacketAdapter> getPacketAdaptersFor(Plugin plugin);

    abstract public void sendPacket(Packet packet, Player player);

    abstract public void receivePacket(Packet packet, Player player);

}
