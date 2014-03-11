package com.captainbern.common.protocol;

import com.captainbern.common.wrappers.AbstractWrapper;

public class Packet extends AbstractWrapper {

    protected PacketType packetType;
    protected transient Object packetHandle;

    public Packet(PacketType packetType) {
        super(packetType.getPacketClass());
        this.packetType = packetType;
    }

    public Packet(Object packet, PacketType type) {
        super(packet.getClass());
    }

    // TODO: add several utilities
}
