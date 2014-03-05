package com.captainbern.common.wrappers;

import com.captainbern.common.reflection.NMSClassTemplate;
import com.captainbern.common.reflection.SafeMethod;
import com.captainbern.common.reflection.refs.CraftPlayerRef;
import com.captainbern.common.reflection.refs.EntityPlayerRef;
import com.captainbern.common.reflection.refs.PlayerConnectionRef;
import org.bukkit.entity.Player;

public class PlayerConnectionWrapper extends AbstractWrapper {

    private final Player player;
    private final Object playerConnection;
    private final NetworkManagerWrapper networkManagerWrapper;

    public PlayerConnectionWrapper(Player player) {
        super(NMSClassTemplate.create("PlayerConnection").getType());
        this.player = player;
        this.playerConnection = EntityPlayerRef.PLAYER_CONNECTION.get(CraftPlayerRef.GET_HANDLE.invoke(player));

        setHandle(this.playerConnection);

        this.networkManagerWrapper = new NetworkManagerWrapper(player, this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public NetworkManagerWrapper getNetworkManagerWrapper() {
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
