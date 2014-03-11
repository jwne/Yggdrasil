package com.captainbern.common.protocol;

import com.captainbern.common.protocol.event.PacketEvent;
import com.captainbern.common.protocol.event.PacketListener;
import com.captainbern.common.protocol.event.PacketMonitor;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class PacketListenerMap {

    private ConcurrentMap<PacketType, Set<PacketListener>> packetTypeToListener;
    private ConcurrentMap<PacketType, Set<PacketMonitor>> packetTypeToMonitor;
    private ConcurrentMap<Plugin, Set<PacketListener>> pluginToListener;
    private ConcurrentMap<Plugin, Set<PacketMonitor>> pluginToMonitor;

    public PacketListenerMap() {
        this.packetTypeToListener = Maps.newConcurrentMap();
        this.pluginToListener = Maps.newConcurrentMap();
    }

    public void addPacketMonitor(PacketMonitor packetMonitor) {
        if(packetMonitor == null)
            throw new IllegalArgumentException("PacketMonitor can't be NULL!");
        if(packetMonitor.getPlugin() == null)
            throw new IllegalArgumentException("Plugin can't be NULL!");

        // Handle the server packets
    }

    public void addPacketListener(PacketListener packetListener) {
        if(packetListener == null)
            throw new IllegalArgumentException("PacketListener can't be NULL!");
        if(packetListener.getPlugin() == null)
            throw new IllegalArgumentException("Plugin can't be NULL!");

        // Handle the server packets
        for(PacketType type : packetListener.getSendPackets().getTypes()) {

            // Add to packetType -> listener map
            Set<PacketListener> listeners = this.packetTypeToListener.get(type);
            if(listeners == null) {
                listeners = new HashSet<PacketListener>();
                this.pluginToListener.putIfAbsent(packetListener.getPlugin(), listeners);
            }

            listeners.add(packetListener);

            // Add to plugin -> listener map
            Set<PacketListener> set = this.pluginToListener.get(packetListener.getPlugin());
            if(set == null) {
                set = new HashSet<PacketListener>();
                this.pluginToListener.putIfAbsent(packetListener.getPlugin(), set);
            }

            set.add(packetListener);
        }

        // Handle the client packets
        for(PacketType type : packetListener.getReceivePackets().getTypes()) {

            // Add to packetType -> listener map
            Set<PacketListener> listeners = this.packetTypeToListener.get(type);
            if(listeners == null) {
                listeners = new HashSet<PacketListener>();
                this.pluginToListener.putIfAbsent(packetListener.getPlugin(), listeners);
            }

            listeners.add(packetListener);

            // Add to plugin -> listener map
            Set<PacketListener> set = this.pluginToListener.get(packetListener.getPlugin());
            if(set == null) {
                set = new HashSet<PacketListener>();
                this.pluginToListener.putIfAbsent(packetListener.getPlugin(), set);
            }

            set.add(packetListener);
        }
    }

    public PacketEvent handlePacketSend(Player player, Object packet) {
        if(player == null || packet == null) {
            return null;
        }

        PacketType type = PacketType.getTypeFrom(packet.getClass());
        Set<PacketListener> listeners = this.packetTypeToListener.get(type);

        Packet wrappedPacket = new Packet(packet, type);
        PacketEvent event = new PacketEvent(packet, wrappedPacket, player);

        if(listeners != null) {
            for(PacketListener listener : listeners) {
                listener.onPacketSending(event);
            }

            //TODO: handle monitors   (PS: make sure to check if the event was cancelled in the injector)
        }
        return event;
    }

    public PacketEvent handlePacketRecieve(Player player, Object packet) {
        if(player == null || packet == null) {
            return null;
        }

        PacketType type = PacketType.getTypeFrom(packet.getClass());
        Set<PacketListener> listeners = this.packetTypeToListener.get(type);

        Packet wrappedPacket = new Packet(packet, type);
        PacketEvent event = new PacketEvent(packet, wrappedPacket, player);

        if(listeners != null) {
            for(PacketListener listener : listeners) {
                listener.onPacketReceiving(event);
            }

            //TODO: handle monitors   (PS: make sure to check if the event was cancelled in the injector)
        }
        return event;
    }

    /* TODO: add stuff for receive, add getListenerTypes(PacketType type); method
       TODO: Add stuff to retrieve packetlisteners of a plugin
     */

    public Collection<PacketListener> getListenersFor(PacketType packetType) {
        return this.packetTypeToListener.get(packetType);
    }

    public Collection<Set<PacketListener>> values() {
        return this.packetTypeToListener.values();
    }

    public Set<PacketType> keySet() {
        return this.packetTypeToListener.keySet();
    }
}
