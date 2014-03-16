package com.captainbern.common.protocol2;

import com.captainbern.common.protocol.event.PacketEvent;
import com.captainbern.common.protocol.event.PacketListener;
import com.captainbern.common.protocol2.injector.InjectionManager;
import com.google.common.base.Preconditions;

import java.util.concurrent.ConcurrentSkipListSet;

public class PacketListenerList extends ConcurrentSkipListSet<PacketListener> {

    private InjectionManager injectionManager;

    public PacketListenerList(InjectionManager injectionManager) {
        Preconditions.checkNotNull(injectionManager, "InjectionManager can't be NULL!");

        this.injectionManager = injectionManager;
    }

    public InjectionManager getInjectionManager() {
        return this.injectionManager;
    }

    public PacketEvent handleReceive(PacketEvent event) {
        return null;
    }
}
