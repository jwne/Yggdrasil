package com.captainbern.common.protocol;

import com.captainbern.common.protocol.event.PacketListener;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class PacketListenerMap {

    private ConcurrentMap<PacketType, Set<PacketListener>> listeners;

    public PacketListenerMap() {
        this.listeners = Maps.newConcurrentMap();
    }

    public void addListener(PacketListener packetListener, PacketList packetList) {
        for(PacketType packetType : packetList.getTypes()) {
            Set<PacketListener> list = this.listeners.get(packetType);

            if(list == null) {
                Set<PacketListener> packetTypeSet = new HashSet<PacketListener>();

                list = this.listeners.putIfAbsent(packetType, packetTypeSet);
            }

            list.add(packetListener);
        }
    }

    public Collection<PacketListener> getListenersFor(PacketType packetType) {
        return this.listeners.get(packetType);
    }

    public Collection<Set<PacketListener>> values() {
        return this.listeners.values();
    }

    public Set<PacketType> keySet() {
        return this.listeners.keySet();
    }
}
