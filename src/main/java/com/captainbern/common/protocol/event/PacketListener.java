package com.captainbern.common.protocol.event;

public interface PacketListener {

    public void onPacketSending(PacketEvent event);

    public void onPacketReceiving(PacketEvent event);
}
