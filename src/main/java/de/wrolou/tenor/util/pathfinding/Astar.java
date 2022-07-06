package de.wrolou.tenor.util.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.wrolou.tenor.Tenor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class Astar {

    public static List<BlockPos> aStar(Level world, BlockPos start_, BlockPos end_) {

        BlockPosAstar start = BlockPosAstar.fromBlockPos(start_);
        BlockPosAstar end = BlockPosAstar.fromBlockPos(end_);
        List<BlockPosAstar> openSet = new ArrayList<BlockPosAstar>();

        openSet.add(start);
        int nodes = 0;
        HashMap<BlockPosAstar, BlockPosAstar> cameFrom = new HashMap<BlockPosAstar, BlockPosAstar>();
        HashMap<BlockPosAstar, Integer> gScore = new HashMap<BlockPosAstar, Integer>();
        HashMap<BlockPosAstar, Integer> fScore = new HashMap<BlockPosAstar, Integer>();

        gScore.put(start, 0);
        fScore.put(start, heuristic(start, end));

        while (openSet.size() > 0) {
            BlockPosAstar current = getLowestScore(openSet, fScore);

            if (current.equals(end)) {
                List<BlockPos> path = new ArrayList<BlockPos>();

                for (BlockPosAstar pathBlock : reconstructPath(cameFrom, end)) {
                    path.add((BlockPos) pathBlock);
                }
                Tenor.LOGGER.info("Astar path found. Searched " + nodes + " nodes.");

                return path;
            }

            openSet.remove(current);
            nodes++;

            for (BlockPosAstar neighbor : getNeighbors(world, current)) {

                int tentativeGScore = gScore.get(current) + 1;
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, gScore.get(neighbor) + heuristic(neighbor, end));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }

            }

        }

        return null;
    }

    private static List<BlockPosAstar> reconstructPath(HashMap<BlockPosAstar, BlockPosAstar> cameFrom,
            BlockPosAstar current) {
        List<BlockPosAstar> path = new ArrayList<BlockPosAstar>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        // reverse the path

        List<BlockPosAstar> reversedPath = new ArrayList<BlockPosAstar>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }
        return reversedPath;
    }

    private static int heuristic(BlockPosAstar start, BlockPosAstar end) {
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY())
                + Math.abs(start.getZ() - end.getZ());
    }

    private static BlockPosAstar getLowestScore(List<BlockPosAstar> openSet, HashMap<BlockPosAstar, Integer> score) {
        int lowest = Integer.MAX_VALUE;
        BlockPosAstar lowestPos = null;
        for (BlockPosAstar pos : openSet) {
            if (score.getOrDefault(pos, Integer.MAX_VALUE) < lowest) {
                lowest = score.getOrDefault(pos, Integer.MAX_VALUE);
                lowestPos = pos;
            }
        }
        return lowestPos;
    }

    private static List<BlockPosAstar> getNeighbors(Level world, BlockPosAstar current) {
        List<BlockPosAstar> neighbors = new ArrayList<BlockPosAstar>();

        // top
        if (world.getBlockState(current.above().toBlockPos()).isAir() &&
                world.getBlockState(current.above().above().toBlockPos()).isAir()) {
            neighbors.add(current.above());
        }
        // bottom
        if (world.getBlockState(current.below().toBlockPos()).isAir() &&
                world.getBlockState(current.below().above().toBlockPos()).isAir()) {
            neighbors.add(current.below());
        }

        // left
        if (world.getBlockState(current.west().toBlockPos()).isAir() &&
                world.getBlockState(current.west().above().toBlockPos()).isAir()) {
            neighbors.add(current.west());
        }

        // right
        if (world.getBlockState(current.east().toBlockPos()).isAir() &&
                world.getBlockState(current.east().above().toBlockPos()).isAir()) {
            neighbors.add(current.east());
        }

        // front
        if (world.getBlockState(current.north().toBlockPos()).isAir() &&
                world.getBlockState(current.north().above().toBlockPos()).isAir()) {
            neighbors.add(current.north());
        }

        // back
        if (world.getBlockState(current.south().toBlockPos()).isAir() &&
                world.getBlockState(current.south().above().toBlockPos()).isAir()) {
            neighbors.add(current.south());
        }

        return neighbors;
    }

}
