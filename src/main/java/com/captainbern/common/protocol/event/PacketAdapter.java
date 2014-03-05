package com.captainbern.common.protocol.event;

import com.captainbern.common.protocol.PacketList;
import com.captainbern.common.protocol.PacketType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class PacketAdapter implements PacketListener {

    protected Plugin plugin;
    protected Set<PacketType> types;
    protected PacketList sendList;
    protected PacketList receiveList;

    public PacketAdapter(Plugin plugin, PacketType... packetTypes) {
        this.plugin = plugin;
        this.types = new HashSet<PacketType>(Arrays.asList(packetTypes));
        this.sendList = new PacketList(packetTypes);
        this.receiveList =  new PacketList(packetTypes);

        for(PacketType type : packetTypes) {
            if(type.isClient()) {
                this.receiveList.add(type);
            } else if(type.isServer()) {
                this.sendList.add(type);
            }
        }
    }

    public Set<PacketType> getpacketTypes() {
        return this.types;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public PacketList getSendPackets() {
        return this.sendList;
    }

    @Override
    public PacketList getReceivePackets() {
        return this.receiveList;
    }
}
