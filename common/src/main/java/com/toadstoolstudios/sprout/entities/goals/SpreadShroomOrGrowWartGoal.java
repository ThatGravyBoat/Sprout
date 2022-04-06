package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
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
        if(!bug.isSitting() && bug.isNotBusy() && bug.getTargetPlant() != null) {
            return bug.isNearShroom();
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        bug.setIfSpreadingSpores(true);
        Block block = bug.getWorld().getBlockState(bug.getTargetPlant()).getBlock();
        this.isShroom = block instanceof FungusBlock || block instanceof MushroomPlantBlock;
        bug.lookAt(bug.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(bug.getTargetPlant()));
        shroomPos = bug.getTargetPlant();
    }

    @Override
    public boolean shouldContinue() {
        return bug.isNearShroom() && bug.getTargetPlant() != null && growTimer < TIMER_CAP && !bug.getWorld().getBlockState(shroomPos).isAir() && !bug.isSitting();
    }

    @Override
    public void tick() {
        if(!bug.getWorld().isClient) ((ServerWorld) bug.getWorld()).spawnParticles(bug.getBounceBugVariant().particleEffect, bug.getX(), bug.getY(), bug.getZ(), 1, 0.1, 0.1, 0.1, 0);
        bug.lookAt(bug.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(bug.getTargetPlant()));
        if(isShroom && growTimer % 20 == 1) {
            Box blocks = Box.from(Vec3d.ofCenter(shroomPos)).expand(5, 1, 5);
            BlockPos.stream(blocks).filter(blockPos -> bug.getWorld().getBlockState(blockPos).isAir()).filter(blockPos -> {
                BlockState blockState = bug.getWorld().getBlockState(blockPos.down());
                return blockState.isIn(BlockTags.NYLIUM) || blockState.isOf(Blocks.SOUL_SOIL) || blockState.isOf(Blocks.MYCELIUM);
            }).forEach(blockPos -> {
                if(bug.getWorld().random.nextInt(200) == 1) {
                    if(!bug.getWorld().isClient) ((ServerWorld) bug.getWorld()).spawnParticles(bug.getBounceBugVariant().particleEffect, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2, 0.1, 0.1, 0.1, 0.2);
                    bug.getWorld().setBlockState(blockPos, bug.getWorld().getBlockState(shroomPos).getBlock().getDefaultState());
                }
            });
        } else if(!isShroom) {
            Box blocks = Box.from(Vec3d.ofCenter(shroomPos)).expand(1, 1, 1);
            BlockPos.stream(blocks).filter(blockPos -> bug.getWorld().getBlockState(blockPos).isOf(Blocks.NETHER_WART)).forEach(blockPos -> {
                BlockState state = bug.getWorld().getBlockState(blockPos);
                if (bug.getWorld().getRandom().nextInt(150) == 1) {
                    int age = state.get(NetherWartBlock.AGE);
                    if(age < NetherWartBlock.field_31199) bug.getWorld().setBlockState(blockPos, state.with(NetherWartBlock.AGE, age + 1));
                }
            });
        }
        growTimer++;
    }

    @Override
    public void stop() {
        growTimer = 0;
        bug.setIfSpreadingSpores(false);
        bug.setTargetPlant(null);
        super.stop();
    }
}
