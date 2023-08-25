package tech.thatgravyboat.sprout.common.entities.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.sprout.common.entities.Herbivore;
import tech.thatgravyboat.sprout.common.utils.EntityUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.function.BiPredicate;

public class FindPlantGoal<T extends TamableAnimal & Herbivore> extends Goal {
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityUtils.getPositionalOffsets(12);

    private final T herbivore;
    public final BiPredicate<Level, BlockPos> blockPredicate;
    @Nullable
    private BlockPos targetPosition;

    public FindPlantGoal(T herbivore, BiPredicate<Level, BlockPos> blockPredicate) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET, Flag.LOOK));
        this.herbivore = herbivore;
        this.blockPredicate = blockPredicate;
    }

    @Override
    public boolean canUse() {
        if (herbivore.isNotBusy() && herbivore.getOwner() != null && herbivore.specialPredicate()) {
            findNearbyPlant();
            return targetPosition != null && Math.sqrt(targetPosition.distSqr(herbivore.blockPosition())) - 1 >= 1;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPosition != null && Math.sqrt(targetPosition.distSqr(herbivore.blockPosition())) - 1 >= 1;
    }

    @Override
    public void start() {
        super.start();
        if (targetPosition != null) {
            PathNavigation nav = herbivore.getNavigation();
            nav.moveTo(targetPosition.getX() + 0.5, targetPosition.getY() + 0.75, targetPosition.getZ() + 0.5, 0.3);
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
        BlockPos.MutableBlockPos plantPos = herbivore.blockPosition().mutable();
        for (BlockPos blockPos : POSITIONAL_OFFSETS) {
            plantPos.setWithOffset(herbivore.blockPosition(), blockPos);
            if (blockPredicate.test(herbivore.level, plantPos)) {
                Path path = herbivore.getNavigation().createPath(plantPos, 0);
                if (path != null && path.canReach()) {
                    this.targetPosition = path.getTarget();
                    return;
                }
            }
        }
    }
}
