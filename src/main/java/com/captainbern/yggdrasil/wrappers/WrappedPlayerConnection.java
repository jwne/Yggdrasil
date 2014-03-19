package com.captainbern.yggdrasil.wrappers;

import com.captainbern.yggdrasil.reflection.SafeMethod;
import com.captainbern.yggdrasil.reflection.refs.entity.EntityPlayerRef;
import com.captainbern.yggdrasil.reflection.refs.entity.craft.CraftEntityRef;
import com.captainbern.yggdrasil.reflection.refs.network.PlayerConnectionRef;
import org.bukkit.entity.Player;

public class WrappedPlayerConnection extends AbstractWrapper {

    private final Player player;
    private final Object playerConnection;
    private final WrappedNetworkManager networkManagerWrapper;

    public WrappedPlayerConnection(Player player) {
        super(PlayerConnectionRef.TEMPLATE.getType());
        this.player = player;
        this.playerConnection = EntityPlayerRef.PLAYER_CONNECTION.get(CraftEntityRef.GET_HANDLE.invoke(player));

        setHandle(this.playerConnection);

        this.networkManagerWrapper = new WrappedNetworkManager(player, this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public WrappedNetworkManager getNetworkManagerWrapper() {
        return this.networkManagerWrapper;
    }

    public void sendPacket(Object packet) {
        PlayerConnectionRef.SEND_PACKET.invoke(getHandle(), packet);
    }

    public void receivePacket(Object packet) {
        SafeMethod<Void> receive = new SafeMethod<Void>(playerConnection.getClass(), "a", packet.getClass());
        receive.invoke(playerConnection, packet);
    }
}
