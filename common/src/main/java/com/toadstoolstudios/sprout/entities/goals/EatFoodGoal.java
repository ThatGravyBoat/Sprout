package com.toadstoolstudios.sprout.entities.goals;

import com.toadstoolstudios.sprout.entities.ElephantBaseEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.registry.SproutItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class EatFoodGoal extends Goal {
    private final ElephantBaseEntity elephant;
    private ItemEntity foodEntity;
    private ItemStack foodStackCopy;
    private int ticker;
    private final int EAT_TIME = 15;

    public EatFoodGoal(ElephantBaseEntity elephant) {
        this.elephant = elephant;
    }

    @Override
    public boolean canStart() {
        ItemEntity nearFood = elephant.isNearFood();
        if(nearFood != null) {
            this.foodEntity = nearFood;
            this.foodStackCopy = nearFood.getStack().copy();
            BlockPos targetPosition = nearFood.getBlockPos();
            return Math.sqrt(targetPosition.getSquaredDistance(elephant.getPos())) - 1 <= 1 && elephant.isPreocupied();
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        elephant.lookAt(elephant.getCommandSource().getEntityAnchor(), foodEntity.getPos());
        ServerWorld sWorld = (ServerWorld) elephant.world;
        sWorld.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, foodStackCopy), elephant.getX(), elephant.getY() + 0.4, elephant.getZ(), 1, 0.2, 0, 0.2, 0.05);
        if(ticker == 0) {
            elephant.sendPickup(foodEntity, 1);
            if(foodEntity.getStack().getCount() == 1) {
                foodEntity.discard();
            }
            foodEntity.getStack().decrement(1);
        }

        if(ticker == EAT_TIME - 1) {
            if(elephant.getOwner() == null) {
                PlayerEntity nearPlayer = elephant.isNearPlayer();
                if(nearPlayer != null) {
                    elephant.eatFoodAroundPlayer(nearPlayer);
                }
            }
            if(elephant.getHealth() < elephant.getMaxHealth()) {
                elephant.heal(2.0F);
                sWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER, elephant.getX(), elephant.getY(), elephant.getZ(), 4, 1, 1, 1, 1.4);
            }
        }

        ticker++;
    }

    @Override
    public void start() {
        super.start();
        this.elephant.setEating(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.elephant.setEating(false);
        ticker = 0;
    }

    @Override
    public boolean shouldContinue() {
        if(ticker >= EAT_TIME) {
            if(foodEntity.isAlive()) ticker = 0;
        }
        BlockPos targetPosition = foodEntity.getBlockPos();
        return ticker < EAT_TIME && Math.sqrt(targetPosition.getSquaredDistance(elephant.getPos())) - 1 <= 1;
    }
}
