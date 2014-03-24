package com.captainbern.yggdrasil.protection.region.plugins;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.protection.region.Region;
import com.captainbern.yggdrasil.protection.region.RegionFlag;
import com.captainbern.yggdrasil.protection.region.RegionProtectionProvider;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class RegionProtectionProvider_Factions extends RegionProtectionProvider<Factions> {

    public RegionProtectionProvider_Factions(Yggdrasil yggdrasil) {
        super(yggdrasil, "Factions");
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
    public Set<Region> getRegionOf(Location location) {
        Set<Region> returnSet = new HashSet<Region>();
        Faction faction = BoardColls.get().getFactionAt(PS.valueOf(location));
        returnSet.add(new FactionRegion(faction));
        return returnSet;
    }

    @Override
    public Set<Region> getRegionOf(Block block) {
        Set<Region> returnSet = new HashSet<Region>();
        Faction faction = BoardColls.get().getFactionAt(PS.valueOf(block));
        returnSet.add(new FactionRegion(faction));
        return returnSet;
    }

    @Override
    public Set<Region> getRegionOf(Entity entity) {
        Set<Region> returnSet = new HashSet<Region>();
        Faction faction = BoardColls.get().getFactionAt(PS.valueOf(entity));
        returnSet.add(new FactionRegion(faction));
        return returnSet;
    }

    @Override
    public boolean hasRegions(Player player) {
        UPlayer uPlayer = UPlayer.get(player);
        return uPlayer.hasFaction();
    }

    @Override
    public boolean removeRegion(Player player, Region region) {
        UPlayer uPlayer = UPlayer.get(player);

        uPlayer.leave();

        return !uPlayer.hasFaction();
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

    /**
     * Don't mind the loops here, in factions a player can only own/have 1 faction at the time
     * (getRegionOf() returns a Set because in worldguard multiple regions can intersect each other)
     * @param player
     * @param location
     * @return
     */
    @Override
    protected boolean canBuild(Player player, Location location) {
        Set<Region> regions = getRegionOf(location);
        for(Region region : regions) {
            if(region.isMember(player.getName()) || region.isOwner(player.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean canBuild(Player player, Block block) {
        Set<Region> regions = getRegionOf(block);
        for(Region region : regions) {
            if(region.isMember(player.getName()) || region.isOwner(player.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Faction region
     */
    private class FactionRegion extends Region<Faction> {

        protected final Faction faction;

        public FactionRegion(Faction faction) {
            this.faction = faction;
        }

        @Override
        public String getId() {
            return this.faction.getId();
        }

        @Override
        public Location getMinLocation() {
            return null;
        }

        @Override
        public Location getMaxLocation() {
            return null;
        }

        @Override
        public Set<RegionFlag> getFlags() {
            return null;
        }

        @Override
        public void addOwner(String playerName) {
            UPlayer uPlayer = UPlayer.get(Bukkit.getPlayerExact(playerName));
            uPlayer.setFaction(this.faction);
            uPlayer.setRole(Rel.LEADER);
        }

        @Override
        public void addMember(String playerName) {
            UPlayer uPlayer = UPlayer.get(Bukkit.getPlayerExact(playerName));
            uPlayer.setFaction(this.faction);
            uPlayer.setRole(Rel.MEMBER);
        }

        @Override
        public void removeOwner(String playerName) {
            UPlayer uPlayer = UPlayer.get(Bukkit.getPlayerExact(playerName));
            if(this.faction.getUPlayers().contains(uPlayer)) {
                uPlayer.setRole(Rel.MEMBER);
            }
        }

        @Override
        public void removeMember(String playerName) {
            UPlayer uPlayer = UPlayer.get(Bukkit.getPlayerExact(playerName));
            if(this.faction.getUPlayers().contains(uPlayer)) {
                uPlayer.leave();
            }
        }

        @Override
        public boolean isOwner(String playerName) {
            UPlayer uPlayer = UPlayer.get(playerName);

            if(this.faction.getUPlayers().contains(uPlayer)) {
                return uPlayer.getRole().equals(Rel.LEADER);
            }
            return false;
        }

        @Override
        public boolean isMember(String playerName) {
            UPlayer uPlayer = UPlayer.get(playerName);

            if(this.faction.getUPlayers().contains(uPlayer)) {
                return uPlayer.getRole().equals(Rel.MEMBER);
            }
            return false;
        }

        @Override
        public Set<String> getOwners() {
            Set<String> leaders = new HashSet<String>();
            leaders.add(this.faction.getLeader().getName());
            return leaders;
        }

        @Override
        public Set<String> getMembers() {
            Set<String> members = new HashSet<String>();
            for(UPlayer member : this.faction.getUPlayers()) {
                members.add(member.getName());
            }
            return members;
        }

        @Override
        public Faction getHandler() {
            return this.faction;
        }
    }
}
