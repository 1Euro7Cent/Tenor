package de.wrolou.tenor.util.pathfinding;

import java.util.Objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BlockPosAstar extends BlockPos {

    public BlockPosAstar(int p_121869_, int p_121870_, int p_121871_) {
        super(p_121869_, p_121870_, p_121871_);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BlockPosAstar pos = (BlockPosAstar) o;
        return pos.getX() == getX() && pos.getY() == getY() && pos.getZ() == getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    public static BlockPosAstar fromBlockPos(BlockPos pos) {
        return new BlockPosAstar(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos toBlockPos() {
        return toBlockPos(this);
    }

    public BlockPos toBlockPos(BlockPosAstar pos) {
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPosAstar above() {
        return this.relative(Direction.UP);
    }

    public BlockPosAstar below() {
        return this.relative(Direction.DOWN);
    }

    public BlockPosAstar north() {
        return this.relative(Direction.NORTH);
    }

    public BlockPosAstar south() {
        return this.relative(Direction.SOUTH);
    }

    public BlockPosAstar west() {
        return this.relative(Direction.WEST);
    }

    public BlockPosAstar east() {
        return this.relative(Direction.EAST);
    }

    public BlockPosAstar relative(Direction dir) {
        return new BlockPosAstar(this.getX() + dir.getStepX(), this.getY() + dir.getStepY(),
                this.getZ() + dir.getStepZ());
    }

}
