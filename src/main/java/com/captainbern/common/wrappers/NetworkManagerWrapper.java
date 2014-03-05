package com.captainbern.common.wrappers;

import com.captainbern.common.reflection.NMSClassTemplate;
import com.captainbern.common.reflection.refs.NetworkManagerRef;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.net.SocketAddress;

public class NetworkManagerWrapper extends AbstractWrapper {

    private final Player player;
    private final PlayerConnectionWrapper playerConnectionWrapper;

    public NetworkManagerWrapper(Player player, PlayerConnectionWrapper playerConnectionWrapper) {
        super(NMSClassTemplate.create("NetworkManager").getType());

        this.player = player;
        this.playerConnectionWrapper = playerConnectionWrapper;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerConnectionWrapper getPlayerConnection() {
        return this.playerConnectionWrapper;
    }

    public Channel getChannel() {
        return NetworkManagerRef.CHANNEL_FIELD.get(getHandle());
    }

    public SocketAddress getSocketAddress() {
        return NetworkManagerRef.SOCKET_FIELD.get(getHandle());
    }
}
