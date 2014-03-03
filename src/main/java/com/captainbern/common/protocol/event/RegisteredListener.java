package com.captainbern.common.protocol.event;

import com.captainbern.common.protocol.ConnectionSide;
import com.captainbern.common.protocol.PacketType;

import java.util.Set;

public class RegisteredListener {

    private PacketListener packetListener;
    private Set<PacketType> packetTypes;
    private ConnectionSide connectionSide;

    public RegisteredListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    public void callSend(PacketEvent packetEvent) {

    }

    public void callReceive(PacketEvent packetEvent) {

    }
}
