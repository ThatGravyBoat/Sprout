package com.toadstoolstudios.sprout.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathConstants;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GlowflyEntity extends AmbientEntity implements IAnimatable {
    @Nullable
    private BlockPos targetPosition;
    private final AnimationFactory factory = new AnimationFactory(this);

    public GlowflyEntity(EntityType<? extends AmbientEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public static DefaultAttributeContainer.Builder createGlowflyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (!(this.targetPosition == null || this.world.isAir(this.targetPosition) && this.targetPosition.getY() > this.world.getBottomY())) {
            this.targetPosition = null;
        }
        if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.isWithinDistance(this.getPos(), 2.0)) {
            this.targetPosition = new BlockPos(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
        }
        double x = this.targetPosition.getX() + 0.5 - this.getX();
        double y = this.targetPosition.getY() + 0.1 - this.getY();
        double z = this.targetPosition.getZ() + 0.5 - this.getZ();
        Vec3d vec3d = this.getVelocity();
        Vec3d vec3d2 = vec3d.add((Math.signum(x) * 0.05 - vec3d.x) * (double)0.05f, (Math.signum(y) * (double)0.7f - vec3d.y) * (double)0.1f, (Math.signum(z) * 0.5 - vec3d.z) * (double)0.05f);
        float f = (float)(MathHelper.atan2(vec3d2.z, vec3d2.x) * MathConstants.DEGREES_PER_RADIAN) - 90.0f;
        float g = MathHelper.wrapDegrees(f - this.getYaw());
        this.forwardSpeed = 0.1f;
        this.setYaw(this.getYaw() + g);
        this.setVelocity(vec3d2);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
        //ItemStack stack =
    }

/*
    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation flyingNavigation = new BirdNavigation(this, world) {
            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };

        flyingNavigation.setCanSwim(true);
        flyingNavigation.setCanPathThroughDoors(true);
        flyingNavigation.setCanEnterOpenDoors(false);
        return flyingNavigation;
    }
*/
    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }



    //region Animation
    private <E extends IAnimatable> PlayState walkCycle(AnimationEvent<E> event) {
        if (event.isMoving() || !this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.glowfly.flying", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.glowfly.standing", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "walk_controller", 10, this::walkCycle));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //endregion
}
