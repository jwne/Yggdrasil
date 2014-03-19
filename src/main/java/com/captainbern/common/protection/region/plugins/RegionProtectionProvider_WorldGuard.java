package com.captainbern.common.protection.region.plugins;

import com.captainbern.common.exceptions.PluginHookException;
import com.captainbern.common.internal.Yggdrasil;
import com.captainbern.common.protection.region.Region;
import com.captainbern.common.protection.region.RegionFlag;
import com.captainbern.common.protection.region.RegionProtectionProvider;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.HashSet;
import java.util.Set;

public class RegionProtectionProvider_WorldGuard extends RegionProtectionProvider<WorldGuardPlugin> {

    private Yggdrasil yggdrasil;
    private WorldGuardPlugin worldGuardPlugin;
    protected boolean hooked;

    public RegionProtectionProvider_WorldGuard(Yggdrasil yggdrasil) {
        this.yggdrasil = yggdrasil;

        if(this.worldGuardPlugin == null) {
            this.worldGuardPlugin = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin(getName());

            if(this.worldGuardPlugin != null && this.worldGuardPlugin.isEnabled()) {
                this.hooked = true;
                Yggdrasil.LOGGER.info("[" + getName() + "] Successfully hooked");
            }
        }

        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            protected void onEnable(PluginEnableEvent event) {
                if((worldGuardPlugin == null) && (event.getPlugin().getName().equalsIgnoreCase(getName()))) {
                    try {
                        worldGuardPlugin = (WorldGuardPlugin) event.getPlugin();
                        hooked = true;
                        Yggdrasil.LOGGER.info("[" + getName() + "] Successfully hooked");
                    } catch (Exception e) {
                        throw new PluginHookException(event.getPlugin());
                    }
                }
            }

            @EventHandler
            protected void onDisable(PluginDisableEvent event) {
                if((worldGuardPlugin != null) && (event.getPlugin().getName().equalsIgnoreCase(getName()))) {
                    worldGuardPlugin = null;
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
        return "WorldGuard";
    }

    @Override
    public WorldGuardPlugin getProviderClass() {
        return this.worldGuardPlugin;
    }

    @Override
    public boolean isHooked() {
        return this.hooked;
    }

    @Override
    public boolean isInRegion(Location location) {
        RegionManager regionManager = this.worldGuardPlugin.getRegionManager(location.getWorld());
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

        RegionManager regionManager = this.worldGuardPlugin.getRegionManager(location.getWorld());
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
            RegionManager regionManager = this.worldGuardPlugin.getRegionManager(world);
            if(regionManager.getRegionCountOfPlayer(this.worldGuardPlugin.wrapPlayer(player)) > 0) {
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
        return getProviderClass().canBuild(player, location);
    }

    @Override
    protected boolean canBuild(Player player, Block block) {
        return getProviderClass().canBuild(player, block);
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
