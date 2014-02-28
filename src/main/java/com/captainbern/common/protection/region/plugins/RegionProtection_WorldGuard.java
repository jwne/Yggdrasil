package com.captainbern.common.protection.region.plugins;

import com.captainbern.common.protection.region.Region;
import com.captainbern.common.protection.region.RegionFlag;
import com.captainbern.common.protection.region.RegionProtectionProvider;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RegionProtection_WorldGuard extends RegionProtectionProvider<WorldGuardPlugin> {

    public RegionProtection_WorldGuard() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Class<WorldGuardPlugin> getProviderClass() {
        return null;
    }

    @Override
    public boolean isHooked() {
        return false;
    }

    @Override
    public boolean isInRegion(Location location) {
        return false;
    }

    @Override
    public boolean isInRegion(Block block) {
        return false;
    }

    @Override
    public boolean isInRegion(Entity entity) {
        return false;
    }

    @Override
    public Region getRegionOf(Location location) {
        return null;
    }

    @Override
    public Region getRegionOf(Block block) {
        return null;
    }

    @Override
    public Region getRegionOf(Entity entity) {
        return null;
    }

    @Override
    public boolean hasRegions(Player player) {
        return false;
    }

    @Override
    public boolean removeRegion(Player player, Region region) {
        return false;
    }

    @Override
    public boolean hasFlags(Region region) {
        return false;
    }

    @Override
    public boolean hasFlags(Region region, RegionFlag... flags) {
        return false;
    }

    @Override
    public boolean hasFlag(Region region, RegionFlag flag) {
        return false;
    }
}
