package com.captainbern.yggdrasil.protocol;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.protocol.event.PacketListener;
import com.captainbern.yggdrasil.protocol.event.PacketMonitor;
import com.captainbern.yggdrasil.protocol.injector.*;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CommonProtocolManager implements ProtocolManager {

    private static CommonProtocolManager INSTANCE;

    private InjectionManager injectionManager;

    private Injector injector;

    private static volatile List<Injector> injectorList = new ArrayList<Injector>();
    private static volatile List<Injector> injectors = Collections.synchronizedList(injectorList);

    public CommonProtocolManager(Yggdrasil yggdrasil) {
        INSTANCE = this;

        injectionManager = new InjectionManager(yggdrasil);

        //this.injector = new ChannelPipelineInjectorHandler(injectionManager);

        registerEvents(yggdrasil.getServer().getPluginManager(), yggdrasil);
    }

    private void registerEvents(PluginManager pluginManager, Plugin plugin) {
        if(this.injector != null) {
            //this.injector.inject();
        }

        pluginManager.registerEvents(new Listener() {

            @EventHandler(priority = EventPriority.MONITOR)
            public void onPlayerLogin(PlayerLoginEvent event) {
                CommonProtocolManager.this.onPlayerLogin(event);
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onPlayerJoin(PlayerJoinEvent event) {
                CommonProtocolManager.this.onPlayerJoin(event);
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onPlayerLeave(PlayerQuitEvent event) {
                CommonProtocolManager.this.onPlayerLeave(event);
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onPluginDisable(PluginDisableEvent event) {
                CommonProtocolManager.this.onPluginDisable(event);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }, plugin);
    }

    private void onPlayerLogin(PlayerLoginEvent event) {

    }

    private void onPlayerJoin(PlayerJoinEvent event) {

    }

    private void onPlayerLeave(PlayerQuitEvent event) {

    }

    private void onPluginDisable(PluginDisableEvent event) {

    }

    /*********************************************************************************************
     *
     *                          Start of the user-usable methods
     *
     *********************************************************************************************/
    @Override
    public void registerPacketListener(PacketListener listener) {

    }

    @Override
    public void registerPacketMonitor(PacketMonitor monitor) {

    }

    @Override
    public Set<PacketListener> getPacketListenersFor(PacketType type) {
        return null;
    }

    @Override
    public Set<PacketMonitor> getPacketMonitorsFor(PacketType packetType) {
        return null;
    }

    @Override
    public Set<PacketListener> getPacketListenersOf(Plugin plugin) {
        return null;
    }

    @Override
    public Set<PacketListener> getPacketMonitorsOf(Plugin plugin) {
        return null;
    }

    @Override
    public boolean removePacketMonitor(PacketMonitor monitor) {
        return false;
    }

    @Override
    public boolean removePacketListener(PacketListener listener) {
        return false;
    }

    @Override
    public boolean removePacketListenersOf(Plugin plugin) {
        return false;
    }

    @Override
    public boolean removePacketMonitorsOf(Plugin plugin) {
        return false;
    }

    @Override
    public InjectionManager getInjectionManager() {
        return null;
    }
}
