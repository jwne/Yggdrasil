package com.captainbern.common.protocol2.injector;

import com.captainbern.common.protocol2.Protocol;
import com.captainbern.common.protocol2.event.PacketEvent;
import com.captainbern.common.reflection.refs.entity.EntityPlayerRef;
import com.captainbern.common.reflection.refs.entity.craft.CraftEntityRef;
import com.captainbern.common.reflection.refs.network.NetworkManagerRef;
import com.captainbern.common.reflection.refs.network.PlayerConnectionRef;

import com.google.common.base.Preconditions;

import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;

import org.bukkit.entity.Player;

public class ChannelPipelineInjectorHandler extends ChannelDuplexHandler implements ChannelPipelineInjector {

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

    private ListenerInvoker invoker;

    private ChannelInjector listener;

    public ChannelPipelineInjectorHandler(Player player, InjectionManager manager, ChannelInjector listener) {
        Preconditions.checkNotNull(player, "Player can't be NULL!");
        Preconditions.checkNotNull(manager, "InjectionManager can't be NULL!");
        Preconditions.checkNotNull(listener, "The ChannelInjector can't be NULL!");

        this.player = player;
        this.listener = listener;

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
            this.channel.pipeline().addAfter("packet_handler", getHandlerName(), this);

            injected = true;
            return true;
        }
    }

    @Override
    public boolean close() {
        if(this.closed) {

            // Remove our handler from the pipeline
            try {
                getChannel().pipeline().remove(getHandlerName());
                this.closed = true;
            } catch (Throwable t) {
                // this should never be called, but if so..
                this.closed = false;
            }
        }
        return false;
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

    private String getHandlerName() {
        return toString();
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
    public PacketEvent handleReceive(ChannelPipelineInjector injector, Object packet) {
        return this.listener.onPacketReceive(injector, packet);
    }

    //@Override
    public PacketEvent handleSend(ChannelPipelineInjector injector, Object packet) {
        return this.listener.onPacketSend(injector, packet);
    }

    @Override
    public String toString() {
        return "PacketHandler{player=" + this.player.getName() + "}";
    }
}
