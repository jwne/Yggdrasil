package com.captainbern.common.protocol;

import com.captainbern.common.entity.CommonPlayer;
import com.captainbern.common.internal.CBCommonLib;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class PlayerInjector {

    private static boolean closed = false;

    public PlayerInjector(final CBCommonLib cbCommonLib) {
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                if(!isOpen())
                    return;
                injectPlayer(event.getPlayer());
            }

            @EventHandler
            public void onDisable(PluginDisableEvent event) {
                if(event.getPlugin().equals(cbCommonLib)) {
                    close();
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }, cbCommonLib);

        for(Player player : Bukkit.getOnlinePlayers()) {
            injectPlayer(player);
        }
    }

    public boolean isOpen() {
        return  !closed;
    }

    private void close() {
        if(!closed) {
            closed = true;

            for(Player player : Bukkit.getOnlinePlayers()) {
                unInjectPlayer(player);
            }
        }
    }

    public void injectPlayer(final Player player) {
        CommonPlayer commonPlayer = CommonPlayer.get(player);
        commonPlayer.getChannel().pipeline().addAfter("packet_handler", getProtocolHandlerName(), new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                try {
                    //System.out.print("Received packet: " + msg.getClass().getSimpleName());
                    //msg = onPacketInAsync(player, msg);
                } catch (Exception e) {
                    CBCommonLib.LOGGER_PROTOCOL.warning("Error in packet receive async.");
                }

                if (msg != null) {
                    super.channelRead(ctx, msg);
                }
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                try {
                    //System.out.print("Send packet: " + msg.getClass().getSimpleName());
                    // msg = onPacketOutAsync(player, msg);
                } catch (Exception e) {
                    CBCommonLib.LOGGER_PROTOCOL.warning("Error in packet send async.");
                }

                if (msg != null) {
                    super.write(ctx, msg, promise);
                }
            }
        });
    }

    public void unInjectPlayer(final Player player) {
        CommonPlayer commonPlayer = CommonPlayer.get(player);
        commonPlayer.getChannel().pipeline().remove(getProtocolHandlerName());
    }

    public String getProtocolHandlerName() {
        return "CBCommonLib - ProtocolHandler";
    }
}
