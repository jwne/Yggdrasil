package com.captainbern.common.protocol;

import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.protocol.event.PacketAdapter;
import com.captainbern.common.protocol.event.PacketEvent;
import com.captainbern.common.protocol.event.PacketListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class CommonProtocolManager extends ProtocolManager {

    private static final PacketListenerMap packetListenerMap = new PacketListenerMap();

    public CommonProtocolManager(final CBCommonLib cbCommonLib) {
        new PlayerInjector(cbCommonLib);
    }

    @Override
    public void registerPacketListener(PacketListener packetListener, Plugin plugin) {
        packetListenerMap.addListener(packetListener, packetListener.getReceivePackets());
        packetListenerMap.addListener(packetListener, packetListener.getSendPackets());
    }

    @Override
    public void registerPacketAdapter(PacketAdapter adapter, Plugin plugin) {
        registerPacketListener(adapter, plugin);
    }

    @Override
    public void removePacketListener(PacketListener packetListener) {

    }

    @Override
    public void removePacketAdapter(PacketAdapter packetAdapter) {

    }

    @Override
    public Collection<PacketListener> getPacketListenersFor(Plugin plugin) {
        return null;
    }

    @Override
    public Collection<PacketAdapter> getPacketAdaptersFor(Plugin plugin) {
        return null;
    }

    @Override
    public void sendPacket(Packet packet, Player player) {
        for(PacketListener listener : packetListenerMap.getListenersFor(packet.packetType)) {
            listener.onPacketSending(new PacketEvent(packet.packetHandle, packet, player));
        }
    }

    @Override
    public void receivePacket(Packet packet, Player player) {

    }
}
