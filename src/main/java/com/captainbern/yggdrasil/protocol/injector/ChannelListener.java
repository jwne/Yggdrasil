package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.protocol.event.PacketEvent;

public interface ChannelListener {

    public PacketEvent onPacketSending(Injector injector, Object packet);

    public PacketEvent onPacketReceiving(Injector injector, Object packet);

    public boolean hasListener(Class<?> packetClass);
}
