package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.MammothEntity;
import com.toadstoolstudios.sprout.utils.EntityPathingUtils;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreakPlantsGoal extends Goal {
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(12);
    @Nullable
    private BlockPos targetPosition;
    private final MammothEntity mammoth;

    public BreakPlantsGoal(MammothEntity mammoth) {
        this.mammoth = mammoth;
    }

    @Override
    public boolean canStart() {
        if (mammoth.isPreocupied() && mammoth.getOwner() != null) {
            findNearbyPlant();
            return targetPosition != null && Math.sqrt(targetPosition.getSquaredDistance(mammoth.getPos())) - 1 >= 1;
        } else {
            return false;
        }
    }

    public void findNearbyPlant() {
        if (targetPosition != null) return;
        BlockPos.Mutable plantPos = mammoth.getBlockPos().mutableCopy();
        for (BlockPos blockPos : POSITIONAL_OFFSETS) {
            plantPos.set(mammoth.getBlockPos(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (mammoth.world.getBlockState(plantPos).getBlock() instanceof CropBlock) {
                Path path = mammoth.getNavigation().findPathTo(plantPos, 0);
                if (path != null && path.reachesTarget()) {
                    this.targetPosition = path.getTarget();
                    return;
                }
            }
        }
    }
}
