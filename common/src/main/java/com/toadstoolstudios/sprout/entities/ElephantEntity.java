package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.goals.*;
import com.toadstoolstudios.sprout.registry.SproutItems;
import com.toadstoolstudios.sprout.registry.SproutParticles;
import com.toadstoolstudios.sprout.registry.SproutSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class ElephantEntity extends TameableEntity implements IAnimatable, Herbivore {
    protected static final TrackedData<Boolean> DRINKING = DataTracker.registerData(ElephantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> WATERING = DataTracker.registerData(ElephantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> HAS_WATER = DataTracker.registerData(ElephantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final Ingredient PEANUT_TEMPT_ITEM = Ingredient.ofItems(SproutItems.PEANUT.get());
    public static final TagKey<Item> PEANUT_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Sprout.MODID, "peanuts"));


    @Nullable
    private BlockPos waterPos;
    private BlockPos plantPos;

    private final AnimationFactory factory = new AnimationFactory(this);

    public ElephantEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<ElephantEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return pos.getY() > world.getSeaLevel() && world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && world.getRandom().nextInt(10) == 0;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DRINKING, false);
        this.dataTracker.startTracking(WATERING, false);
        this.dataTracker.startTracking(HAS_WATER, false);
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
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.3));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, .5, PEANUT_TEMPT_ITEM, false));
        this.goalSelector.add(3, new FindWaterGoal(this));
        this.goalSelector.add(3, new DrinkWaterGoal(this, 3));
        this.goalSelector.add(3, new FindPlantGoal<>(this, block -> block instanceof CropBlock));
        this.goalSelector.add(3, new SprayWaterGoal(this, 6));
        this.goalSelector.add(8, new SproutWanderGoal<>(this, 0.2));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
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
        this.setWatering(false);
        return super.damage(source, amount);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isIn(PEANUT_TAG)) {
            if (!isTamed()) {
                if(!player.getAbilities().creativeMode) stack.decrement(1);
                if (!this.world.isClient()) {
                    if (this.random.nextInt(10) == 0) {
                        this.setOwner(player);
                        this.world.sendEntityStatus(this, (byte) 7);
                    } else {
                        this.world.sendEntityStatus(this, (byte) 6);
                    }
                }
                return ActionResult.success(this.world.isClient());
            } else if (this.getHealth() < this.getMaxHealth()) {
                if(!player.getAbilities().creativeMode) stack.decrement(1);
                if (!this.world.isClient) this.heal(2f);
                return ActionResult.success(this.world.isClient());
            }
            return ActionResult.PASS;
        } else if(this.isTamed() && this.isOwner(player)) {
            if(!this.world.isClient()) {
                if(!this.isSitting()) this.setWatering(false);
                this.setSitting(!this.isSitting());
            }
            return ActionResult.success(this.world.isClient());
        }
        return super.interactMob(player, hand);
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

    @Nullable
    public BlockPos getWaterPos() {
        return waterPos;
    }

    public void setWaterPos(@Nullable BlockPos waterPos) {
        this.waterPos = waterPos;
    }

    public boolean isNearWater() {
        return isNearBlock(getWaterPos(), 1);
    }

    public boolean isNearBlock(BlockPos pos, int range) {
        return pos != null && pos.getSquaredDistance(this.getPos()) < range * range;
    }

    public boolean isNearPlant() {
        return isNearBlock(getTargetPlant(), 3);
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

    //region Animation

    private <E extends IAnimatable>PlayState actions(AnimationEvent<E> event) {
        if(!event.isMoving() && !isInSittingPose()) {
            if (this.isDrinking()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.drinking", false));
                return PlayState.CONTINUE;
            } else if (this.isWatering()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.watering", true));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState walkCycle(AnimationEvent<E> event) {
        if(!isInSittingPose()) {
            if(event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.walking", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.idling", true));
            }
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState sitStand(AnimationEvent<E> event) {
        if(isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.elephant.sleeping", true));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable> void particleInitializer(ParticleKeyFrameEvent<E> event) {
        if ("sprout:snooze".equals(event.effect)) {
            this.world.addParticle(SproutParticles.SNOOZE.get(), this.getX(), this.getY() + 0.8, this.getZ(), 0.01, 1, 0.01);
        }
    }

    @Environment(EnvType.CLIENT)
    private <E extends IAnimatable> void soundInitializer(SoundKeyframeEvent<E> event) {
        if (!this.world.isClient()) return;
        if ("sprout:sleep".equals(event.sound)) {
            this.world.playSoundFromEntity(MinecraftClient.getInstance().player, this, SproutSounds.SLEEP.get(), SoundCategory.AMBIENT, 0.5f, 0.5f);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "action_controller", 5, this::actions));
        animationData.addAnimationController(new AnimationController<>(this, "walk_controller", 5, this::walkCycle));

        var sleepController = new AnimationController<>(this, "sit_controller", 2, this::sitStand);
        sleepController.registerParticleListener(this::particleInitializer);
        sleepController.registerSoundListener(this::soundInitializer);
        animationData.addAnimationController(sleepController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public boolean isNotBusy() {
        return !(this.isDrinking() || this.isSitting() || this.isWatering());
    }


    @Override
    public boolean specialPredicate() {
        return hasWater();
    }

    @Override
    public void setTargetPlant(BlockPos pos) {
        this.plantPos = pos;
    }

    @Override
    public @Nullable BlockPos getTargetPlant() {
        return plantPos;
    }

    //endregion
}