package com.captainbern.yggdrasil.protocol;

import com.captainbern.yggdrasil.protocol.event.PacketListener;
import com.captainbern.yggdrasil.protocol.event.PacketMonitor;
import com.captainbern.yggdrasil.protocol.injector.InjectionManager;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public interface ProtocolManager {

    public void registerPacketListener(PacketListener listener);

    public void registerPacketMonitor(PacketMonitor monitor);

    public Set<PacketListener> getPacketListenersFor(PacketType type);

    public Set<PacketMonitor> getPacketMonitorsFor(PacketType packetType);

    public Set<PacketListener> getPacketListenersOf(Plugin plugin);

    public Set<PacketListener> getPacketMonitorsOf(Plugin plugin);

    public boolean removePacketMonitor(PacketMonitor monitor);

    public boolean removePacketListener(PacketListener listener);

    public boolean removePacketListenersOf(Plugin plugin);

    public boolean removePacketMonitorsOf(Plugin plugin);

    public InjectionManager getInjectionManager();
}
