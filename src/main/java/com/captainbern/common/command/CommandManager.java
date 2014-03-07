/*
 * WorldEdit
 * Copyright (C) 2010 sk89q <http://www.sk89q.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package com.captainbern.common.command;

import com.captainbern.common.command.core.Command;
import com.captainbern.common.command.core.CommandPermissions;
import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.SafeField;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.*;

public class CommandManager {

    protected static final FieldAccessor<Map<String, org.bukkit.command.Command>> KNOWN_COMMANDS = new SafeField<Map<String, org.bukkit.command.Command>>(SimpleCommandMap.class, "knownCommands");

    static {
        Bukkit.getServer().getHelpMap().registerHelpTopicFactory(DynamicPluginCommand.class, new DynamicPluginCommandHelpTopic.Factory());
    }

    private Plugin plugin;
    private CommandExecutor executor;
    protected CommandMap fallbackCommandmap;
    protected CommandRegistrationService commandRegistrationService;

    public CommandManager(Plugin plugin) {
        this(plugin, plugin);
    }

    public CommandManager(Plugin plugin, CommandExecutor executor) {
        this.plugin = plugin;
        this.executor = executor;
        this.commandRegistrationService = new CommandRegistrationService();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public CommandRegistrationService getCommandRegistrationService() {
        return this.commandRegistrationService;
    }

    public void register(Class<?> clazz) {
        List<Command> commands = this.commandRegistrationService.registerAndReturn(clazz);
        List<CommandInfo> toRegister = new ArrayList<CommandInfo>();

        for(Command command : commands) {
            List<String> permissions = null;
            Method cmdMethod = this.commandRegistrationService.getMethods().get(null).get(command.name());
            Map<String, Method> childMethods = this.commandRegistrationService.getMethods().get(cmdMethod);

            if (cmdMethod != null && cmdMethod.isAnnotationPresent(CommandPermissions.class)) {
                permissions = Arrays.asList(cmdMethod.getAnnotation(CommandPermissions.class).value());
            } else if (cmdMethod != null && childMethods != null && childMethods.size() > 0) {
                permissions = new ArrayList<String>();
                for (Method m : childMethods.values()) {
                    if (m.isAnnotationPresent(CommandPermissions.class)) {
                        permissions.addAll(Arrays.asList(m.getAnnotation(CommandPermissions.class).value()));
                    }
                }
            }
            toRegister.add(new CommandInfo(command.name(), command.usage(), command.description(), command.aliases(), commands, permissions == null ? null : permissions.toArray(new String[permissions.size()])));
        }

        register(toRegister);
    }

    protected CommandMap getCommandMap() {
        CommandMap commandMap = CBCommonLib.getCommonServer().getCommandMap();
        if(commandMap == null) {
            if(fallbackCommandmap != null) {
                commandMap = fallbackCommandmap;
            } else {
                CBCommonLib.LOGGER.warning("[" + plugin.getDescription().getName() + "] Failed to retrieve the server command-map, using fallback command-service instead!");
                fallbackCommandmap = commandMap = new SimpleCommandMap(Bukkit.getServer());
                Bukkit.getServer().getPluginManager().registerEvents(new FallbackCommandRegistrationService(fallbackCommandmap), plugin);
            }
        }
        return commandMap;
    }

    private boolean register(List<CommandInfo> commandList) {
        CommandMap commandMap = getCommandMap();
        if (commandList == null || commandMap == null) {
            return false;
        }
        for (CommandInfo command : commandList) {
            DynamicPluginCommand cmd = new DynamicPluginCommand(command.getName(), command.getAliases(),
                    command.getDesc(), "/" + command.getName() + " " + command.getUsage(), executor, command.getRegisteredWith(), plugin);
            cmd.setPermissions(command.getPermissions());
            commandMap.register(plugin.getDescription().getName(), cmd);
        }
        return true;
    }

    public boolean unregister() {
        CommandMap commandMap = getCommandMap();
        List<String> toRemove = new ArrayList<String>();
        Map<String, org.bukkit.command.Command> knownCommands = KNOWN_COMMANDS.get(commandMap);
        if (knownCommands == null) {
            return false;
        }
        for (Iterator<org.bukkit.command.Command> i = knownCommands.values().iterator(); i.hasNext();) {
            org.bukkit.command.Command cmd = i.next();
            if (cmd instanceof DynamicPluginCommand && ((DynamicPluginCommand) cmd).getOwner().equals(executor)) {
                i.remove();
                for (String alias : cmd.getAliases()) {
                    org.bukkit.command.Command aliasCmd = knownCommands.get(alias);
                    if (cmd.equals(aliasCmd)) {
                        toRemove.add(alias);
                    }
                }
            }
        }
        for (String string : toRemove) {
            knownCommands.remove(string);
        }
        return true;
    }

    public boolean hasPermission(String permission, CommandSender sender) {
        if(sender.hasPermission(permission)) {
            return true;
        }
        return false;
    }
}
