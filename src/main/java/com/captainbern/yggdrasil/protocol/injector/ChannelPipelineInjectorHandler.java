package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.protocol.Protocol;
import com.captainbern.yggdrasil.protocol.event.PacketEvent;
import com.captainbern.yggdrasil.reflection.refs.entity.EntityPlayerRef;
import com.captainbern.yggdrasil.reflection.refs.entity.craft.CraftEntityRef;
import com.captainbern.yggdrasil.reflection.refs.network.NetworkManagerRef;
import com.captainbern.yggdrasil.reflection.refs.network.PlayerConnectionRef;

import com.google.common.base.Preconditions;

import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;

import org.bukkit.entity.Player;

import java.util.concurrent.Callable;

public class ChannelPipelineInjectorHandler extends ChannelDuplexHandler implements Injector {

    // The player
    private Player player;

    // Is the custom handler injected in the pipeline or not?
    private boolean injected;

    // Is this injector open or closed?
    private boolean closed;

    // Is this player exempted?
    private boolean exempted;

    // TODO: make sure to init those in the constructor
    private Object nmsHandle;

    private Object playerConnection;
    private Object networkManager;
    private Channel channel;

    private InjectionManager injectionManager;

    public ChannelPipelineInjectorHandler(Player player, InjectionManager manager) {
        Preconditions.checkNotNull(player, "Player can't be NULL!");
        Preconditions.checkNotNull(manager, "InjectionManager can't be NULL!");

        this.player = player;
        this.injectionManager = manager;

        /**
         * Channel/network stuff
         */
        this.channel = getChannel();
    }

    @Override
    public boolean inject() {
        synchronized (networkManager) {

            if (this.closed)
                return false;
            if (!this.channel.isActive())
                return false;

            if(channel == null)
                throw new IllegalStateException("Channel is NULL! Cannot inject handler without a Channel!");

            // Yay, let's inject
            this.channel.pipeline().addBefore("packet_handler", "yggdrasil_packet_handler", this);

            injected = true;
            return true;
        }
    }

    @Override
    public void close() {
        if(!this.closed) {

            this.closed = true;

            if(injected) {
                // Remove our handler from the pipeline

                getChannel().eventLoop().submit(new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        getChannel().pipeline().remove(ChannelPipelineInjectorHandler.this);
                        return null;
                    }

                });

                injected = false;

            }
        }
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(Player player) {
        Preconditions.checkNotNull(player, "Player can't be NULL!");

        this.player = player;

        this.channel = getChannel();
    }

    public Object getNmsHandle() {
        if(this.nmsHandle == null)
            this.nmsHandle = CraftEntityRef.GET_HANDLE.invoke(getPlayer());
        return this.nmsHandle;
    }

    public Object getPlayerConnection() {
        if(this.playerConnection == null)
            this.playerConnection = EntityPlayerRef.PLAYER_CONNECTION.get(getNmsHandle());
        return this.playerConnection;
    }

    public Object getNetworkManager() {
        if(this.networkManager == null)
            this.networkManager = PlayerConnectionRef.NETWORK_MANAGER.get(getPlayerConnection());
        return this.playerConnection;
    }

    @Override
    public Channel getChannel() {
        if(this.channel == null)
            this.channel = NetworkManagerRef.CHANNEL_FIELD.get(getNetworkManager());
        return this.channel;
    }

    @Override
    public boolean isInjected() {
        return this.injected;
    }

    /**
     * Returns whether or not this player is exempted from any protocol operations.
     * (If he is exempted then any packet/protocol modifications won't take effect at his side)
     * @return
     */
    @Override
    public boolean isExempted() {
        return this.exempted;
    }

    @Override
    public void setExempted(boolean bool) {
        this.exempted = bool;
    }

    @Override
    public Protocol getProtocolPhase() {
        return Protocol.fromVanilla(NetworkManagerRef.PROTOCOL_PHASE.get(getNetworkManager()));
    }

    private InjectionManager getInjectionManager() {
        if(this.injectionManager == null)
            throw new RuntimeException("The InjectionManager is NULL!");

        return this.injectionManager;
    }

    // Override the channel read/write methods so we can capture packets
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(this.isExempted())  // Plugins can "exempt" a player. This means that this player won't be effected by any protocol changes.
            super.write(ctx, msg, promise);

        PacketEvent event = handleSend(this, msg);

        // Houston we have failed...
        if(event == null)
            super.write(ctx, msg, promise);

        // Some listener cancelled the event
        if(event.isCancelled())
            return;

        // Nope the event isn't cancelled but we need to update the packetObject
        msg = event.getPacket().getHandle();

        // Yay, everything went well, let's write the packet to the client
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(this.isExempted())
            super.channelRead(ctx, msg);

        PacketEvent event = handleReceive(this, msg);

        // Houston, we have a problem... Again
        if(event == null)
            super.channelRead(ctx, msg);

        // Did anyone request a cancel?
        if(event.isCancelled())
            return;

        // Seems like everything went well
        msg = event.getPacket().getHandle();

        // Everything went well
        super.channelRead(ctx, msg);
    }

    //@Override
    public PacketEvent handleReceive(Injector injector, Object packet) {
        return null;
    }

    //@Override
    public PacketEvent handleSend(Injector injector, Object packet) {
        return null;
    }

    @Override
    public String toString() {
        return "PacketHandler{player=" + this.player.getName() + "}";
    }
}
