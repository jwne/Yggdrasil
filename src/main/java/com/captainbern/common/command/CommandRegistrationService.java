package com.captainbern.common.command;

import com.captainbern.common.command.core.Command;
import com.captainbern.common.command.core.CommandPermissions;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistrationService {

    protected Map<Method, Map<String, Method>> commands = new HashMap<Method, Map<String, Method>>();

    protected Map<Method, Object> instances = new HashMap<Method, Object>();

    protected Map<String, String> descriptions = new HashMap<String, String>();

    protected Map<String, String> help = new HashMap<String, String>();

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

            /**
             * TODO:
             * - create fancy help
             * - add nexted commands
             */
        }

        if(clazz.getSuperclass() != null) {
            registerMethods(clazz.getSuperclass(), parent, instance);
        }

        return registeredCommands;
    }

    private boolean hasPermission(CommandSender sender, Method method) {
        CommandPermissions permissions = method.getAnnotation(CommandPermissions.class);

        if(permissions == null) {
            return true;
        }

        for(String permission : permissions.permissions()) {
            if(sender.hasPermission(permission)) {
                return true;
            }
        }

        return false;
    }

    public Map<String, String> getHelpMessages() {
        return this.help;
    }

    public Map<String, String> getDescriptions() {
        return this.descriptions;
    }

    public Map<Method, Map<String, Method>> getMethods() {
        return this.commands;
    }

    public ObjectInstantiator getObjectInstantiator() {
        return this.objectInstantiator;
    }

    public void setObjectInstantiator(ObjectInstantiator objectInstantiator) {
        this.objectInstantiator = objectInstantiator;
    }
}
