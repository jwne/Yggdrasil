package com.captainbern.common.protocol;

import com.captainbern.common.reflection.ClassTemplate;

public class Packet {

    private PacketType packetType;
    private Class<?> packetClass;
    private Object packetHandle;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
        this.packetClass = packetType.getPacketClass();
        this.packetHandle = new ClassTemplate(this.packetClass).new0Instance();
    }

    // TODO: add several utilities
}
