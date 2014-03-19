package com.captainbern.common.protocol.event;

import com.captainbern.common.protocol.PacketType;
import com.captainbern.common.protocol.event.Packet;
import org.bukkit.entity.Player;

public class PacketEvent {

    private PacketType packetType;
    private boolean cancelled;

    private Packet packet;

    private Player player;

    public PacketEvent(Packet packet, Player player) {
        this.packet = packet;
        this.player = player;

        this.packetType = PacketType.getTypeFrom(packet.getHandle().getClass());
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

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean bool) {
        this.cancelled = bool;
    }
}
