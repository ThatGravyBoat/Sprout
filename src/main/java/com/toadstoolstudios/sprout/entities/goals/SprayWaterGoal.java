package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.utils.EntityPathingUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class SprayWaterGoal extends Goal {
    private int sprayTimer;
    private final int timerCap;
    private BlockPos plantPos;

    private final ElephantEntity elephant;
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(3);

    public SprayWaterGoal(ElephantEntity elephant, int timeInSeconds) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.elephant = elephant;
        this.timerCap = timeInSeconds * 20;
    }

    @Override
    public boolean canStart() {
        return elephant.isNearPlant() && elephant.hasWater();
    }

    //noinspection ConstantCondition
    @Override
    public void start() {
        super.start();
        sprayTimer = 0;
        plantPos = elephant.getPlantPos();
        elephant.lookAt(elephant.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(elephant.getPlantPos()));
        elephant.setWatering(true);
    }

    @Override
    public boolean shouldContinue() {
        if(elephant.getOwner() != null) {
            return elephant.isNearPlant() && sprayTimer++ <= timerCap;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        elephant.lookAt(elephant.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(elephant.getPlantPos()));
        if((this.sprayTimer % 40) < 20) return;
        //System.out.println(sprayTimer);
        Box blockBox = new Box(plantPos).expand(1, 1, 1);
        ServerWorld sWorld = (ServerWorld) elephant.world;
        sWorld.spawnParticles(ParticleTypes.SPLASH, plantPos.getX(), plantPos.getY(), plantPos.getZ(), 10, 1, 1, 1, 1.4);
        BlockPos.stream(blockBox).filter(blockPos -> elephant.world.getBlockState(blockPos).getBlock() instanceof Fertilizable).forEach(blockPos -> {
            if (elephant.world.getRandom().nextInt(25) == 0) {
                BlockState crop = elephant.world.getBlockState(blockPos);
                Fertilizable fertilizable = ((Fertilizable) crop.getBlock());
                fertilizable.grow(sWorld, elephant.world.random, blockPos, crop);
            }
        });
        BlockPos.stream(blockBox).filter(blockPos -> elephant.world.getBlockState(blockPos).getBlock() instanceof FarmlandBlock).forEach(blockPos -> {
            BlockState blockState = sWorld.getBlockState(blockPos);
            int moisture = blockState.get(Properties.MOISTURE);
            if(elephant.world.getRandom().nextInt(15) == 0 && moisture < 7){
                elephant.world.setBlockState(blockPos, blockState.with(Properties.MOISTURE, 7));
            }
        });
    }

    @Override
    public void stop() {
        super.stop();
        elephant.setWatering(false);
        elephant.setPlantPos(null);
        elephant.setIfHasWater(false);
    }
}
