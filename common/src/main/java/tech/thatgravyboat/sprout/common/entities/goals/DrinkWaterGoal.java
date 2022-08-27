package tech.thatgravyboat.sprout.common.entities.goals;

import tech.thatgravyboat.sprout.common.entities.ElephantEntity;
import tech.thatgravyboat.sprout.common.utils.EntityPathingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class DrinkWaterGoal extends Goal {
    private int drinkTimer;
    private final int timerCap;

    private final ElephantEntity elephant;
    private static final List<BlockPos> POSITIONAL_OFFSETS = EntityPathingUtils.getPositionalOffsets(1);

    public DrinkWaterGoal(ElephantEntity elephant, int timeInSeconds) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET, Flag.LOOK));
        this.elephant = elephant;
        this.timerCap = timeInSeconds * 20;
    }

    @Override
    public boolean canUse() {
        if(elephant.getOwner() != null && !elephant.hasWater()) {
            return !elephant.isWatering() && !elephant.hasWater() && elephant.isNearWater() && checkForWater();
        }
        return false;
    }

    //noinspection ConstantCondition
    @Override
    public void start() {
        super.start();
        drinkTimer = 0;
        //noinspection ConstantConditions
        elephant.lookAt(elephant.createCommandSourceStack().getAnchor(), Vec3.atCenterOf(elephant.getWaterPos()));
        elephant.setDrinking(true);
    }

    @Override
    public void tick() {
        super.tick();
        //noinspection ConstantConditions
        elephant.lookAt(elephant.createCommandSourceStack().getAnchor(), Vec3.atCenterOf(elephant.getWaterPos()));
    }

    @Override
    public boolean canContinueToUse() {
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
        BlockPos.MutableBlockPos waterPos = elephant.blockPosition().mutable();
        for (BlockPos blockPos : POSITIONAL_OFFSETS){
            waterPos.setWithOffset(elephant.blockPosition(), blockPos);
            if (this.elephant.level.isWaterAt(waterPos)){
                return true;
            }
        }
        return false;
    }
}
