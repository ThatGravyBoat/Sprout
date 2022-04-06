package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.Herbivore;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.passive.TameableEntity;

public class SproutWanderGoal<T extends TameableEntity & Herbivore> extends WanderAroundFarGoal {
    T elephant;
    public SproutWanderGoal(T pathAwareEntity, double d) {
        super(pathAwareEntity, d);
        this.elephant = pathAwareEntity;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && elephant.isNotBusy();
    }
}
