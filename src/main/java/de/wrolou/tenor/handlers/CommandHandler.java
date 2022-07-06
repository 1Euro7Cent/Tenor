package de.wrolou.tenor.handlers;

import java.util.ArrayList;
import java.util.List;

import de.wrolou.tenor.commands.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;

public class CommandHandler {
    private List<Command> commands = new ArrayList<>();

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public void handleCommand(String command, ClientChatEvent event, Player player) {
        boolean run = false;
        boolean shouldRun = false;

        for (Command c : commands) {
            if (command.startsWith(c.getPrefix())) {
                shouldRun = true;
                event.setCanceled(true);
                String calling = command.substring(c.getPrefix().length());
                if (calling.equals(c.getCalling())) {
                    c.run(command);
                    run = true;
                }
            }
        }

        if (shouldRun && !run) {
            // todo: send message to player that the command was not found

        }
    }

}
