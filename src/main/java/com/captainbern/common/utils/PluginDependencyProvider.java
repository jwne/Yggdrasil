package com.captainbern.common.utils;

import com.captainbern.common.exceptions.PluginHookException;
import com.captainbern.common.internal.CBCommonLib;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class PluginDependencyProvider<T extends Plugin> {

    private T dependency;
    protected boolean hooked;
    private Plugin myPluginInstance;
    private String dependencyName;

    // TODO: add more utils, plugin stuff mostly.

    public PluginDependencyProvider(Plugin myPluginInstance, String dependencyName) {
        this.myPluginInstance = myPluginInstance;

        if(dependency == null && !this.hooked) {
            try {
                dependency = (T) Bukkit.getPluginManager().getPlugin(getDependencyName());

                if(this.dependency != null && this.dependency.isEnabled()) {
                    this.hooked = true;
                    CBCommonLib.LOGGER.info("[" + this.dependency.getName() + "] Successfully hooked");
                }
            } catch (Exception e) {
                CBCommonLib.LOGGER_REFLECTION.warning("Could not create a PluginDependencyProvider for: " + getDependencyName() + "! (Are you sure the type is valid?)");
            }
        }

        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            protected void onEnable(PluginEnableEvent event) {
                if((dependency == null) && (event.getPlugin().getName().equalsIgnoreCase(getDependencyName()))) {
                    try {
                        dependency = (T) event.getPlugin();
                        hooked = true;
                        CBCommonLib.LOGGER.info("[" + getDependencyName() + "] Successfully hooked");
                    } catch (Exception e) {
                        throw new PluginHookException(event.getPlugin());
                    }
                }
            }

            @EventHandler
            protected void onDisable(PluginDisableEvent event) {
                if((dependency != null) && (event.getPlugin().getName().equalsIgnoreCase(getDependencyName()))) {
                    dependency = null;
                    hooked = false;
                    CBCommonLib.LOGGER.info("[" + getDependencyName() + "] Successfully unhooked");
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }, getHandlingPlugin());
    }

    public T getDependency() {
        return this.dependency;
    }

    public boolean isHooked() {
        return this.hooked;
    }

    public Plugin getHandlingPlugin() {
        return this.myPluginInstance;
    }

    public String getDependencyName() {
        return this.dependencyName;
    }
}