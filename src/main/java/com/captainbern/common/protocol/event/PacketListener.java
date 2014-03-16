package com.captainbern.common.protocol.event;

import org.bukkit.plugin.Plugin;

public interface PacketListener {

    public void onPacketSending(PacketEvent event);

    public void onPacketReceiving(PacketEvent event);

    public Plugin getPlugin();

    public PacketList getSendPackets();

    public PacketList getReceivePackets();
}
