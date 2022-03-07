package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FindFoodGoal extends Goal {
    public final ElephantEntity elephant;
    public final Ingredient foodItem;
    public ItemEntity foodEntity;

    public FindFoodGoal(ElephantEntity elephant, Ingredient foodItem) {
        this.elephant = elephant;
        this.foodItem = foodItem;
    }

    @Override
    public boolean canStart() {
        ItemEntity nearFood = elephant.isNearFood();
        if(nearFood != null) {
            this.foodEntity = nearFood;
            return !elephant.isWatering() && this.isPreocupied();
        }
        return false;
    }

    @Override
    public void tick() {
        BlockPos foodPosition = foodEntity.getBlockPos();
        elephant.getNavigation().startMovingTo(foodPosition.getX(), foodPosition.getY(), foodPosition.getZ(), 0.3);
        elephant.lookAt(elephant.getCommandSource().getEntityAnchor(), Vec3d.ofCenter(foodPosition));
    }

    @Override
    public boolean shouldContinue() {
        BlockPos targetPosition = foodEntity.getBlockPos();
        return Math.sqrt(targetPosition.getSquaredDistance(elephant.getPos())) - 1 >= 1 && this.isPreocupied();
    }

    public boolean isPreocupied() {
        return !elephant.isWatering() && !elephant.isDrinking() && !elephant.getIfEating() && !elephant.isSitting();
    }
}
