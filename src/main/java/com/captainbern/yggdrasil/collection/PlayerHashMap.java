package com.captainbern.yggdrasil.collection;

import java.util.concurrent.ConcurrentMap;

import org.bukkit.entity.Player;

import com.google.common.collect.ForwardingConcurrentMap;
import com.google.common.collect.MapMaker;

/**
 * Credits:
 * https://forums.bukkit.org/threads/class-no-more-dont-store-players-crap-playerkeyhashmap-v-playervaluehashmap-k.168772/#post-1812688
 *
 *
 * Represents a concurrent hash-map using a Bukkit player object as key. The player keys are
 * referenced weakly, and will be properly collected once each player has logged out.
 * @author Kristian
 * @param <TValue> Type of the stored value.
 */
public class PlayerHashMap<TValue> extends ForwardingConcurrentMap<Player, TValue> {
    protected ConcurrentMap<Player, TValue> delegate;

    /**
     * Construct a new player-based concurrent hash map.
     */
    public PlayerHashMap() {
        this.delegate = new MapMaker().weakKeys().makeMap();
    }

    /**
     * Construct a new player-based concurrent hash map.
     * @param concurrencyLevel - guides the allowed concurrency among update operations.
     */
    public PlayerHashMap(int concurrencyLevel) {
        this.delegate = new MapMaker().weakKeys().concurrencyLevel(concurrencyLevel).makeMap();
    }

    // You could also register a listener and remove players explicitly.
    @Override
    protected ConcurrentMap<Player, TValue> delegate() {
        return delegate;
    }
}
