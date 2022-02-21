package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FindWaterGoal extends Goal {

    private final ElephantEntity elephant;
    private BlockPos targetPosition;

    public FindWaterGoal(ElephantEntity elephant) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.elephant = elephant;
    }

    @Override
    public boolean canStart() {
        findWaterSource();
        return targetPosition != null;
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
        targetPosition = null;
    }

    private static int sign(int value) {
        return value % 2 == 1 ? value / -2 : value / 2;
    }


    public void findWaterSource() {
        if(targetPosition == null) {
            for (int xOffset = 0; xOffset <= 48; ++xOffset) {
                for (int zOffset = 0; zOffset <= 48; ++zOffset) {
                    int x = sign(xOffset);
                    int z = sign(zOffset);
                    int y = elephant.world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
                    BlockPos position = new BlockPos(x, y - 1, z);
                    if (elephant.world.isWater(position)) {
                        Path path = elephant.getNavigation().findPathTo(position, 1);
                        if (path != null && path.reachesTarget()) {
                            this.targetPosition = path.getTarget();
                            return;
                        }
                    }
                }
            }
        }
    }
}
