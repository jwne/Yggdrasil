package com.captainbern.common.protocol2.injector;

import com.captainbern.common.protocol2.event.PacketEvent;

public interface ChannelInjector {

    public PacketEvent onPacketSend(ChannelPipelineInjector injector, Object packet);

    public PacketEvent onPacketReceive(ChannelPipelineInjector injector, Object packet);
}
