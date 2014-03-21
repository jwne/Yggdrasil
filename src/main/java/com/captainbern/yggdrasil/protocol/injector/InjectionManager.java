package com.captainbern.yggdrasil.protocol.injector;

import com.captainbern.yggdrasil.collection.PlayerHashMap;
import com.captainbern.yggdrasil.core.Yggdrasil;
import org.bukkit.entity.Player;

/**
 * The class that will go about registering our custom ChannelHandler in the channel of the given player.
 */
public class InjectionManager {

    private static final PlayerHashMap<ChannelPipelineInjector> lookup = new PlayerHashMap<ChannelPipelineInjector>();

    private volatile boolean closed;

    private static ListenerInvoker invoker;

    public InjectionManager(Yggdrasil yggdrasil) {
        invoker = new ListenerInvoker(yggdrasil, this);
    }

    public boolean isClosed() {
        return this.closed;
    }

    public ListenerInvoker getInvoker() {
        if(invoker == null)
            throw new RuntimeException("Well, this is embarrassing... The ListenerInvoker appears to be NULL!");

        return invoker;
    }

    /**
     * Registers an injector for the given player
     * @param player
     * @param injector
     * @return
     */
    public ChannelPipelineInjector registerInjector(Player player, ChannelInjector injector) {
        if(closed) {
            return null;
        }

        ChannelPipelineInjector pipelineInjector = lookup.get(player);
        if(pipelineInjector == null) {
            pipelineInjector = new ChannelPipelineInjectorHandler(player, this, injector);
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
