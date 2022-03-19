package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantBaseEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.utils.EntityPathingUtils;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class FindPlantGoal extends Goal {
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(12);

    private final ElephantBaseEntity elephant;
    @Nullable
    private BlockPos targetPosition;

    public FindPlantGoal(ElephantBaseEntity elephant) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.elephant = elephant;
    }

    @Override
    public boolean canStart() {
        if (elephant.isPreocupied() && elephant.getOwner() != null) {
            findNearbyPlant();
            return targetPosition != null && Math.sqrt(targetPosition.getSquaredDistance(elephant.getPos())) - 1 >= 1;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return targetPosition != null && Math.sqrt(targetPosition.getSquaredDistance(elephant.getPos())) - 1 >= 1;
    }

    @Override
    public void start() {
        super.start();
        if (targetPosition != null && !elephant.getIfEating()) {
            EntityNavigation nav = elephant.getNavigation();
            nav.startMovingTo(targetPosition.getX() + 0.5, targetPosition.getY() + 0.75, targetPosition.getZ() + 0.5, 0.3);
        }
    }

    @Override
    public void stop() {
        super.stop();
        elephant.setPlantPos(targetPosition);
        targetPosition = null;
    }

    public void findNearbyPlant() {
        if (targetPosition != null) return;
        BlockPos.Mutable plantPos = elephant.getBlockPos().mutableCopy();
        for (BlockPos blockPos : POSITIONAL_OFFSETS) {
            plantPos.set(elephant.getBlockPos(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (elephant.world.getBlockState(plantPos).getBlock() instanceof CropBlock) {
                Path path = elephant.getNavigation().findPathTo(plantPos, 0);
                if (path != null && path.reachesTarget()) {
                    this.targetPosition = path.getTarget();
                    return;
                }
            }
        }
    }
}
