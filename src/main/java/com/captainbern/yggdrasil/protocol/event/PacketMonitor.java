package com.captainbern.yggdrasil.protocol.event;

import org.bukkit.plugin.Plugin;

public interface PacketMonitor {

    public PacketTypeSet getSendingWhiteList();

    public PacketTypeSet getReceivingWhiteList();

    public Plugin getPlugin();

    public void onPacketSend(PacketEvent event);

    public void onPacketReceive(PacketEvent event);
}
