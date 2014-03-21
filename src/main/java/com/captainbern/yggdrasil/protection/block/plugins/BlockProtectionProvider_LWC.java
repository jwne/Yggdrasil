package com.captainbern.yggdrasil.protection.block.plugins;

import com.captainbern.yggdrasil.exceptions.PluginHookException;
import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.protection.block.BlockProtectionProvider;
import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class BlockProtectionProvider_LWC extends BlockProtectionProvider<LWCPlugin> {

    private Yggdrasil yggdrasil;
    private LWCPlugin lwcPlugin;
    private LWC lwc;
    protected boolean hooked;

    public BlockProtectionProvider_LWC(Yggdrasil yggdrasil) {
        this.yggdrasil = yggdrasil;

        if(this.lwcPlugin == null && this.lwc == null) {
            this.lwcPlugin = (LWCPlugin) Bukkit.getPluginManager().getPlugin(getName());
            this.lwc = this.lwcPlugin.getLWC();
            if((this.lwcPlugin != null) && (this.lwcPlugin.isEnabled())) {
                this.hooked = true;
                Yggdrasil.LOGGER.info("[" + getName() + "] Successfully hooked");
            }
        }

        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            protected void onEnable(PluginEnableEvent event) {
                if((lwcPlugin == null) && (event.getPlugin().getName().equalsIgnoreCase(getName()))) {
                    try {
                        lwcPlugin = (LWCPlugin) event.getPlugin();
                        lwc = lwcPlugin.getLWC();
                        hooked = true;
                        Yggdrasil.LOGGER.info("[" + getName() + "] Successfully hooked");
                    } catch (Exception e) {
                        throw new PluginHookException(event.getPlugin());
                    }
                }
            }

            @EventHandler
            protected void onDisable(PluginDisableEvent event) {
                if((lwcPlugin != null) && (event.getPlugin().getName().equalsIgnoreCase(getName()))) {
                    lwcPlugin = null;
                    lwc = null;
                    hooked = false;
                    Yggdrasil.LOGGER.info("[" + getName() + "] Successfully unhooked");
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }, this.yggdrasil);
    }

    @Override
    public String getName() {
        return "LWC";
    }

    @Override
    public LWCPlugin getProviderClass() {
        return this.lwcPlugin;
    }

    @Override
    public boolean isHooked() {
        return this.hooked;
    }

    @Override
    public boolean isProtected(Block block) {
        return this.lwc.findProtection(block).getBukkitOwner() != null;
    }

    @Override
    public String getOwner(Block block) {
        return this.lwc.findProtection(block).getBukkitOwner().getName();
    }

    @Override
    public boolean setOwner(Block block, Player owner) {
        if(this.lwc.isProtectable(block)) {
            this.lwc.findProtection(block).setOwner(owner.getName());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeProtection(Block block) {
        try {
            this.lwc.findProtection(block).remove();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
