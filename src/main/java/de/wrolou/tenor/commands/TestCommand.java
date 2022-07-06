package de.wrolou.tenor.commands;

import de.wrolou.tenor.Tenor;
import de.wrolou.tenor.handlers.CommandHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;

public class TestCommand extends Command {

    public TestCommand(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getCalling() {
        return "test";
    }

    @Override
    public void run(String command, ClientChatEvent event, Player player) {
        Tenor.LOGGER.info("Command test called");
        Tenor.LOGGER.info("TE§ST");
    }

}
