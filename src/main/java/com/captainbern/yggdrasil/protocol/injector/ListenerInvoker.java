package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.internal.Yggdrasil;
import com.captainbern.yggdrasil.protocol.PacketType;
import com.captainbern.yggdrasil.protocol.event.PacketEvent;
import com.captainbern.yggdrasil.protocol.event.PacketListener;
import com.captainbern.yggdrasil.protocol.event.PacketMonitor;
import com.captainbern.yggdrasil.protocol.event.PacketTypeSet;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerInvoker {

    private Yggdrasil yggdrasil;
    private InjectionManager injectionManager;

    private ConcurrentHashMap<PacketType, Set<PacketListener>> packTypeToListener = new ConcurrentHashMap<PacketType, Set<PacketListener>>();
    private ConcurrentHashMap<PacketType, Set<PacketMonitor>> packTypeToMonitor = new ConcurrentHashMap<PacketType, Set<PacketMonitor>>();

    private ConcurrentHashMap<Plugin, Set<PacketListener>> pluginToListener = new ConcurrentHashMap<Plugin, Set<PacketListener>>();
    private ConcurrentHashMap<Plugin, Set<PacketMonitor>> pluginToMonitor = new ConcurrentHashMap<Plugin, Set<PacketMonitor>>();

    private List<PacketListener> allListeners = new ArrayList<PacketListener>();
    private List<PacketMonitor> allMonitors = new ArrayList<PacketMonitor>();

    public ListenerInvoker(Yggdrasil yggdrasil, InjectionManager injectionManager) {
        this.yggdrasil = yggdrasil;
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
            // Sending
            for(PacketType type : sending) {
                Set<PacketListener> listeners = this.packTypeToListener.get(type);

                if(listeners == null) {
                    listeners = new HashSet<PacketListener>();
                    packTypeToListener.put(type, listeners);
                }

                if(!listeners.contains(listener))
                    listeners.add(listener);

            }

            // Receiving
            for(PacketType type : receiving) {
                Set<PacketListener> listeners = this.packTypeToListener.get(type);

                if(listeners == null) {
                    listeners = new HashSet<PacketListener>();
                    packTypeToListener.put(type, listeners);
                }

                if(!listeners.contains(listener))
                    listeners.add(listener);

            }

            // Now store them in the plugin map
            Set<PacketListener> listeners = this.pluginToListener.get(listener.getPlugin());

            if(listeners == null) {
                listeners = new HashSet<PacketListener>();
                pluginToListener.put(listener.getPlugin(), listeners);
            }

            if(!listeners.contains(listener))
                listeners.add(listener);

            // Make sure it's registered
            allListeners.add(listener);
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

        PacketTypeSet sending = monitor.getSendingWhiteList();
        PacketTypeSet receiving = monitor.getReceivingWhiteList();

        if(sending.size() > 0 || receiving.size() > 0) {
            // Sending
            for(PacketType type : sending) {
                Set<PacketMonitor> monitors = this.packTypeToMonitor.get(type);

                if(monitors == null) {
                    monitors = new HashSet<PacketMonitor>();
                    packTypeToMonitor.put(type, monitors);
                }

                if(!monitors.contains(monitor))
                    monitors.add(monitor);

            }

            // Receiving
            for(PacketType type : receiving) {
                Set<PacketMonitor> monitors = this.packTypeToMonitor.get(type);

                if(monitors == null) {
                    monitors = new HashSet<PacketMonitor>();
                    packTypeToMonitor.put(type, monitors);
                }

                if(!monitors.contains(monitor))
                    monitors.add(monitor);

            }

            // Now store them in the plugin map
            Set<PacketMonitor> monitors = this.pluginToMonitor.get(monitor.getPlugin());

            if(monitors == null) {
                monitors = new HashSet<PacketMonitor>();
                pluginToMonitor.put(monitor.getPlugin(), monitors);
            }

            if(!monitors.contains(monitor))
                monitors.add(monitor);

            // Make sure it's registered
            allMonitors.add(monitor);
        }
    }

    /**
     * Removes a specific PacketListener
     * @param listener
     */
    public void removeListener(PacketListener listener) {
        if(listener == null)
            throw new IllegalArgumentException("Listener can't be NULL!");

        if(!hasListener(listener))
            return;

        PacketTypeSet sending = listener.getSendingWhiteList();
        PacketTypeSet receiving = listener.getReceivingWhiteList();

        if(sending.size() > 0 || receiving.size() > 0) {
            // Sending
            for(PacketType type : sending) {
                Set<PacketListener> listeners = this.packTypeToListener.get(type);

                if(listeners != null) {

                    listeners.remove(listener);

                }
            }

            // Receiving
            for(PacketType type : receiving) {
                Set<PacketListener> listeners = this.packTypeToListener.get(type);

                if(listeners != null) {

                    listeners.remove(listener);

                }
            }

            Set<PacketListener> listeners = this.pluginToListener.get(listener.getPlugin());

            if(listeners != null) {

                listeners.remove(listener);

            }
        }
        allListeners.remove(listener);
    }

    /**
     * Removes a specific PacketMonitor
     * @param monitor
     */
    public void removeMonitor(PacketMonitor monitor) {
        if(monitor == null)
            throw new IllegalArgumentException("Monitor can't be NULL!");

        if(!hasMonitor(monitor))
            return;

        PacketTypeSet sending = monitor.getSendingWhiteList();
        PacketTypeSet receiving = monitor.getReceivingWhiteList();

        if(sending.size() > 0 || receiving.size() > 0) {
            // Sending
            for(PacketType type : sending) {
                Set<PacketMonitor> monitors = this.packTypeToMonitor.get(type);

                if(monitors != null) {

                    monitors.remove(monitor);

                }
            }

            // Receiving
            for(PacketType type : receiving) {
                Set<PacketMonitor> monitors = this.packTypeToMonitor.get(type);

                if(monitors != null) {

                    monitors.remove(monitor);

                }
            }

            Set<PacketMonitor> monitors = this.pluginToMonitor.get(monitor.getPlugin());

            if(monitors != null) {

                monitors.remove(monitor);

            }
        }
        allMonitors.remove(monitor);
    }

    public void removeListeners(Plugin plugin) {
        if(plugin == null)
            throw new IllegalArgumentException("Plugin can't be NULL!");

        Set<PacketListener> listeners = pluginToListener.get(plugin);

        for(PacketListener listener : listeners) {
            removeListener(listener);
        }

        packTypeToListener.remove(plugin);
        allListeners.remove(listeners);
    }

    public void removeMonitors(Plugin plugin) {
        if(plugin == null)
            throw new IllegalArgumentException("Plugin can't be NULL!");

        Set<PacketMonitor> monitors = pluginToMonitor.get(plugin);

        for(PacketMonitor monitor : monitors) {
            removeMonitor(monitor);
        }

        packTypeToMonitor.remove(plugin);
        allMonitors.remove(monitors);
    }

    public Set<PacketListener> getListenersFor(PacketType type) {
        if(type == null)
            throw new IllegalArgumentException("PacketType can't be NULL!");

        return packTypeToListener.get(type);
    }

    public Set<PacketMonitor> getMonitorsFor(PacketType type) {
        if(type == null)
            throw new IllegalArgumentException("PacketType can't be NULL!");

        return packTypeToMonitor.get(type);
    }

    public boolean hasListener(PacketListener listener) {
        return allListeners.contains(listener);
    }

    public boolean hasMonitor(PacketMonitor monitor) {
        return allMonitors.contains(monitor);
    }

    private PacketEvent handleReceiveOnMainThread(final PacketEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.yggdrasil, new Runnable() {
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
