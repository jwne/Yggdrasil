package com.captainbern.yggdrasil.protocol.injector;

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
}
