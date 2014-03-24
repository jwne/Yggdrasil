package com.captainbern.yggdrasil.protection.region.plugins;

import com.captainbern.yggdrasil.protection.region.Region;
import com.captainbern.yggdrasil.protection.region.RegionFlag;
import com.captainbern.yggdrasil.protection.region.RegionProtectionProvider;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class RegionProtectionProvider_GriefPrevention extends RegionProtectionProvider<GriefPrevention> {

    public RegionProtectionProvider_GriefPrevention(Plugin myPluginInstance) {
        super(myPluginInstance, "GriefPrevention");
    }

    @Override
    public void onHook() {
        // Ignore
    }

    @Override
    public void onUnhook() {
        // Ignore
    }

    @Override
    public boolean isInRegion(Location location) {
        return GriefPrevention.instance.dataStore.getClaimAt(location, true) != null;
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
        Set<Region> regions = new HashSet<Region>();
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true);
        GriefPreventionRegion region = new GriefPreventionRegion(claim);
        regions.add(region);
        return regions;
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
        return GriefPrevention.instance.dataStore.hasPlayerData(player.getName());
    }

    @Override
    public boolean removeRegion(Player player, Region region) {
        return GriefPrevention.instance.dataStore.deleteClaim((Claim) region.getHandler(), player);
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
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true);
        return claim.isManager(player);
    }

    @Override
    protected boolean canBuild(Player player, Block block) {
        return canBuild(player, block.getLocation());
    }

    // GriefPrevention Region (aka Claim)
    private class GriefPreventionRegion extends Region<Claim> {

        protected final Claim claim;

        public GriefPreventionRegion(Claim claim) {
            this.claim = claim;
        }

        @Override
        public String getId() {
            return String.valueOf(this.claim.getID());
        }

        @Override
        public Location getMinLocation() {
            return this.claim.getLesserBoundaryCorner();
        }

        @Override
        public Location getMaxLocation() {
            return this.claim.getGreaterBoundaryCorner();
        }

        @Override
        public Set<RegionFlag> getFlags() {
            return new HashSet<RegionFlag>();    // In GriefPrevention there isn't a "usable" flag system
        }

        @Override // GriefPrevention doesn't really provide a way to add multiple owners
        public void addOwner(String playerName) {
           this.claim.addManager(playerName);
        }

        @Override
        public void addMember(String playerName) {
           this.claim.addManager(playerName);
        }

        @Override
        public void removeOwner(String playerName) {
            GriefPrevention.instance.dataStore.deleteClaim(this.claim, Bukkit.getPlayerExact(playerName));
            this.claim.removeManager(playerName);
            this.claim.dropPermission(playerName);
        }

        @Override
        public void removeMember(String playerName) {
            this.claim.dropPermission(playerName);
        }

        @Override
        public boolean isOwner(String playerName) {
            return this.claim.getOwnerName().equalsIgnoreCase(playerName);
        }

        @Override
        public boolean isMember(String playerName) {
            return this.claim.isManager(playerName);
        }

        @Override
        public Set<String> getOwners() {
            Set<String> set = new HashSet<String>();
            set.add(this.claim.getOwnerName());
            return set;
        }

        @Override
        public Set<String> getMembers() {
            return new HashSet<String>(this.claim.getManagerList());
        }

        @Override
        public Claim getHandler() {
            return this.claim;
        }
    }
}
