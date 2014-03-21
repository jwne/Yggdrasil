package com.captainbern.yggdrasil.utils;

import com.captainbern.yggdrasil.exceptions.PluginHookException;
import com.captainbern.yggdrasil.core.Yggdrasil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public abstract class PluginDependencyProvider<T extends Plugin> {

    protected PluginDependencyProvider<T> instance;
    protected T dependency;
    protected boolean hooked;
    protected Plugin myPluginInstance;
    protected String dependencyName;

    // TODO: add more utils, plugin stuff mostly.

    public PluginDependencyProvider(Plugin myPluginInstance, String dependencyName) {
        this.instance = this;
        this.myPluginInstance = myPluginInstance;
        this.dependencyName = dependencyName;

        if(dependency == null && !this.hooked) {
            try {
                dependency = (T) Bukkit.getPluginManager().getPlugin(getDependencyName());

                if(this.dependency != null && this.dependency.isEnabled()) {
                    this.hooked = true;
                    onHook();
                    Yggdrasil.LOGGER.info("[" + this.dependency.getName() + "] Successfully hooked");
                }
            } catch (Exception e) {
                Yggdrasil.LOGGER_REFLECTION.warning("Could not create a PluginDependencyProvider for: " + getDependencyName() + "! (Are you sure the type is valid?)");
            }
        }

        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            protected void onEnable(PluginEnableEvent event) {
                if((dependency == null) && (event.getPlugin().getName().equalsIgnoreCase(getDependencyName()))) {
                    try {
                        dependency = (T) event.getPlugin();
                        hooked = true;
                        onHook();
                        Yggdrasil.LOGGER.info("[" + getDependencyName() + "] Successfully hooked");
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
                    onUnhook();
                    Yggdrasil.LOGGER.info("[" + getDependencyName() + "] Successfully unhooked");
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }, getHandlingPlugin());
    }

    public abstract void onHook();

    public abstract void onUnhook();

    public T getDependency() {
        if(this.dependency == null) {
            throw new RuntimeException("Dependency is NULL!");
        }
        return this.dependency;
    }

    public boolean isHooked() {
        return this.hooked;
    }

    public Plugin getHandlingPlugin() {
        if(this.myPluginInstance == null) {
            throw new RuntimeException("HandlingPlugin is NULL!");
        }
        return this.myPluginInstance;
    }

    public String getDependencyName() {
        if(this.dependencyName == null) {
            throw new RuntimeException("Dependency name is NULL!");
        }
        return this.dependencyName;
    }
}
