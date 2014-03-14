package com.captainbern.common.protocol2;

import com.captainbern.common.protocol.event.PacketListener;
import com.captainbern.common.protocol2.injector.netty.ChannelPipelineInjector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtocolManager {

    private static ProtocolManager INSTANCE;

    private static volatile List<ChannelPipelineInjector> injectorList = new ArrayList<ChannelPipelineInjector>();

    private static volatile List<ChannelPipelineInjector> injectors = Collections.synchronizedList(injectorList);

    public ProtocolManager() {
        INSTANCE = this;
    }

    public void registerListener(PacketListener listener) {

    }
}
