package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantBaseEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ElephantWanderGoal extends WanderAroundFarGoal {
    ElephantBaseEntity elephant;
    public ElephantWanderGoal(ElephantBaseEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
        this.elephant = pathAwareEntity;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !elephant.isWatering() && !elephant.isDrinking() && !elephant.isSitting();
    }
}
