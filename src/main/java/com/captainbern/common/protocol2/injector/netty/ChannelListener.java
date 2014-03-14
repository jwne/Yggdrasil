package com.captainbern.common.protocol2.injector.netty;

import com.captainbern.common.protocol.event.PacketEvent;

public interface ChannelListener {

    public PacketEvent handleReceive(Object packet, ChannelPipelineInjector injector);

    public PacketEvent handleSend(Object packet, ChannelPipelineInjector injector);
}
