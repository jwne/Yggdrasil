package com.captainbern.common.protocol;

import com.captainbern.common.wrappers.AbstractWrapper;

public class Packet extends AbstractWrapper {

    protected PacketType packetType;
    protected transient Object packetHandle;

    public Packet(PacketType packetType) {
        super(null);
        this.packetType = packetType;
    }

    // TODO: add several utilities
}
