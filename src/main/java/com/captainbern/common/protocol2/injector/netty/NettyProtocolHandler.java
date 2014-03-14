package com.captainbern.common.protocol2.injector.netty;

import com.captainbern.common.protocol.event.PacketListener;
import com.captainbern.common.protocol2.ProtocolManager;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class NettyProtocolHandler {

    private static ConcurrentHashMap<Plugin, PacketListener> pluginToListener = new ConcurrentHashMap<Plugin, PacketListener>();

    public NettyProtocolHandler(ProtocolManager manager) {

    }
}
