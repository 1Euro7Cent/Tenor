package de.wrolou.tenor.commands;

import de.wrolou.tenor.handlers.CommandHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;

public abstract class Command {
    private CommandHandler handler;

    public Command(CommandHandler handler) {
        this.handler = handler;

    }

    public abstract String getCalling();

    public String getPrefix() {
        return "@";
    }

    public abstract void run(String command, ClientChatEvent event, Player player);
}
