package com.captainbern.common.protocol2.injector;

import com.captainbern.common.protocol2.Protocol;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;

public interface ChannelPipelineInjector {

    public boolean inject();

    public boolean close();

    public Player getPlayer();

    public void setPlayer(Player player);

    public Channel getChannel();

    public boolean isInjected();

    public boolean isExempted();

    public void setExempted(boolean bool);

    public Protocol getProtocolPhase();
}
