package com.captainbern.common.protocol;

import com.captainbern.common.reflection.refs.EnumProtocolRef;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PacketRegistry {

    private BiMap<PacketType, Class<?>> typeToClass = HashBiMap.create();
    private Set<PacketType> serverPackets = Sets.newHashSet();
    private Set<PacketType> clientPackets = Sets.newHashSet();

    public PacketRegistry() {
        registerPackets();
    }

    public Map<PacketType, Class<?>> getClassLookup() {
        return Collections.unmodifiableMap(typeToClass);
    }

    public Map<Class<?>, PacketType> getTypeLookup() {
        return Collections.unmodifiableMap(typeToClass.inverse());
    }

    public Set<PacketType> getServerPackets() {
        return Collections.unmodifiableSet(serverPackets);
    }

    public Set<PacketType> getClientPackets() {
        return Collections.unmodifiableSet(clientPackets);
    }

    protected void registerPackets() {
        Object[] protocolTypes = EnumProtocolRef.TEMPLATE.getType().getEnumConstants();
        List<Map<Integer, Class<?>>> clientPackets = Lists.newArrayList();
        List<Map<Integer, Class<?>>> serverPackets = Lists.newArrayList();

        for(Object protocolType : protocolTypes) {
            clientPackets.add(EnumProtocolRef.getClientPackets(protocolType));
            serverPackets.add(EnumProtocolRef.getServerPackets(protocolType));
        }

        for(int i = 0; i < protocolTypes.length; i++) {
            Enum<?> protocolType = (Enum<?>) protocolTypes[i];
            setPackets(clientPackets.get(i), Protocol.fromVanilla(protocolType), Sender.CLIENT);
            setPackets(serverPackets.get(i), Protocol.fromVanilla(protocolType), Sender.SERVER);
        }
    }

    protected void setPackets(Map<Integer, Class<?>> map, Protocol protocol, Sender sender) {
        for(Map.Entry<Integer, Class<?>> entry : map.entrySet()) {
            PacketType type = PacketType.getTypeFrom(protocol, sender, entry.getKey());

            typeToClass.put(type, entry.getValue());
            if(sender == Sender.CLIENT) {
                clientPackets.add(type);
            }

            if(sender == Sender.SERVER) {
                serverPackets.add(type);
            }
        }
    }
}
