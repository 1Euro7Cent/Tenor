package de.wrolou.tenor.commands;

import de.wrolou.tenor.Tenor;
import de.wrolou.tenor.handlers.CommandHandler;

public class TestCommand extends Command {

    public TestCommand(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getCalling() {
        return "test";
    }

    @Override
    public void run(String command) {
        Tenor.LOGGER.info("Command test called");
        Tenor.LOGGER.info("Le Test");
    }

}
