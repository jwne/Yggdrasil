package com.captainbern.yggdrasil;

import com.captainbern.yggdrasil.command.CommandManager;
import com.captainbern.yggdrasil.command.DefaultCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitPlugin extends JavaPlugin {

    protected CommandManager commandManager;

    @Override
    public void onDisable() {
        this.commandManager.unregister();
        unLoad();
    }

    @Override
    public void onEnable() {
        this.commandManager = new CommandManager(this);
        DefaultCommandExecutor defaultCommandExecutor = new DefaultCommandExecutor(commandManager);

        commandManager.setExecutor(defaultCommandExecutor);

        load();
    }

    public abstract void load();

    public abstract void unLoad();

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public final void registerCommand(Class<?> commands){
        this.commandManager.register(commands);
    }

    public final void registerListener(Listener listener) {
        if (listener == null) {
            throw new RuntimeException("A Listener instance may not be null!");
        }
        if (listener != this) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public final void registerListener(Class<? extends Listener> listener) {
        if (listener == null) {
            throw new RuntimeException("A Listener instance may not be null!");
        }
        try {
            this.registerListener(listener.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
