package com.captainbern.common.internal;

import com.captainbern.common.economy.EconomyProvider;
import com.captainbern.common.permissions.PermissionsProvider;
import com.captainbern.common.protection.block.BlockProtectionProvider;
import com.captainbern.common.protection.block.plugins.BlockProtectionProvider_LWC;
import com.captainbern.common.protection.region.RegionProtectionProvider;
import com.captainbern.common.protection.region.plugins.RegionProtectionProvider_Factions;
import com.captainbern.common.protection.region.plugins.RegionProtectionProvider_WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class ExternalPluginAPILayer {

    protected CBCommonLib cbCommonLib;
    private ServicesManager servicesManager;

    public ExternalPluginAPILayer(CBCommonLib cbCommonLib) {
        this.cbCommonLib = cbCommonLib;
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
        registerRegionProtection(new RegionProtectionProvider_WorldGuard(cbCommonLib), ServicePriority.Normal);

        registerRegionProtection(new RegionProtectionProvider_Factions(cbCommonLib), ServicePriority.Normal);

        /**
         * Register the block protection providers
         */
        registerBlockProtection(new BlockProtectionProvider_LWC(cbCommonLib), ServicePriority.Normal);
    }

    /**
     * Used to register services to access plugins like iConomy, BOSEconomy etc...
     * @param economyProvider
     * @param servicePriority
     */
    protected void registerEconomy(EconomyProvider economyProvider, ServicePriority servicePriority) {
        getServicesManager().register(EconomyProvider.class, economyProvider, this.cbCommonLib, servicePriority);
    }

    /**
     * Used to register services to access plugins like bPermissions, PermissionsBukkit etc...
     * @param permissionsProvider
     * @param servicePriority
     */
    protected void registerPermission(PermissionsProvider permissionsProvider, ServicePriority servicePriority) {
        getServicesManager().register(PermissionsProvider.class, permissionsProvider, this.cbCommonLib, servicePriority);
    }

    /**
     * Used to register services to access plugins like LWC, Lockette, etc...
     * @param blockProtectionProvider
     * @param servicePriority
     */
    protected void registerBlockProtection(BlockProtectionProvider blockProtectionProvider, ServicePriority servicePriority) {
        getServicesManager().register(BlockProtectionProvider.class, blockProtectionProvider, this.cbCommonLib, servicePriority);
    }

    /**
     * Used to register services to access plugins like WorldGuard, Towny, Factions etc...
     * @param regionProtectionProvider
     * @param servicePriority
     */
    protected void registerRegionProtection(RegionProtectionProvider regionProtectionProvider, ServicePriority servicePriority) {
        getServicesManager().register(RegionProtectionProvider.class, regionProtectionProvider, this.cbCommonLib, servicePriority);
    }

    public final ServicesManager getServicesManager() {
        return this.servicesManager;
    }
}
