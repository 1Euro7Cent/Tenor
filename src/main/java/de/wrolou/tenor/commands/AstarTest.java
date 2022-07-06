package de.wrolou.tenor.commands;

import java.util.List;

import de.wrolou.tenor.Tenor;
import de.wrolou.tenor.handlers.CommandHandler;
import de.wrolou.tenor.util.pathfinding.Astar;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ClientChatEvent;

public class AstarTest extends Command {

    public AstarTest(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getCalling() {
        return "astar";
    }

    @Override
    public void run(String command, ClientChatEvent event, Player player) {
        Tenor.LOGGER.info("starting Astar pathfinding");
        List<BlockPos> path = Astar.aStar(
                Minecraft.getInstance().level,
                new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()),
                new BlockPos(-39, -59, -65)

        );

        Thread thread = new Thread(() -> {
            for (BlockPos pos : path) {
                Vec3 dir = new Vec3(pos.getX() - player.getBlockX(), pos.getY() - player.getBlockY(),
                        pos.getZ() - player.getBlockZ());

                // step in between
                if (dir.x == 1) {
                    player.setPos(pos.getX(), player.getY(), player.getZ());
                } else if (dir.x == -1) {
                    player.setPos(pos.getX() + 1, player.getY(), player.getZ());
                } else if (dir.z == 1) {
                    player.setPos(player.getX(), player.getY(), pos.getZ());
                } else if (dir.z == -1) {
                    player.setPos(player.getX(), player.getY(), pos.getZ() + 1);
                } else if (dir.y == 1) {
                    player.setPos(player.getX(), pos.getY(), player.getZ());
                } else if (dir.y == -1) {
                    player.setPos(player.getX(), pos.getY() + 0.5, player.getZ());
                }

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                player.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        // Tenor.LOGGER.info("Path: " + path);

    }

}
