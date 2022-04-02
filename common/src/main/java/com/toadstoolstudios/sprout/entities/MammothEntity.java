package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.entities.goals.DrinkWaterGoal;
import com.toadstoolstudios.sprout.entities.goals.FindPlantGoal;
import com.toadstoolstudios.sprout.entities.goals.FindWaterGoal;
import com.toadstoolstudios.sprout.entities.goals.SprayWaterGoal;
import com.toadstoolstudios.sprout.registry.SproutItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MammothEntity extends ElephantBaseEntity {
    public static final Ingredient BERRY_TEMPT_ITEM = Ingredient.ofItems(Items.SWEET_BERRIES);

    private final AnimationFactory factory = new AnimationFactory(this);

    public MammothEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }



    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    //Getting drinking and water states
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }
    //Setting drinking and water states
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

    }

    @Override
    public Ingredient getFoodItem() {
        return BERRY_TEMPT_ITEM;
    }

    @Override
    public boolean isPreocupied() {
        return true;
    }

    private <E extends IAnimatable>PlayState actions(AnimationEvent<E> event) {
        if(!event.isMoving() && !isInSittingPose()) {
            if (this.getIfEating()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.eating", true));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        super.registerControllers(animationData);
        animationData.addAnimationController(new AnimationController<>(this, "action_controller", 0, this::actions));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
