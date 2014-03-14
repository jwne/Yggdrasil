package com.captainbern.common.protocol2.injector.netty;

import com.captainbern.common.collection.PlayerHashMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * The class that will go about registering our custom ChannelHandler in the channel of the given player.
 */
public class InjectionManager {

    private static final PlayerHashMap<ChannelPipelineInjector> lookup = new PlayerHashMap<ChannelPipelineInjector>();

    private volatile boolean closed;

    private final Plugin plugin;

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
     * @param listener
     * @return
     */
    public ChannelPipelineInjector registerInjector(Player player, ChannelListener listener) {
        if(closed) {
            return new NullChannelPipelineInjector(player);
        }

        ChannelPipelineInjector pipelineInjector = lookup.get(player);
        if(pipelineInjector == null) {
            pipelineInjector = new ChannelPipelineInjectorHandler(player, listener, this);
        }

        if(pipelineInjector != null) {
            lookup.remove(player);
            pipelineInjector.setPlayer(player);
        }

        return lookup.put(player, pipelineInjector);
    }

    /**
     * Removes an injector for the given player
     * @param player
     */
    public void removeInjector(Player player) {
        if(!lookup.containsKey(player))
            return;

        ChannelPipelineInjector injector = lookup.get(player);
        injector.close();

        lookup.remove(player);
    }

    public synchronized void close() {
        if (!closed) {
            closed = true;

            // Close everything
            for (ChannelPipelineInjector injector : lookup.values())
                injector.close();
        }
    }
}
