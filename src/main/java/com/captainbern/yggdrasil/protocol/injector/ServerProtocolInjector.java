package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.protocol.event.PacketEvent;
import net.minecraft.util.io.netty.channel.*;
import org.bukkit.plugin.Plugin;

public class ServerProtocolInjector implements ChannelListener {

    private volatile boolean injected;
    private volatile boolean closed;

    private InjectionManager injectionManager;
    private ListenerInvoker listenerInvoker;

    public ServerProtocolInjector(Plugin plugin, ListenerInvoker listenerInvoker) {
        this.injectionManager = new InjectionManager(plugin);
        this.listenerInvoker = listenerInvoker;
    }

    /**
     * Credits to ProtocolLib for this hack
     */
    private synchronized void inject() {
        if(injected)
            throw new IllegalStateException("Cannot inject twice");

        final ChannelInboundHandlerAdapter endInit = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {

            }
        };

        final ChannelInboundHandler beginInit = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {

            }
        };

        final ChannelHandler connectionHandler = new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {

            }
        };

    }

    @Override
    public PacketEvent onPacketSending(Injector injector, Object packet) {
        return null;
    }

    @Override
    public PacketEvent onPacketReceiving(Injector injector, Object packet) {
        return null;
    }

    @Override
    public boolean hasListener(Class<?> packetClass) {
        return false;
    }
}
