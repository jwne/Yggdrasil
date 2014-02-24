package com.captainbern.common.command;

import com.captainbern.common.command.core.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CommandManager extends CommandRegistrationService {

    private Plugin plugin;
    private CommandExecutor executor;

    public CommandManager(Plugin plugin) {
        this(plugin, plugin);
    }

    public CommandManager(Plugin plugin, CommandExecutor executor) {
        this.plugin = plugin;
        this.executor = executor;
}

    public Plugin getPlugin() {
        return this.plugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    protected void register(List<Command> commandList) {

    }

    protected void unregister(List<Command> commandList) {

    }
}
