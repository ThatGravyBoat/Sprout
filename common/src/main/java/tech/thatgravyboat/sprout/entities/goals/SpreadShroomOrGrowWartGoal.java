package tech.thatgravyboat.sprout.entities.goals;

import tech.thatgravyboat.sprout.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.entities.BounceBugVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
    public boolean canUse() {
        if(!bug.isInSittingPose() && bug.isNotBusy() && bug.getTargetPlant() != null) {
            return bug.isNearShroom();
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        bug.setIfSpreadingSpores(true);
        Block block = bug.getLevel().getBlockState(bug.getTargetPlant()).getBlock();
        this.isShroom = block instanceof FungusBlock || block instanceof MushroomBlock;
        bug.lookAt(bug.createCommandSourceStack().getAnchor(), Vec3.atCenterOf(bug.getTargetPlant()));
        shroomPos = bug.getTargetPlant();
    }

    @Override
    public boolean canContinueToUse() {
        return bug.isNearShroom() && bug.getTargetPlant() != null && growTimer < TIMER_CAP && !bug.getLevel().getBlockState(shroomPos).isAir() && !bug.isInSittingPose();
    }

    @Override
    public void tick() {
        Level world = bug.getLevel();
        BounceBugVariant variant = bug.getBounceBugVariant();
        if(!world.isClientSide()) ((ServerLevel) world).sendParticles(variant.particleEffect, bug.getX(), bug.getY(), bug.getZ(), 1, 0.1, 0.1, 0.1, 0);
        //noinspection ConstantConditions
        bug.lookAt(bug.createCommandSourceStack().getAnchor(), Vec3.atCenterOf(bug.getTargetPlant()));
        if(isShroom && growTimer % 15 == 1) {
            AABB blocks = AABB.unitCubeFromLowerCorner(Vec3.atCenterOf(shroomPos)).inflate(2, 1, 2);
            BlockPos.betweenClosedStream(blocks).filter(blockPos -> world.getBlockState(blockPos).isAir())
                .filter(blockPos -> mushroomPredicate(world.getBlockState(shroomPos), world.getBlockState(blockPos.below()), world, blockPos))
                .forEach(blockPos -> {
                    if(world.random.nextInt(30) == 1) {
                        if(!world.isClientSide()) ((ServerLevel) world).sendParticles(variant.particleEffect, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2, 0.1, 0.1, 0.1, 0.2);
                        world.setBlockAndUpdate(blockPos, world.getBlockState(shroomPos).getBlock().defaultBlockState());
                    }
                });
        } else if(!isShroom) {
            AABB blocks = AABB.unitCubeFromLowerCorner(Vec3.atCenterOf(shroomPos)).inflate(1, 1, 1);
            BlockPos.betweenClosedStream(blocks).filter(blockPos -> world.getBlockState(blockPos).is(Blocks.NETHER_WART)).forEach(blockPos -> {
                BlockState state = world.getBlockState(blockPos);
                if (world.getRandom().nextInt(150) == 1) {
                    int age = state.getValue(NetherWartBlock.AGE);
                    if(age < NetherWartBlock.MAX_AGE) world.setBlockAndUpdate(blockPos, state.setValue(NetherWartBlock.AGE, age + 1));
                }
            });
        }
        growTimer++;
    }

    private static boolean mushroomPredicate(BlockState shroom, BlockState below, Level world, BlockPos pos) {
        if (shroom.getBlock() instanceof MushroomBlock) {
            return below.is(BlockTags.MUSHROOM_GROW_BLOCK) || (world.getMaxLocalRawBrightness(pos, 0) < 13 && below.isSolidRender(world, pos));
        }
        if (shroom.getBlock() instanceof FungusBlock) {
            return below.is(BlockTags.NYLIUM) || below.is(Blocks.SOUL_SOIL) || below.is(Blocks.MYCELIUM) || below.is(BlockTags.DIRT) || below.is(Blocks.FARMLAND);
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
