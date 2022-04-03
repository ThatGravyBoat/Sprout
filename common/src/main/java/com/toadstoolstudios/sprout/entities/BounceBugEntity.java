package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.entities.goals.FindPlantGoal;
import com.toadstoolstudios.sprout.entities.goals.SpreadShroomOrGrowWartGoal;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BounceBugEntity extends TameableEntity implements IAnimatable, Herbivore {

    private static final TrackedData<BounceBugVariant> BOUNCE_BUG_VARIANT = DataTracker.registerData(BounceBugEntity.class, SproutEntities.BOUNCE_BUG_VARIANT);
    private final AnimationFactory factory = new AnimationFactory(this);
    private BlockPos shroomPos;
    public final boolean isInJar;


    public BounceBugEntity(EntityType<BounceBugEntity> entityType, World world) {
        this(entityType, world, false);
    }

    public BounceBugEntity(EntityType<BounceBugEntity> entityType, World world, boolean isInJar) {
        super(entityType, world);
        this.isInJar = isInJar;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if(world.getBiome(this.getBlockPos()).matchesKey(BiomeKeys.CRIMSON_FOREST)) this.setBounceBugVariant(BounceBugVariant.CRIMSON);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOUNCE_BUG_VARIANT, BounceBugVariant.WARPED);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("bugType", this.getBounceBugVariant().name());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("bugType")) {
            this.setBounceBugVariant(BounceBugVariant.valueOf(nbt.getString("bugType")));
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.3));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(2, new FindPlantGoal<>(this, block -> block instanceof NetherWartBlock || block instanceof MushroomPlantBlock));
        this.goalSelector.add(3, new SpreadShroomOrGrowWartGoal(this));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.2));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    public boolean isNearBlock(BlockPos pos, int range) {
        return pos != null && pos.getSquaredDistance(this.getPos()) < range * range;
    }

    public boolean isNearShroom() {
        return isNearBlock(getTargetPlant(), 3);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isNotBusy() {
        return true;
    }

    @Override
    public void setTargetPlant(BlockPos pos) {
        this.shroomPos = pos;
    }

    @Override
    public @Nullable BlockPos getTargetPlant() {
        return shroomPos;
    }

    private void setBounceBugVariant(BounceBugVariant variant) {
        dataTracker.set(BOUNCE_BUG_VARIANT, variant);
    }

    public BounceBugVariant getBounceBugVariant() {
        return dataTracker.get(BOUNCE_BUG_VARIANT);
    }


    private <E extends IAnimatable> PlayState walkCycle(AnimationEvent<E> event) {
        if(!isInSittingPose()) {
            if(event.isMoving() || this.isInJar) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bounce_bug.walking", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bounce_bug.idling", true));
            }
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "walk_cycle", 0, this::walkCycle));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
