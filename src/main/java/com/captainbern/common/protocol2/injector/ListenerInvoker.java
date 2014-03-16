package com.captainbern.common.protocol2.injector;

import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.protocol2.PacketType;
import com.captainbern.common.protocol2.event.PacketEvent;
import com.captainbern.common.protocol2.event.PacketListener;
import com.captainbern.common.protocol2.event.PacketMonitor;
import com.captainbern.common.protocol2.event.PacketTypeSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerInvoker {

    private CBCommonLib cbCommonLib;
    private InjectionManager injectionManager;

    private static ConcurrentHashMap<PacketType, Set<PacketListener>> packTypeToListener = new ConcurrentHashMap<PacketType, Set<PacketListener>>();
    private static ConcurrentHashMap<PacketType, Set<PacketMonitor>> packTypeToMonitor = new ConcurrentHashMap<PacketType, Set<PacketMonitor>>();

    private static ConcurrentHashMap<Plugin, Set<PacketListener>> pluginToListener = new ConcurrentHashMap<Plugin, Set<PacketListener>>();
    private static ConcurrentHashMap<Plugin, Set<PacketMonitor>> pluginToMonitor = new ConcurrentHashMap<Plugin, Set<PacketMonitor>>();

    private static List<PacketListener> allListeners = new ArrayList<PacketListener>();
    private static List<PacketMonitor> allMonitors = new ArrayList<PacketMonitor>();

    public ListenerInvoker(CBCommonLib cbCommonLib, InjectionManager injectionManager) {
        this.cbCommonLib = cbCommonLib;
        this.injectionManager = injectionManager;
    }

    public InjectionManager getInjectionManager() {
        if(this.injectionManager == null)
            throw new RuntimeException("InjectionManager is NULL!");

        return this.injectionManager;
    }

    /**
     * Registers a new PacketListener
     * @param listener
     */
    public void addListener(PacketListener listener) {
        if(listener == null)
            throw new IllegalArgumentException("Listener can't be NULL!");

        // Don't want to register a listener twice
        if(!hasListener(listener))
            return;

        PacketTypeSet sending = listener.getSendingWhiteList();
        PacketTypeSet receiving = listener.getReceivingWhiteList();

        if(sending.size() > 0 || receiving.size() > 0) {

        }
    }

    /**
     * Registers a PacketMonitor
     * @param monitor
     */
    public void addPacketMonitor(PacketMonitor monitor) {
        // Don't register a monitor twice
        if(!hasMonitor(monitor))
            return;
    }

    /**
     * Removes a specific PacketListener
     * @param listener
     */
    public void removeListener(PacketListener listener) {

    }

    /**
     * Removes a specific PacketMonitor
     * @param monitor
     */
    public void removePacketMonitor(PacketMonitor monitor) {

    }

    public void removeListeners(Plugin plugin) {

    }

    public void removeMonitors(Plugin plugin) {

    }

    public Set<PacketListener> getListenersFor(PacketType type) {
        return null;
    }

    public Set<PacketListener> getMonitorsFor(PacketType type) {
        return null;
    }

    public boolean hasListener(PacketListener listener) {
        return allListeners.contains(listener);
    }

    public boolean hasMonitor(PacketMonitor monitor) {
        return allMonitors.contains(monitor);
    }

    private PacketEvent handleReceiveOnMainThread(final PacketEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.cbCommonLib, new Runnable() {
            @Override
            public void run() {
               // event = null;
            }
        });
        return event;
    }

    private PacketEvent handleSendOnMainThread(PacketEvent event) {
        return null;
    }

    public PacketEvent handleReceive(ChannelPipelineInjector injector, Object packet) {
        return null;
    }

    public PacketEvent handleSend(ChannelPipelineInjector injector, Object packet) {
        return null;
    }
}
