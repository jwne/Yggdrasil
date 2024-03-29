package com.captainbern.yggdrasil.protection.region.plugins;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.protection.region.Region;
import com.captainbern.yggdrasil.protection.region.RegionFlag;
import com.captainbern.yggdrasil.protection.region.RegionProtectionProvider;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class RegionProtectionProvider_WorldGuard extends RegionProtectionProvider<WorldGuardPlugin> {

    public RegionProtectionProvider_WorldGuard(Yggdrasil yggdrasil) {
        super(yggdrasil, "WorldGuard");
    }

    @Override
    public void onHook() {
        // Ignore
    }

    @Override
    public void onUnhook() {
        // Ignore
    }

    // API

    @Override
    public boolean isInRegion(Location location) {
        RegionManager regionManager = this.getDependency().getRegionManager(location.getWorld());
        ApplicableRegionSet regions = regionManager.getApplicableRegions(location);

        if(regions.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isInRegion(Block block) {
        return isInRegion(block.getLocation());
    }

    @Override
    public boolean isInRegion(Entity entity) {
        return isInRegion(entity.getLocation());
    }

    @Override
    public Set<Region> getRegionOf(Location location) {
        Set<Region> returnSet = new HashSet<Region>();

        RegionManager regionManager = this.getDependency().getRegionManager(location.getWorld());
        ApplicableRegionSet regions = regionManager.getApplicableRegions(location);

        for(ProtectedRegion protectedRegion : regions) {
            returnSet.add(new WorldGuardRegion(protectedRegion, location.getWorld()));
        }

        return returnSet;
    }

    @Override
    public Set<Region> getRegionOf(Block block) {
        return getRegionOf(block.getLocation());
    }

    @Override
    public Set<Region> getRegionOf(Entity entity) {
        return getRegionOf(entity.getLocation());
    }

    @Override
    public boolean hasRegions(Player player) {
        for(World world : Bukkit.getWorlds()) {
            RegionManager regionManager = this.getDependency().getRegionManager(world);
            if(regionManager.getRegionCountOfPlayer(this.getDependency().wrapPlayer(player)) > 0) {
                return true;
            }
        }
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

    @Override
    protected boolean canBuild(Player player, Location location) {
        return this.getDependency().canBuild(player, location);
    }

    @Override
    protected boolean canBuild(Player player, Block block) {
        return this.getDependency().canBuild(player, block);
    }

    /**
     * WorldGuard region
     */
    private class WorldGuardRegion extends Region<ProtectedRegion> {

        protected final ProtectedRegion handle;
        protected final World world;

        /**
         * We need to save the world because there's almost no way to get a world back out of
         * a ProtectedRegion
         * @param handle
         * @param world
         */
        public WorldGuardRegion(ProtectedRegion handle, World world) {
            this.handle = handle;
            this.world = world;
        }

        @Override
        public String getId() {
            return this.handle.getId();
        }

        @Override
        public Location getMinLocation() {
            return new Location(world,
                    this.handle.getMinimumPoint().getBlockX(),
                    this.handle.getMinimumPoint().getBlockY(),
                    this.handle.getMinimumPoint().getBlockZ()
            );
        }

        @Override
        public Location getMaxLocation() {
            return new Location(world,
                    this.handle.getMaximumPoint().getBlockX(),
                    this.handle.getMaximumPoint().getBlockY(),
                    this.handle.getMaximumPoint().getBlockZ()
            );
        }

        @Override
        public Set<RegionFlag> getFlags() {
            return null;
        }

        @Override
        public void addOwner(String playerName) {
            this.handle.getOwners().addPlayer(playerName);
        }

        @Override
        public void addMember(String playerName) {
            this.handle.getMembers().addPlayer(playerName);
        }

        @Override
        public void removeOwner(String playerName) {
            this.handle.getOwners().removePlayer(playerName);
        }

        @Override
        public void removeMember(String playerName) {
            this.handle.getMembers().removePlayer(playerName);
        }

        @Override
        public boolean isOwner(String playerName) {
            return this.handle.isOwner(playerName);
        }

        @Override
        public boolean isMember(String playerName) {
            return this.handle.isMember(playerName);
        }

        @Override
        public Set<String> getOwners() {
            return this.handle.getOwners().getPlayers();
        }

        @Override
        public Set<String> getMembers() {
            return this.handle.getMembers().getPlayers();
        }

        @Override
        public ProtectedRegion getHandler() {
            return handle;
        }
    }
}
