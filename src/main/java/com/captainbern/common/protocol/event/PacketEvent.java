package com.captainbern.common.protocol.event;

import com.captainbern.common.protocol.Packet;
import com.captainbern.common.protocol.PacketType;
import org.bukkit.entity.Player;

public class PacketEvent {

    private Object packetHandle;
    private PacketType packetType;

    private Packet packet;

    private Player player;

    public PacketEvent(Object packetHandle, Packet packet, Player player) {
        this.packetHandle = packetHandle;
        this.packet = packet;
        this.player = player;

        this.packetType = PacketType.getTypeFrom(packetHandle.getClass());
    }

    public Packet getPacket() {
        return this.packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PacketType getPacketType() {
        return this.packetType;
    }

    public Object getPacketHandle() {
        return this.packetHandle;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
