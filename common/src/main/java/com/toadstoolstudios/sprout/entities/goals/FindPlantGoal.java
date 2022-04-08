package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.Herbivore;
import com.toadstoolstudios.sprout.utils.EntityPathingUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class FindPlantGoal<T extends TameableEntity & Herbivore> extends Goal {
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(12);

    private final T herbivore;
    public final Predicate<Block> blockPredicate;
    @Nullable
    private BlockPos targetPosition;

    public FindPlantGoal(T herbivore, Predicate<Block> blockPredicate) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.herbivore = herbivore;
        this.blockPredicate = blockPredicate;
    }

    @Override
    public boolean canStart() {
        if (herbivore.isNotBusy() && herbivore.getOwner() != null && herbivore.specialPredicate()) {
            findNearbyPlant();
            return targetPosition != null && Math.sqrt(targetPosition.getSquaredDistance(herbivore.getPos())) - 1 >= 1;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return targetPosition != null && Math.sqrt(targetPosition.getSquaredDistance(herbivore.getPos())) - 1 >= 1;
    }

    @Override
    public void start() {
        super.start();
        if (targetPosition != null) {
            EntityNavigation nav = herbivore.getNavigation();
            nav.startMovingTo(targetPosition.getX() + 0.5, targetPosition.getY() + 0.75, targetPosition.getZ() + 0.5, 0.3);
        }
    }

    @Override
    public void stop() {
        super.stop();
        herbivore.setTargetPlant(targetPosition);
        targetPosition = null;
    }

    public void findNearbyPlant() {
        if (targetPosition != null) return;
        BlockPos.Mutable plantPos = herbivore.getBlockPos().mutableCopy();
        for (BlockPos blockPos : POSITIONAL_OFFSETS) {
            plantPos.set(herbivore.getBlockPos(), blockPos);
            if (blockPredicate.test(herbivore.world.getBlockState(plantPos).getBlock())) {
                Path path = herbivore.getNavigation().findPathTo(plantPos, 0);
                if (path != null && path.reachesTarget()) {
                    this.targetPosition = path.getTarget();
                    return;
                }
            }
        }
    }
}
