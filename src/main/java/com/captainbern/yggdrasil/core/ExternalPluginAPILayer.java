package com.captainbern.yggdrasil.core;

import com.captainbern.yggdrasil.economy.EconomyProvider;
import com.captainbern.yggdrasil.permissions.PermissionsProvider;
import com.captainbern.yggdrasil.protection.block.BlockProtectionProvider;
import com.captainbern.yggdrasil.protection.block.plugins.BlockProtectionProvider_LWC;
import com.captainbern.yggdrasil.protection.region.RegionProtectionProvider;
import com.captainbern.yggdrasil.protection.region.plugins.RegionProtectionProvider_Factions;
import com.captainbern.yggdrasil.protection.region.plugins.RegionProtectionProvider_WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class ExternalPluginAPILayer {

    protected Yggdrasil yggdrasil;
    private ServicesManager servicesManager;

    public ExternalPluginAPILayer(Yggdrasil yggdrasil) {
        this.yggdrasil = yggdrasil;
        this.servicesManager = Bukkit.getServicesManager();

        loadEconomy();
        loadPermissions();
        loadProtection();
    }

    public void loadEconomy() {

    }

    public void loadPermissions() {

    }

    public void loadProtection() {
        /**
         * Register the region protection providers first
         */
        registerRegionProtection(new RegionProtectionProvider_WorldGuard(yggdrasil), ServicePriority.Normal);

        registerRegionProtection(new RegionProtectionProvider_Factions(yggdrasil), ServicePriority.Normal);

        /**
         * Register the block protection providers
         */
        registerBlockProtection(new BlockProtectionProvider_LWC(yggdrasil), ServicePriority.Normal);
    }

    /**
     * Used to register services to access plugins like iConomy, BOSEconomy etc...
     * @param economyProvider
     * @param servicePriority
     */
    protected void registerEconomy(EconomyProvider economyProvider, ServicePriority servicePriority) {
        getServicesManager().register(EconomyProvider.class, economyProvider, this.yggdrasil, servicePriority);
    }

    /**
     * Used to register services to access plugins like bPermissions, PermissionsBukkit etc...
     * @param permissionsProvider
     * @param servicePriority
     */
    protected void registerPermission(PermissionsProvider permissionsProvider, ServicePriority servicePriority) {
        getServicesManager().register(PermissionsProvider.class, permissionsProvider, this.yggdrasil, servicePriority);
    }

    /**
     * Used to register services to access plugins like LWC, Lockette, etc...
     * @param blockProtectionProvider
     * @param servicePriority
     */
    protected void registerBlockProtection(BlockProtectionProvider blockProtectionProvider, ServicePriority servicePriority) {
        getServicesManager().register(BlockProtectionProvider.class, blockProtectionProvider, this.yggdrasil, servicePriority);
    }

    /**
     * Used to register services to access plugins like WorldGuard, Towny, Factions etc...
     * @param regionProtectionProvider
     * @param servicePriority
     */
    protected void registerRegionProtection(RegionProtectionProvider regionProtectionProvider, ServicePriority servicePriority) {
        getServicesManager().register(RegionProtectionProvider.class, regionProtectionProvider, this.yggdrasil, servicePriority);
    }

    public final ServicesManager getServicesManager() {
        return this.servicesManager;
    }
}
