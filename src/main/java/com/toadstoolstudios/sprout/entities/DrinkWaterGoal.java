package com.toadstoolstudios.sprout.entities;

import net.minecraft.entity.ai.goal.Goal;

public class DrinkWaterGoal extends Goal {
    @Override
    public boolean canStart() {
        return false;
    }
}
