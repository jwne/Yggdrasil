package com.captainbern.common.protocol;

import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.protocol.event.PacketAdapter;
import com.captainbern.common.protocol.event.PacketEvent;
import com.captainbern.common.protocol.event.PacketListener;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class CommonProtocolManager extends ProtocolManager {

    private static PlayerInjector playerInjector;

    private static final PacketListenerMap packetListenerMap = new PacketListenerMap();

    public CommonProtocolManager(final CBCommonLib cbCommonLib) {
        playerInjector = new PlayerInjector(cbCommonLib);
    }

    @Override
    public void registerPacketListener(PacketListener packetListener, Plugin plugin) {

    }

    @Override
    public void removePacketListener(PacketListener packetListener) {

    }

    @Override
    public void removePacketListeners(Plugin plugin) {

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

    @Override
    public ImmutableList<PacketListener> getPacketListeners() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return !playerInjector.isOpen();
    }

    public PlayerInjector getPlayerInjector() {
        return playerInjector;
    }
}
