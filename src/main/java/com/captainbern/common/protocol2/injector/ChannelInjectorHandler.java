package com.captainbern.common.protocol2.injector;

import com.captainbern.common.protocol2.event.PacketEvent;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;

public class ChannelInjectorHandler implements ChannelInjector {

    private InjectionManager injectionManager;

    public ChannelInjectorHandler(InjectionManager injectionManager) {
        Preconditions.checkNotNull(injectionManager, "InjectionManager can't be NULL!");

        this.injectionManager = injectionManager;
    }

    public void injectPlayer(Player player) {
        this.injectionManager.registerInjector(player, this);
    }

    @Override
    public PacketEvent onPacketSend(ChannelPipelineInjector injector, Object packet) {
        return null;
    }

    @Override
    public PacketEvent onPacketReceive(ChannelPipelineInjector injector, Object packet) {
        return null;
    }
}
