package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;

public class ElephantLookAtGoal extends LookAtEntityGoal {
    ElephantEntity elephant;
    public ElephantLookAtGoal(ElephantEntity mob, Class<? extends LivingEntity> targetType, float range) {
        super(mob, targetType, range);
        this.elephant = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !elephant.isPreocupied();
    }
}
