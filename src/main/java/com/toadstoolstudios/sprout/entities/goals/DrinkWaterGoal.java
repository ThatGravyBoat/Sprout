package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class DrinkWaterGoal extends Goal {
    private final ElephantEntity elephant;
    private BlockPos targetPosition;

    public DrinkWaterGoal(ElephantEntity elephant) {
        this.setControls(EnumSet.of(Control.MOVE, Control.TARGET, Control.LOOK));
        this.elephant = elephant;
    }

    @Override
    public boolean canStart() {
        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    public void checkForWater() {

    }
}
