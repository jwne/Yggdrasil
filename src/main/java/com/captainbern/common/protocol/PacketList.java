package com.captainbern.common.protocol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PacketList {

    private Set<PacketType> packetTypes;

    public PacketList() {
        this(null);
    }

    public PacketList(PacketType... types) {
        this.packetTypes = new HashSet<PacketType>();
        this.packetTypes.addAll(Arrays.asList(types));
    }

    public void add(PacketType... packetTypes) {
        for(PacketType packetType : packetTypes) {
            if(!this.packetTypes.contains(packetType)) {
                this.packetTypes.add(packetType);
            }
        }
    }

    public void remove(PacketType... packetType) {
        this.packetTypes.removeAll(packetTypes);
    }

    public void fill() {
        for(PacketType type : PacketType.values()) {
            if(this.packetTypes.contains(packetTypes))
                continue;

            this.packetTypes.add(type);
        }
    }

    public Set<PacketType> getTypes() {
        return this.packetTypes;
    }
}
