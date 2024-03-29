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

public class CommandInfo {
    private final String name;
    private final String[] aliases;
    private final Object registeredWith;
    private final String usage, desc;
    private final String[] permissions;

    public CommandInfo(String name, String usage, String desc, String[] aliases, Object registeredWith) {
        this(name, usage, desc, aliases, registeredWith, null);
    }

    public CommandInfo(String name, String usage, String desc, String[] aliases, Object registeredWith, String[] permissions) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.aliases = aliases;
        this.permissions = permissions;
        this.registeredWith = registeredWith;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDesc() {
        return desc;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public Object getRegisteredWith() {
        return registeredWith;
    }
}
