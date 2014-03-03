package com.captainbern.common.protocol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PacketList {

    private Set<PacketType> packetTypes;

    public PacketList() {
        this.packetTypes = new HashSet<PacketType>();
    }

    public PacketList(PacketType... types) {
        this();
        this.packetTypes.addAll(Arrays.asList(types));
    }

    public void add(PacketType packetType) {
         if(!this.packetTypes.contains(packetType)) {
             this.packetTypes.add(packetType);
         }
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
