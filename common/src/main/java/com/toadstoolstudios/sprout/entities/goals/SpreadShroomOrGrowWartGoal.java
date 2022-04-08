package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.BounceBugVariant;
import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
        //noinspection ConstantConditions
        bug.lookAt(bug.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(bug.getTargetPlant()));
        shroomPos = bug.getTargetPlant();
    }

    @Override
    public boolean shouldContinue() {
        return bug.isNearShroom() && bug.getTargetPlant() != null && growTimer < TIMER_CAP && !bug.getWorld().getBlockState(shroomPos).isAir() && !bug.isSitting();
    }

    @Override
    public void tick() {
        World world = bug.getWorld();
        BounceBugVariant variant = bug.getBounceBugVariant();
        if(!world.isClient) ((ServerWorld) world).spawnParticles(variant.particleEffect, bug.getX(), bug.getY(), bug.getZ(), 1, 0.1, 0.1, 0.1, 0);
        //noinspection ConstantConditions
        bug.lookAt(bug.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(bug.getTargetPlant()));
        if(isShroom && growTimer % 15 == 1) {
            Box blocks = Box.from(Vec3d.ofCenter(shroomPos)).expand(2, 1, 2);
            BlockPos.stream(blocks).filter(blockPos -> world.getBlockState(blockPos).isAir())
                .filter(blockPos -> mushroomPredicate(world.getBlockState(shroomPos), world.getBlockState(blockPos.down()), world, blockPos))
                .forEach(blockPos -> {
                    if(world.random.nextInt(30) == 1) {
                        if(!world.isClient) ((ServerWorld) world).spawnParticles(variant.particleEffect, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2, 0.1, 0.1, 0.1, 0.2);
                        world.setBlockState(blockPos, world.getBlockState(shroomPos).getBlock().getDefaultState());
                    }
                });
        } else if(!isShroom) {
            Box blocks = Box.from(Vec3d.ofCenter(shroomPos)).expand(1, 1, 1);
            BlockPos.stream(blocks).filter(blockPos -> world.getBlockState(blockPos).isOf(Blocks.NETHER_WART)).forEach(blockPos -> {
                BlockState state = world.getBlockState(blockPos);
                if (world.getRandom().nextInt(150) == 1) {
                    int age = state.get(NetherWartBlock.AGE);
                    if(age < NetherWartBlock.field_31199) world.setBlockState(blockPos, state.with(NetherWartBlock.AGE, age + 1));
                }
            });
        }
        growTimer++;
    }

    private static boolean mushroomPredicate(BlockState shroom, BlockState below, World world, BlockPos pos) {
        if (shroom.getBlock() instanceof MushroomPlantBlock) {
            return below.isIn(BlockTags.MUSHROOM_GROW_BLOCK) || (world.getBaseLightLevel(pos, 0) < 13 && below.isOpaqueFullCube(world, pos));
        }
        if (shroom.getBlock() instanceof FungusBlock) {
            return below.isIn(BlockTags.NYLIUM) || below.isOf(Blocks.SOUL_SOIL) || below.isOf(Blocks.MYCELIUM) || below.isIn(BlockTags.DIRT) || below.isOf(Blocks.FARMLAND);
        }
        return false;
    }

    @Override
    public void stop() {
        growTimer = 0;
        bug.setIfSpreadingSpores(false);
        bug.setTargetPlant(null);
        super.stop();
    }
}
