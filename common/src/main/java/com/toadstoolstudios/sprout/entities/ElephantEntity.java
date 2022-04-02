package com.toadstoolstudios.sprout.entities;

import com.ibm.icu.util.DateRule;
import com.toadstoolstudios.sprout.entities.goals.*;
import com.toadstoolstudios.sprout.registry.SproutItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Arrays;
import java.util.List;

public class ElephantEntity extends ElephantBaseEntity {
    protected static final TrackedData<Boolean> DRINKING = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> EATING = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> WATERING = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> HAS_WATER = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Integer> STANDING_TIMER = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final Ingredient PEANUT_TEMPT_ITEM = Ingredient.ofItems(SproutItems.PEANUT.get());

    @Nullable
    private BlockPos waterPos;
    private BlockPos plantPos;

    private final AnimationFactory factory = new AnimationFactory(this);

    public ElephantEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DRINKING, false);
        this.dataTracker.startTracking(WATERING, false);
        this.dataTracker.startTracking(HAS_WATER, false);
        this.dataTracker.startTracking(EATING, false);
        this.dataTracker.startTracking(STANDING_TIMER, 0);
    }

    //Getting drinking and water states
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(DRINKING, nbt.getBoolean("Drinking"));
        dataTracker.set(WATERING, nbt.getBoolean("Watering"));
        dataTracker.set(HAS_WATER, nbt.getBoolean("HasWater"));
    }
    //Setting drinking and water states
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Drinking", dataTracker.get(DRINKING));
        nbt.putBoolean("Watering", dataTracker.get(WATERING));
        nbt.putBoolean("HasWater", dataTracker.get(HAS_WATER));
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new FindWaterGoal(this));
        this.goalSelector.add(5, new DrinkWaterGoal(this, 3));
        this.goalSelector.add(5, new FindPlantGoal(this));
        this.goalSelector.add(5, new SprayWaterGoal(this, 6));
    }



    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(this.isInvulnerableTo(source)) return false;
        this.setSitting(false);
        return super.damage(source, amount);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(this.isTamed() && this.isOwner(player)) {
            if(isSitting()) {
                startStandingTimer();
                //this.setInSittingPose(false);
                return ActionResult.success(this.world.isClient());
            } else {
                resetStandingTimer();
                if(!this.world.isClient()) this.setSitting(!this.isSitting());
                return ActionResult.success(this.world.isClient());
            }
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if(getStandingTimer() > 0) {
            reduceStandingTimer();
        }
        if(getStandingTimer() == 1) {
            this.setSitting(false);
        }
    }

    public boolean isDrinking() {
        return dataTracker.get(DRINKING);
    }

    public boolean isWatering() {
        return dataTracker.get(WATERING);
    }

    public boolean hasWater() {
        return dataTracker.get(HAS_WATER);
    }

    public int getStandingTimer() {
        return dataTracker.get(STANDING_TIMER);
    }

    public void reduceStandingTimer() {
        dataTracker.set(STANDING_TIMER, getStandingTimer() - 1);
    }

    public void startStandingTimer() {
        dataTracker.set(STANDING_TIMER, 15);
    }

    public void resetStandingTimer() {
        dataTracker.set(STANDING_TIMER, 0);
    }

    @Nullable
    public BlockPos getWaterPos() {
        return waterPos;
    }

    public void setWaterPos(@Nullable BlockPos waterPos) {
        this.waterPos = waterPos;
    }

    public boolean isNearWater() {
        return isNearBlock(waterPos, 1);
    }

    public void setDrinking(boolean bool) {
        dataTracker.set(DRINKING, bool);
    }

    public void setWatering(boolean bool) {
        dataTracker.set(WATERING, bool);
    }

    public void setIfHasWater(boolean bool) {
        dataTracker.set(HAS_WATER, bool);
    }

    @Override
    public Ingredient getFoodItem() {
        return PEANUT_TEMPT_ITEM;
    }

    @Override
    public boolean isPreocupied() {
        return !this.isWatering() && !this.isDrinking() && !this.getIfEating() && !this.isSitting();
    }

    //region Animation

    private <E extends IAnimatable>PlayState actions(AnimationEvent<E> event) {
        if(!event.isMoving() && !isInSittingPose()) {
            if (this.isDrinking()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.drinking", false));
                return PlayState.CONTINUE;
            } else if (this.isWatering()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.watering", true));
                return PlayState.CONTINUE;
            } else if (this.getIfEating()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.eating", true));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState walkCycle(AnimationEvent<E> event) {
        if(!isInSittingPose()) {
            if(event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.walk", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.idle", true));
            }
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState sitStand(AnimationEvent<E> event) {
        Animation animation = event.getController().getCurrentAnimation();
        if(isInSittingPose() && getStandingTimer() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.sit", false).addAnimation("animation.elephant.sitting", true));
            return PlayState.CONTINUE;
        } else if(animation != null && animation.animationName.equals("animation.elephant.sitting")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.stand", false));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "action_controller", 10, this::actions));
        animationData.addAnimationController(new AnimationController<>(this, "walk_controller", 10, this::walkCycle));
        animationData.addAnimationController(new AnimationController<>(this, "sit_controller", 0, this::sitStand));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //endregion
}
