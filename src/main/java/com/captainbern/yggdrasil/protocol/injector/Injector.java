package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.protocol.Protocol;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;

public interface Injector {

    public boolean inject();

    public void close();

    public Player getPlayer();

    public void setPlayer(Player player);

    public Channel getChannel();

    public boolean isInjected();

    public boolean isExempted();

    public void setExempted(boolean bool);

    public Protocol getProtocolPhase();
}
