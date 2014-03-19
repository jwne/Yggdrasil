package com.captainbern.common.tab;

import com.captainbern.common.internal.Yggdrasil;
import com.captainbern.common.protocol.event.Packet;
import com.captainbern.common.protocol.PacketType;
import com.captainbern.common.protocol.event.PacketAdapter;
import com.captainbern.common.protocol.event.PacketEvent;

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
