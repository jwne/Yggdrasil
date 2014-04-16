package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.collection.PlayerHashMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * The class that will go about registering our custom ChannelHandler in the channel of the given player.
 */
public class InjectionManager {

    private static final PlayerHashMap<Injector> lookup = new PlayerHashMap<Injector>();

    private volatile boolean closed;

    private Plugin plugin;

    public InjectionManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public boolean isClosed() {
        return this.closed;
    }

    /**
     * Registers an injector for the given player
     * @param player
     * @param injector
     * @return
     */
    public Injector registerInjector(Player player, Injector injector) {
        if(closed) {
            return null;
        }

        Injector pipelineInjector = lookup.get(player);
        if(pipelineInjector == null) {
            pipelineInjector = new ChannelPipelineInjectorHandler(player, this);
        }

        lookup.remove(player);
        pipelineInjector.setPlayer(player);

        return lookup.put(player, pipelineInjector);
    }

    /**
     * Removes an injector for the given player
     * @param player
     */
    public void removeInjector(Player player) {
        if(!lookup.containsKey(player))
            return;

        Injector injector = lookup.get(player);
        injector.close();

        lookup.remove(player);
    }

    public synchronized void close() {
        if (!closed) {
            closed = true;

            // Close everything
            for (Injector injector : lookup.values())
                injector.close();
        }
    }
}
