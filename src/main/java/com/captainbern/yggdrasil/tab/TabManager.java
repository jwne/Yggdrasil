package com.captainbern.yggdrasil.tab;

import com.captainbern.yggdrasil.internal.Yggdrasil;
import com.captainbern.yggdrasil.protocol.event.Packet;
import com.captainbern.yggdrasil.protocol.PacketType;
import com.captainbern.yggdrasil.protocol.event.PacketAdapter;
import com.captainbern.yggdrasil.protocol.event.PacketEvent;

public class TabManager {

    public TabManager(Yggdrasil yggdrasil) {
        Yggdrasil.getProtocolManager().registerPacketListener(new PacketAdapter(yggdrasil, PacketType.Play.Server.PLAYER_INFO) {

            @Override
            public void onPacketSending(PacketEvent event) {
                if(!event.getPacketType().equals(PacketType.Play.Server.PLAYER_INFO))
                    return;

                Packet packet = event.getPacket();
            }
        });
    }
}
