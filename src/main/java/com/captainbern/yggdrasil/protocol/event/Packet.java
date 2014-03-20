package com.captainbern.yggdrasil.protocol.event;

import com.captainbern.yggdrasil.protocol.PacketType;
import com.captainbern.yggdrasil.wrappers.AbstractWrapper;

public class Packet extends AbstractWrapper {

    private final PacketType packetType;

    public Packet(Object handle) {
        this(handle, PacketType.getTypeFrom(handle.getClass()));
    }

    public Packet(Object handle, PacketType packetType) {
        super(packetType.getPacketClass());
        this.packetType = packetType;
        setHandle(handle);
    }

    public Packet(PacketType packetType) {
        super(packetType.getPacketClass());

        this.packetType = packetType;
    }

    public final PacketType getPacketType() {
        if(this.packetType == null) {
            throw new IllegalStateException("PacketType is NULL!");
        }
        return this.packetType;
    }
}
