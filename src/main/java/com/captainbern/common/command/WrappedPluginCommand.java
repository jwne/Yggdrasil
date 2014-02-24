package com.captainbern.common.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public class WrappedPluginCommand extends Command implements PluginIdentifiableCommand {
    protected WrappedPluginCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }
}
