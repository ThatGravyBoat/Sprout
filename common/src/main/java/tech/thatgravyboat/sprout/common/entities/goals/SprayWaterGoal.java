package tech.thatgravyboat.sprout.common.entities.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import tech.thatgravyboat.sprout.common.entities.ElephantEntity;
import tech.thatgravyboat.sprout.common.utils.EntityUtils;

import java.util.EnumSet;

public class SprayWaterGoal extends Goal {
    private int sprayTimer;
    private final int timerCap;
    private BlockPos plantPos;

    private final ElephantEntity elephant;

    public SprayWaterGoal(ElephantEntity elephant, int timeInSeconds) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET, Flag.LOOK));
        this.elephant = elephant;
        this.timerCap = timeInSeconds * 20;
    }

    @Override
    public boolean canUse() {
        return elephant.isNearPlant() && elephant.hasWater() && elephant.isNotBusy();
    }

    //noinspection ConstantCondition
    @Override
    public void start() {
        super.start();
        sprayTimer = 0;
        plantPos = elephant.getTargetPlant();
        //noinspection ConstantConditions
        elephant.lookAt(elephant.createCommandSourceStack().getAnchor(), Vec3.atCenterOf(elephant.getTargetPlant()));
        elephant.setWatering(true);
    }

    @Override
    public boolean canContinueToUse() {
        return elephant.isNearPlant() && sprayTimer++ <= timerCap;
    }

    @Override
    public void tick() {
        super.tick();
        if(sprayTimer < 10) return;
        //noinspection ConstantConditions
        elephant.lookAt(elephant.createCommandSourceStack().getAnchor(), Vec3.atCenterOf(elephant.getTargetPlant()));
        AABB blockBox = new AABB(plantPos.relative(elephant.getDirection())).inflate(1, 1, 1);
        ServerLevel sWorld = (ServerLevel) elephant.level;
        sWorld.sendParticles(ParticleTypes.SPLASH, plantPos.getX(), plantPos.getY(), plantPos.getZ(), 10, 1, 1, 1, 1.4);
        BlockPos.betweenClosedStream(blockBox).filter(blockPos -> EntityUtils.validBoneMealTarget(elephant.level, blockPos)).forEach(blockPos -> {
            if (elephant.level.getRandom().nextInt(125) == 1) {
                BlockState crop = elephant.level.getBlockState(blockPos);
                BonemealableBlock fertilizable = ((BonemealableBlock) crop.getBlock());
                fertilizable.performBonemeal(sWorld, elephant.level.random, blockPos, crop);
            }
        });
        BlockPos.betweenClosedStream(blockBox).filter(blockPos -> elephant.level.getBlockState(blockPos).hasProperty(BlockStateProperties.MOISTURE)).forEach(blockPos -> {
            BlockState blockState = sWorld.getBlockState(blockPos);
            int moisture = blockState.getValue(BlockStateProperties.MOISTURE);
            if(elephant.level.getRandom().nextInt(15) == 1 && moisture < 7){
                elephant.level.setBlockAndUpdate(blockPos, blockState.setValue(BlockStateProperties.MOISTURE, 7));
            }
        });
    }

    @Override
    public void stop() {
        super.stop();
        elephant.setWatering(false);
        elephant.setTargetPlant(null);
        elephant.setIfHasWater(false);
    }
}
