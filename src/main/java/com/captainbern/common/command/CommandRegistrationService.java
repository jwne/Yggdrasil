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
import com.captainbern.common.command.core.CommandAlias;
import com.captainbern.common.command.core.CommandPermissions;
import com.captainbern.common.command.core.NestedCommand;
import com.captainbern.common.command.exceptions.*;
import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.utils.StringUtil;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class CommandRegistrationService {

    protected Map<Method, Map<String, Method>> commands = new HashMap<Method, Map<String, Method>>();

    protected Map<Method, Object> instances = new HashMap<Method, Object>();

    protected Map<String, String> descriptions = new HashMap<String, String>();

    protected Map<String, String> helpMessages = new HashMap<String, String>();

    private ObjectInstantiator objectInstantiator;

    public void register(Class<?> clazz) {
        registerMethods(clazz, null);
    }

    public List<Command> registerAndReturn(Class<?> clazz) {
        return registerMethods(clazz, null);
    }

    protected List<Command> registerMethods(Class<?> clazz, Method parent) {
        if(getObjectInstantiator() != null) {
            return registerMethods(clazz, parent, getObjectInstantiator().instantiate(clazz));
        } else {
            return registerMethods(clazz, parent, null);
        }
    }

    /**
     * Registers all the methods of a given class.
     * @param clazz
     * @param parent
     * @param instance
     * @return
     */
    protected List<Command> registerMethods(Class<?> clazz, Method parent, Object instance) {
        Map<String, Method> map;
        List<Command> registeredCommands = new ArrayList<Command>();

        if(commands.containsKey(parent)) {
            map = commands.get(parent);
        } else {
            map = new HashMap<String, Method>();
            commands.put(parent, map);
        }

        for(Method method : clazz.getMethods()) {
            if(!method.isAnnotationPresent(Command.class))
                continue;

            Command cmd = method.getAnnotation(Command.class);

            map.put(cmd.name(), method);
            for(String alias : cmd.aliases()) {
                map.put(alias, method);
            }

            if(!Modifier.isStatic(method.getModifiers())) {
                if(instance == null) {
                    continue;
                }
                instances.put(method, instance);
            }

            if (parent == null) {
                final String commandName = cmd.aliases()[0];
                final String desc = cmd.description();

                final String usage = cmd.usage();
                if (usage.length() == 0) {
                    descriptions.put(commandName, desc);
                } else {
                    descriptions.put(commandName, usage + " - " + desc);
                }

                String help = cmd.help();
                if (help.length() == 0) {
                    help = desc;
                }

                final CharSequence arguments = getArguments(cmd);
                for (String alias : cmd.aliases()) {
                    final String helpMessage = "/" + alias + " " + arguments + "\n\n" + help;
                    final String key = alias.replaceAll("/", "");
                    String previous = helpMessages.put(key, helpMessage);

                    if (previous != null && !previous.replaceAll("^/[^ ]+ ", "").equals(helpMessage.replaceAll("^/[^ ]+ ", ""))) {
                        helpMessages.put(key, previous + "\n\n" + helpMessage);
                    }
                }

            }
        }

        if(clazz.getSuperclass() != null) {
            registerMethods(clazz.getSuperclass(), parent, instance);
        }

        return registeredCommands;
    }

    /**
     * Checks if a player has the permission to execute the command.
     * @param sender
     * @param method
     * @return
     */
    private boolean hasPermission(CommandSender sender, Method method) {
        CommandPermissions permissions = method.getAnnotation(CommandPermissions.class);

        if(permissions == null) {
            return true;
        }

        for(String permission : permissions.value()) {
            if(sender.hasPermission(permission)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a usage message for the given command
     * @param args
     * @param level
     * @param cmd
     * @return
     */
    protected String getUsage(String[] args, int level, Command cmd) {
        final StringBuilder command = new StringBuilder();

        command.append('/');

        for (int i = 0; i <= level; ++i) {
            command.append(args[i]);
            command.append(' ');
        }
        command.append(getArguments(cmd));

        final String help = cmd.help();
        if (help.length() > 0) {
            command.append("\n\n");
            command.append(help);
        }

        return command.toString();
    }

    /**
     * Returns the possible flags for the given command
     * @param cmd
     * @return
     */
    protected CharSequence getArguments(Command cmd) {
        final String flags = cmd.flags();

        final StringBuilder command2 = new StringBuilder();
        if (flags.length() > 0) {
            String flagString = flags.replaceAll(".:", "");
            if (flagString.length() > 0) {
                command2.append("[-");
                for (int i = 0; i < flagString.length(); ++i) {
                    command2.append(flagString.charAt(i));
                }
                command2.append("] ");
            }
        }

        command2.append(cmd.usage());

        return command2;
    }

    /**
     * Returns the nested usage for the given command.
     * @param args
     * @param level
     * @param method
     * @param player
     * @return
     * @throws CommandException
     */
    protected String getNestedUsage(String[] args, int level, Method method, CommandSender player) throws CommandException {
        StringBuilder command = new StringBuilder();

        command.append("/");

        for (int i = 0; i <= level; ++i) {
            command.append(args[i] + " ");
        }

        Map<String, Method> map = commands.get(method);
        boolean found = false;

        command.append("<");

        Set<String> allowedCommands = new HashSet<String>();

        for (Map.Entry<String, Method> entry : map.entrySet()) {
            Method childMethod = entry.getValue();
            found = true;

            if (hasPermission(player, childMethod)) {
                Command childCmd = childMethod.getAnnotation(Command.class);

                allowedCommands.add(childCmd.aliases()[0]);
            }
        }

        if (allowedCommands.size() > 0) {
            command.append(StringUtil.join(allowedCommands, "|"));
        } else {
            if (!found) {
                command.append("?");
            } else {
                //command.append("action");
                throw new CommandPermissionsException();
            }
        }

        command.append(">");

        return command.toString();
    }

    /**
     * Return sthe commands
     */
    public Map<String, String> getCommands() {
        return this.descriptions;
    }

    /**
     * Returns a map with the help messages.
     * @return
     */
    public Map<String, String> getHelpMessages() {
        return this.helpMessages;
    }

    /**
     * Returns all the command methods.
     * @return
     */
    public Map<Method, Map<String, Method>> getMethods() {
        return this.commands;
    }

    /**
     * Checks if a command is named such at the root level.
     * @param command
     * @return
     */
    public boolean hasCommand(String command) {
        return commands.get(null).containsKey(command.toLowerCase());
    }

    public void execute(String cmd, String[] args, CommandSender player,
                        Object... methodArgs) throws CommandException {

        String[] newArgs = new String[args.length + 1];
        System.arraycopy(args, 0, newArgs, 1, args.length);
        newArgs[0] = cmd;
        Object[] newMethodArgs = new Object[methodArgs.length + 1];
        System.arraycopy(methodArgs, 0, newMethodArgs, 1, methodArgs.length);

        executeMethod(null, newArgs, player, newMethodArgs, 0);
    }

    /**
     * Attempt to execute a command.
     *
     * @param args
     * @param player
     * @param methodArgs
     * @throws CommandException
     */
    public void execute(String[] args, CommandSender player, Object... methodArgs) throws CommandException {

        Object[] newMethodArgs = new Object[methodArgs.length + 1];
        System.arraycopy(methodArgs, 0, newMethodArgs, 1, methodArgs.length);
        executeMethod(null, args, player, newMethodArgs, 0);
    }

    /**
     * Attempt to execute a command.
     *
     * @param parent
     * @param args
     * @param player
     * @param methodArgs
     * @param level
     * @throws CommandException
     */
    public void executeMethod(Method parent, String[] args, CommandSender player, Object[] methodArgs, int level) throws CommandException {

        String cmdName = args[level];

        Map<String, Method> map = commands.get(parent);
        Method method = map.get(cmdName.toLowerCase());

        if (method == null) {
            if (parent == null) { // Root
                throw new UnhandledCommandException();
            } else {
                throw new MissingNestedCommandException("Unknown command: " + cmdName, getNestedUsage(args, level - 1, parent, player));
            }
        }

        checkPermission(player, method);

        int argsCount = args.length - 1 - level;

        // checks if we need to execute the body of the nested command method (false)
        // or display the help what commands are available (true)
        // this is all for an args count of 0 if it is > 0 and a NestedCommand Annotation is present
        // it will always handle the methods that NestedCommand points to
        // e.g.:
        //  - /cmd - @NestedCommand(executeBody = true) will go into the else loop and execute code in that method
        //  - /cmd <arg1> <arg2> - @NestedCommand(executeBody = true) will always go to the nested command class
        //  - /cmd <arg1> - @NestedCommand(executeBody = false) will always go to the nested command class not matter the args
        boolean executeNested = method.isAnnotationPresent(NestedCommand.class) && (argsCount > 0 || !method.getAnnotation(NestedCommand.class).executeBody());

        if (executeNested) {
            if (argsCount == 0) {
                throw new MissingNestedCommandException("Sub-command required.",
                        getNestedUsage(args, level, method, player));
            } else {
                executeMethod(method, args, player, methodArgs, level + 1);
            }
        } else if (method.isAnnotationPresent(CommandAlias.class)) {
            CommandAlias aCmd = method.getAnnotation(CommandAlias.class);
            executeMethod(parent, aCmd.value(), player, methodArgs, level);
        } else {
            Command cmd = method.getAnnotation(Command.class);

            String[] newArgs = new String[args.length - level];
            System.arraycopy(args, level, newArgs, 0, args.length - level);

            final Set<Character> valueFlags = new HashSet<Character>();

            char[] flags = cmd.flags().toCharArray();
            Set<Character> newFlags = new HashSet<Character>();
            for (int i = 0; i < flags.length; ++i) {
                if (flags.length > i + 1 && flags[i + 1] == ':') {
                    valueFlags.add(flags[i]);
                    ++i;
                }
                newFlags.add(flags[i]);
            }

            CommandContext context = new CommandContext(newArgs, valueFlags);

            if (context.argsLength() < cmd.minArgs()) {
                throw new CommandUsageException("Too few arguments.", getUsage(args, level, cmd));
            }

            if (cmd.maxArgs() != -1 && context.argsLength() > cmd.maxArgs()) {
                throw new CommandUsageException("Too many arguments.", getUsage(args, level, cmd));
            }

            if (!cmd.anyFlags()) {
                for (char flag : context.getFlags()) {
                    if (!newFlags.contains(flag)) {
                        throw new CommandUsageException("Unknown flag: " + flag, getUsage(args, level, cmd));
                    }
                }
            }

            methodArgs[0] = context;

            Object instance = instances.get(method);

            invokeMethod(parent, args, player, method, instance, methodArgs, argsCount);
        }
    }

    protected void checkPermission(CommandSender player, Method method) throws CommandException {
        if (!hasPermission(player, method)) {
            throw new CommandPermissionsException();
        }
    }

    public void invokeMethod(Method parent, String[] args, CommandSender player, Method method, Object instance, Object[] methodArgs, int level) throws CommandException {
        try {
            method.invoke(instance, methodArgs);
        } catch (IllegalArgumentException e) {
            CBCommonLib.LOGGER.warning("Failed to execute command!");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            CBCommonLib.LOGGER.warning("Failed to execute command!");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof CommandException) {
                throw (CommandException) e.getCause();
            }

            throw new WrappedCommandException(e.getCause());
        }
    }

    /**
     * Returns the ObjectInstantiator
     * @return
     */
    public ObjectInstantiator getObjectInstantiator() {
        return this.objectInstantiator;
    }

    /**
     * Sets the ObectInstantiator
     * @param objectInstantiator
     */
    public void setObjectInstantiator(ObjectInstantiator objectInstantiator) {
        this.objectInstantiator = objectInstantiator;
    }
}
