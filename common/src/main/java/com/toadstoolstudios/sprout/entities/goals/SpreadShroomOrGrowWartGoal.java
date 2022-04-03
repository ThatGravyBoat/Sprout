package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class SpreadShroomOrGrowWartGoal extends Goal {
    private final BounceBugEntity bug;
    private static final int TIMER_CAP = 120;
    private boolean isShroom;
    private int growTimer;
    private BlockPos shroomPos;

    public SpreadShroomOrGrowWartGoal(BounceBugEntity bug) {
        super();
        this.bug = bug;
    }

    @Override
    public boolean canStart() {
        if(bug.isNotBusy() && bug.getTargetPlant() != null) {
            return bug.isNearShroom();
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        Block block = bug.getWorld().getBlockState(bug.getTargetPlant()).getBlock();
        this.isShroom = block instanceof FungusBlock || block instanceof NetherWartBlock;
        shroomPos = bug.getTargetPlant();
    }

    @Override
    public boolean shouldContinue() {
        return bug.isNearShroom() && bug.getTargetPlant() != null && growTimer < TIMER_CAP;
    }

    @Override
    public void tick() {
        if(isShroom && growTimer % 20 == 1) {
            Box blocks = Box.from(Vec3d.ofCenter(shroomPos)).expand(5, 1, 5);
            BlockPos.stream(blocks).filter(blockPos -> bug.getWorld().getBlockState(blockPos).isAir()).filter(blockPos -> {
                BlockState blockState = bug.getWorld().getBlockState(blockPos.down());
                return blockState.isIn(BlockTags.NYLIUM) || blockState.isOf(Blocks.SOUL_SOIL) || blockState.isOf(Blocks.MYCELIUM);
            }).forEach(blockPos -> {
                if(bug.getWorld().random.nextInt(1000) == 1) {
                    bug.getWorld().setBlockState(blockPos, bug.getWorld().getBlockState(shroomPos).getBlock().getDefaultState());
                }
            });
        }
        growTimer++;
    }
}
