package tech.thatgravyboat.sprout.common.entities.goals;

import tech.thatgravyboat.sprout.common.entities.ElephantEntity;
import tech.thatgravyboat.sprout.common.utils.EntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class FindWaterGoal extends Goal {
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityUtils.getPositionalOffsets(12);

    private final ElephantEntity elephant;
    @Nullable
    private BlockPos targetPosition;

    public FindWaterGoal(ElephantEntity elephant) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET, Flag.LOOK));
        this.elephant = elephant;
    }

    @Override
    public boolean canUse() {
        if(elephant.getOwner() != null && !elephant.hasWater()) {
            findWaterSource();
            return targetPosition != null && elephant.isNotBusy() && targetPosition.distSqr(elephant.blockPosition()) > 1;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPosition != null && targetPosition.distSqr(elephant.blockPosition()) > 1;
    }

    @Override
    public void start() {
        super.start();
        if(targetPosition != null) {
            PathNavigation nav = elephant.getNavigation();
            nav.moveTo(targetPosition.getX() + 0.5, targetPosition.getY() + 0.75, targetPosition.getZ() + 0.5, 0.3);
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
        BlockPos.MutableBlockPos waterPos = elephant.blockPosition().mutable();
        for (BlockPos blockPos : POSITIONAL_OFFSETS){
            waterPos.setWithOffset(elephant.blockPosition(), blockPos);
            if (this.elephant.level.isWaterAt(waterPos)){
                Path path = elephant.getNavigation().createPath(waterPos, 1);
                this.targetPosition = path == null ? null : path.getTarget();
                return;
            }
        }
    }
}
