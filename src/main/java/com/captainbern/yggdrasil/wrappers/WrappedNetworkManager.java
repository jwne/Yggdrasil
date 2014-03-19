package com.captainbern.yggdrasil.wrappers;

import com.captainbern.yggdrasil.reflection.refs.network.NetworkManagerRef;
import com.captainbern.yggdrasil.reflection.refs.network.PlayerConnectionRef;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.net.SocketAddress;

public class WrappedNetworkManager extends AbstractWrapper {

    private final Player player;
    private final WrappedPlayerConnection playerConnectionWrapper;

    public WrappedNetworkManager(Player player, WrappedPlayerConnection playerConnectionWrapper) {
        super(NetworkManagerRef.TEMPLATE.getType());

        this.player = player;
        this.playerConnectionWrapper = playerConnectionWrapper;

        setHandle(PlayerConnectionRef.NETWORK_MANAGER.get(playerConnectionWrapper.getHandle()));
    }

    public Player getPlayer() {
        return this.player;
    }

    public WrappedPlayerConnection getPlayerConnection() {
        return this.playerConnectionWrapper;
    }

    public Channel getChannel() {
        return NetworkManagerRef.CHANNEL_FIELD.get(getHandle());
    }

    public SocketAddress getSocketAddress() {
        return NetworkManagerRef.SOCKET_FIELD.get(getHandle());
    }
}
