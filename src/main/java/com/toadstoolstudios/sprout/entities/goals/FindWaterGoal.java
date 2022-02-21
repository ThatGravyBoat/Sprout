package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.utils.EntityPathingUtils;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FindWaterGoal extends Goal {
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(12);

    private final ElephantEntity elephant;
    @Nullable
    private BlockPos targetPosition;

    public FindWaterGoal(ElephantEntity elephant) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.elephant = elephant;
    }

    @Override
    public boolean canStart() {
        findWaterSource();
        return !elephant.hasWater() && targetPosition != null && !elephant.isDrinking() && targetPosition.getSquaredDistance(elephant.getPos(), true) > 1;
    }

    @Override
    public boolean shouldContinue() {
        return targetPosition != null && targetPosition.getSquaredDistance(elephant.getPos(), true) > 1;
    }

    @Override
    public void start() {
        super.start();
        if(targetPosition != null) {
            EntityNavigation nav = elephant.getNavigation();
            nav.startMovingTo(targetPosition.getX() + 0.5, targetPosition.getY() + 0.75, targetPosition.getZ() + 0.5, 0.3);
        }
    }

    @Override
    public void stop() {
        super.stop();
        elephant.setWaterPos(targetPosition);
        targetPosition = null;
    }

    public void findWaterSource() {
        if(targetPosition != null) return;
        BlockPos.Mutable waterPos = elephant.getBlockPos().mutableCopy();
        for (BlockPos blockPos : POSITIONAL_OFFSETS){
            waterPos.set(elephant.getBlockPos(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (this.elephant.world.isWater(waterPos)){
                Path path = elephant.getNavigation().findPathTo(waterPos, 1);
                if (path != null && path.reachesTarget()) {
                    this.targetPosition = path.getTarget();
                    return;
                }
            }
        }
    }
}
