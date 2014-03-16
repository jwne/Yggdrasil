package com.captainbern.common.protocol.event;

import com.captainbern.common.internal.CBCommonLib;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommonProtocolManager extends ProtocolManager {

    private static PlayerInjector playerInjector;

    private static final PacketListenerMap packetListenerMap = new PacketListenerMap();

    public CommonProtocolManager(final CBCommonLib cbCommonLib) {
        playerInjector = new PlayerInjector(cbCommonLib);
    }

    @Override
    public void registerPacketListener(PacketListener packetListener) {
        packetListenerMap.addPacketListener(packetListener);
    }

    @Override
    public void removePacketListener(PacketListener packetListener) {
        packetListenerMap.removePacketListener(packetListener);
    }

    @Override
    public void removePacketListeners(Plugin plugin) {
        packetListenerMap.removePacketListeners(plugin);
    }

    @Override
    public PacketEvent handleSend(Player player, Object packet) {
        return packetListenerMap.handlePacketSend(player, packet);
    }

    @Override
    public PacketEvent handleReceived(Player player, Object packet) {
        return packetListenerMap.handlePacketReceived(player, packet);
    }

    @Override
    public ImmutableList<PacketListener> getPacketListeners() {
        return new ImmutableList.Builder<PacketListener>().addAll(packetListenerMap.values()).build();
    }

    @Override
    public boolean isClosed() {
        return !playerInjector.isOpen();
    }

    public PlayerInjector getPlayerInjector() {
        return playerInjector;
    }
}
