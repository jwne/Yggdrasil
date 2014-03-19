package com.captainbern.yggdrasil.command;

import com.captainbern.yggdrasil.command.exceptions.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DefaultCommandExecutor implements CommandExecutor {

    private final CommandManager COMMAND_MANAGER;

    private String NO_PERMISSION = ChatColor.DARK_RED + "Error: " + ChatColor.RED + " You don't have permission";
    private String ERROR_OCCURRED = ChatColor.RED + "An unknown error occurred. See the console for more info.";
    private String NUMBER_EXCEPTION = ChatColor.RED + "Number expected, string received instead.";

    public DefaultCommandExecutor(CommandManager commandManager) {
        this.COMMAND_MANAGER = commandManager;
    }

    public String getNoPermissionMessage() {
        return this.NO_PERMISSION;
    }

    public String getErrorOccurredMessage() {
        return this.ERROR_OCCURRED;
    }

    public String getNumberExceptionMessage() {
        return this.NUMBER_EXCEPTION;
    }

    public void setNoPermissionMessage(String message) {
        this.NO_PERMISSION = message;
    }

    public void setErrorOccurredMessage(String message) {
        this.ERROR_OCCURRED = message;
    }

    public void setNumberExceptionMessage(String message) {
        this.NUMBER_EXCEPTION = message;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.COMMAND_MANAGER.getCommandRegistrationService().execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(this.NO_PERMISSION);
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(this.NUMBER_EXCEPTION);
            } else {
                sender.sendMessage(this.ERROR_OCCURRED);
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }
        return true;
    }
}
