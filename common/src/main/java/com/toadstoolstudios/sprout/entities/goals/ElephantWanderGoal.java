package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;

public class ElephantWanderGoal extends WanderAroundFarGoal {
    ElephantEntity elephant;
    public ElephantWanderGoal(ElephantEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
        this.elephant = pathAwareEntity;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && elephant.isNotBusy();
    }
}
