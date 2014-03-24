package com.captainbern.yggdrasil.protection.region;

import com.captainbern.yggdrasil.utils.PluginDependencyProvider;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public abstract class RegionProtectionProvider<T extends Plugin> extends PluginDependencyProvider<T> {

    public RegionProtectionProvider(Plugin myPluginInstance, String dependencyName) {
        super(myPluginInstance, dependencyName);
    }

    abstract public boolean isInRegion(Location location);

    abstract public boolean isInRegion(Block block);

    abstract public boolean isInRegion(Entity entity);

    abstract public Set<Region> getRegionOf(Location location);

    abstract public Set<Region> getRegionOf(Block block);

    abstract public Set<Region> getRegionOf(Entity entity);

    abstract public boolean hasRegions(Player player);

    abstract public boolean removeRegion(Player player, Region region);

    abstract public boolean hasFlags(Region region);

    abstract public boolean hasFlags(Region region, RegionFlag... flags);

    abstract public boolean hasFlag(Region region, RegionFlag flag);

    abstract protected boolean canBuild(Player player, Location location);

    abstract protected boolean canBuild(Player player, Block block);
}
