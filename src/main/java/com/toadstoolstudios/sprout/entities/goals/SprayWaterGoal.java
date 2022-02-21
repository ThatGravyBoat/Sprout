package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SprayWaterGoal extends FindWaterGoal {
    ElephantEntity elephant;

    public SprayWaterGoal(ElephantEntity elephantEntity) {
        super(elephantEntity);
        this.setControls(EnumSet.of(Control.TARGET, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return false;
    }
}
