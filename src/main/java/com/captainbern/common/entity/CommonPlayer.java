package com.captainbern.common.entity;

import com.captainbern.common.chat.ChatMessage;
import com.captainbern.common.collection.PlayerHashMap;
import com.captainbern.common.protocol.Packet;
import com.captainbern.common.reflection.refs.CraftPlayerRef;
import com.captainbern.common.wrappers.NetworkManagerWrapper;
import com.captainbern.common.wrappers.PlayerConnectionWrapper;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;

public class CommonPlayer {

    private static final PlayerHashMap<CommonPlayer> players = new PlayerHashMap<CommonPlayer>();

    public static CommonPlayer get(Player player) {
        if(!players.containsKey(player)) {
            players.put(player, new CommonPlayer(player));
        }
        return players.get(player);
    }

    private Object nms_handle;

    private final Player player;

    private PlayerConnectionWrapper playerConnectionWrapper;

    private CommonPlayer(Player player) {
        this.player = player;
        this.playerConnectionWrapper = new PlayerConnectionWrapper(player);
        this.nms_handle = CraftPlayerRef.GET_HANDLE.invoke(player);
    }

    public Player asBukkitPlayer() {
        return this.player;
    }

    public Object getNMSHandle() {
        return this.nms_handle;
    }

    public void sendPacket(Object packet) {
        getChannel().pipeline().writeAndFlush(packet);
    }

    public void sendPacket(Packet packet) {
        this.sendPacket(packet.getHandle());
    }

    public void receivePacket(Object packet) {
        getChannel().pipeline().context("encoder").fireChannelRead(packet);
    }

    public void receivePacket(Packet packet) {
        this.receivePacket(packet.getHandle());
    }

    public void sendFancyMessage(ChatMessage message) {

    }

    public PlayerConnectionWrapper getPlayerConnection() {
        return this.playerConnectionWrapper;
    }

    public NetworkManagerWrapper getNetworkManager() {
        return getPlayerConnection().getNetworkManagerWrapper();
    }

    public Channel getChannel() {
        return getNetworkManager().getChannel();
    }
}
