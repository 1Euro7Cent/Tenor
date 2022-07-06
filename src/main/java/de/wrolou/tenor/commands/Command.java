package de.wrolou.tenor.commands;

import de.wrolou.tenor.handlers.CommandHandler;

public abstract class Command {
    private CommandHandler handler;

    public Command(CommandHandler handler) {
        this.handler = handler;

    }

    public abstract String getCalling();

    public String getPrefix() {
        return "@";
    }

    public abstract void run(String command);
}
