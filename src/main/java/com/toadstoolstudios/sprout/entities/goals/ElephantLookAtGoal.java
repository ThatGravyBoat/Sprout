package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantBaseEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public class ElephantLookAtGoal extends LookAtEntityGoal {
    ElephantBaseEntity elephant;
    public ElephantLookAtGoal(ElephantBaseEntity mob, Class<? extends LivingEntity> targetType, float range) {
        super(mob, targetType, range);
        this.elephant = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !elephant.isWatering() && !elephant.isDrinking() && !elephant.isSitting();
    }
}
