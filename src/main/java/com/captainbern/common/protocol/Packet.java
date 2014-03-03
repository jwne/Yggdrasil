package com.captainbern.common.protocol;

public class Packet {

    protected PacketType packetType;
    protected transient Object packetHandle;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    // TODO: add several utilities
}
