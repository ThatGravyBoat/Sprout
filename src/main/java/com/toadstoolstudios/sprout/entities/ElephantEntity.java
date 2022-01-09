package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.SproutItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

public class ElephantEntity extends TameableEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public ElephantEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.5));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.3));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return SproutEntities.ELEPHANT_ENTITY_TYPE.create(world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(this.isInvulnerableTo(source)) return false;
        this.setSitting(false);
        return super.damage(source, amount);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(SproutItems.PEANUT) && !isTamed()) {
            if(!player.getAbilities().creativeMode) stack.decrement(1);
            if(!this.world.isClient()) {
                if(this.random.nextInt(10) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }
            }
            return ActionResult.success(this.world.isClient());
        } else if(this.isTamed() && this.isOwner(player)) {
            if(!this.world.isClient()) this.setSitting(!this.isSitting());
            return ActionResult.success(this.world.isClient());
        }
        return super.interactMob(player, hand);
    }

    //region Animation

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
        if(isInSittingPose()) {
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
        animationData.addAnimationController(new AnimationController<>(this, "walk_controller", 10, this::walkCycle));
        animationData.addAnimationController(new AnimationController<>(this, "sit_controller", 10, this::sitStand));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //endregion
}
