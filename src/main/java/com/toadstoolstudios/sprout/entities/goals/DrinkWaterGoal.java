package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.utils.EntityPathingUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DrinkWaterGoal extends Goal {
    private int drinkTimer;
    private final int timerCap;

    private final ElephantEntity elephant;
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(1);

    public DrinkWaterGoal(ElephantEntity elephant, int timeInSeconds) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.elephant = elephant;
        this.timerCap = timeInSeconds * 20;
    }

    @Override
    public boolean canStart() {
        if(elephant.getOwner() != null) {
            return !elephant.isWatering() && !elephant.hasWater() && elephant.isNearWater() && checkForWater();
        }
        return false;
    }

    //noinspection ConstantCondition
    @Override
    public void start() {
        super.start();
        drinkTimer = 0;
        elephant.lookAt(elephant.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(elephant.getWaterPos()));
        elephant.setDrinking(true);
    }

    @Override
    public void tick() {
        super.tick();
        elephant.lookAt(elephant.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(elephant.getWaterPos()));
    }

    @Override
    public boolean shouldContinue() {
        return checkForWater() && drinkTimer++ <= timerCap && elephant.isNearWater();
    }



    @Override
    public void stop() {
        super.stop();
        elephant.setDrinking(false);
        elephant.setWaterPos(null);
        if(drinkTimer >= timerCap) elephant.setIfHasWater(true);
    }

    public boolean checkForWater() {
        BlockPos.Mutable waterPos = elephant.getBlockPos().mutableCopy();
        for (BlockPos blockPos : POSITIONAL_OFFSETS){
            waterPos.set(elephant.getBlockPos(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (this.elephant.world.isWater(waterPos)){
                return true;
            }
        }
        return false;
    }
}
