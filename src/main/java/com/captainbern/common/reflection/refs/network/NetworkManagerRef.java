package com.captainbern.common.reflection.refs.network;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;
import net.minecraft.util.io.netty.channel.Channel;

import java.net.SocketAddress;

public class NetworkManagerRef {

    /**
     * This class is a ref of:
     * https://github.com/Bukkit/mc-dev/blob/master/net/minecraft/server/NetworkManager.java
     */

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("NetworkManager");

    public static final FieldAccessor<Channel> CHANNEL_FIELD = TEMPLATE.getField("k");
    public static final FieldAccessor<SocketAddress> SOCKET_FIELD = TEMPLATE.getField("l");
    public static final FieldAccessor<Enum> PROTOCOL_PHASE = TEMPLATE.getField("n");
}
