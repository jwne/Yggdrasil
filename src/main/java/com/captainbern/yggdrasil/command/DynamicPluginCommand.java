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
package com.captainbern.yggdrasil.command;

import com.captainbern.yggdrasil.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class DynamicPluginCommand extends Command implements PluginIdentifiableCommand{
    protected final CommandExecutor owner;
    protected final Object registeredWith;
    protected final Plugin owningPlugin;
    protected String[] permissions = new String[0];

    public DynamicPluginCommand(String name, String[] aliases, String desc, String usage, CommandExecutor owner, Object registeredWith, Plugin plugin) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.owner = owner;
        this.owningPlugin = plugin;
        this.registeredWith = registeredWith;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return owner.onCommand(sender, this, label, args);
    }

    public Object getOwner() {
        return owner;
    }

    public Object getRegisteredWith() {
        return registeredWith;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
        if (permissions != null) {
            super.setPermission(StringUtils.join(permissions, ";"));
        }
    }

    public String[] getPermissions() {
        return permissions;
    }

    @Override
    public Plugin getPlugin() {
        return owningPlugin;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean testPermissionSilent(CommandSender sender) {
        if (permissions == null || permissions.length == 0) {
            return true;
        }

        if (registeredWith instanceof CommandManager) {
            try {
                for (String permission : permissions) {
                    if (((CommandManager) registeredWith).hasPermission(permission, sender)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable ignore) {
            }
        }
        return super.testPermissionSilent(sender);
    }
}
