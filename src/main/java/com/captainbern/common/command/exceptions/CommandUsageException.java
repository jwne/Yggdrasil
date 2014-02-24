package com.captainbern.common.command.exceptions;

public class CommandUsageException extends CommandException {

    protected final String USAGE;

    public CommandUsageException(String usage) {
        this.USAGE = usage;
    }

    public String getUsage() {
        return this.USAGE;
    }
}
