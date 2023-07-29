package top.fpsmaster.utils.special.vac;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Block {
    private BlockPos blockPos;
    private EnumFacing sideOfHit;

    public Block(BlockPos blockPos, EnumFacing sideOfHit) {
        this.blockPos = blockPos;
        this.sideOfHit = sideOfHit;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public EnumFacing getSideOfHit() {
        return sideOfHit;
    }

    public void setSideOfHit(EnumFacing sideOfHit) {
        this.sideOfHit = sideOfHit;
    }
}
