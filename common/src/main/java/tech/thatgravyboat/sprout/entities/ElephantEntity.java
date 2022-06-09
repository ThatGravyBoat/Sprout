package tech.thatgravyboat.sprout.entities;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.entities.goals.*;
import tech.thatgravyboat.sprout.registry.SproutItems;
import tech.thatgravyboat.sprout.registry.SproutParticles;
import tech.thatgravyboat.sprout.registry.SproutSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.CropBlock;
import org.jetbrains.annotations.NotNull;
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

public class ElephantEntity extends TamableAnimal implements IAnimatable, Herbivore {
    protected static final EntityDataAccessor<Boolean> DRINKING = SynchedEntityData.defineId(ElephantEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> WATERING = SynchedEntityData.defineId(ElephantEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> HAS_WATER = SynchedEntityData.defineId(ElephantEntity.class, EntityDataSerializers.BOOLEAN);
    public static final Ingredient PEANUT_TEMPT_ITEM = Ingredient.of(SproutItems.PEANUT.get());
    public static final TagKey<Item> PEANUT_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Sprout.MODID, "peanuts"));


    @Nullable
    private BlockPos waterPos;
    private BlockPos plantPos;

    private final AnimationFactory factory = new AnimationFactory(this);

    public ElephantEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<ElephantEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() > world.getSeaLevel() && world.getBlockState(pos.below()).is(BlockTags.DIRT) && world.getRandom().nextInt(10) == 0;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DRINKING, false);
        this.getEntityData().define(WATERING, false);
        this.getEntityData().define(HAS_WATER, false);
    }

    //Getting drinking and water states
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        getEntityData().set(DRINKING, nbt.getBoolean("Drinking"));
        getEntityData().set(WATERING, nbt.getBoolean("Watering"));
        getEntityData().set(HAS_WATER, nbt.getBoolean("HasWater"));
    }
    //Setting drinking and water states
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Drinking", getEntityData().get(DRINKING));
        nbt.putBoolean("Watering", getEntityData().get(WATERING));
        nbt.putBoolean("HasWater", getEntityData().get(HAS_WATER));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 0.3));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, .5, PEANUT_TEMPT_ITEM, false));
        this.goalSelector.addGoal(3, new FindWaterGoal(this));
        this.goalSelector.addGoal(3, new DrinkWaterGoal(this, 3));
        this.goalSelector.addGoal(3, new FindPlantGoal<>(this, block -> block instanceof CropBlock));
        this.goalSelector.addGoal(3, new SprayWaterGoal(this, 6));
        this.goalSelector.addGoal(8, new SproutWanderGoal<>(this, 0.2));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Nullable
    @Override
    public ElephantEntity getBreedOffspring(@NotNull ServerLevel world, @NotNull AgeableMob entity) {
        return null;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if(this.isInvulnerableTo(source)) return false;
        this.setOrderedToSit(false);
        this.setWatering(false);
        return super.hurt(source, amount);
    }

    @Override
    public InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(PEANUT_TAG)) {
            if (!isTame()) {
                if(!player.getAbilities().instabuild) stack.shrink(1);
                if (!this.level.isClientSide()) {
                    if (this.random.nextInt(10) == 0) {
                        this.tame(player);
                        this.level.broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level.broadcastEntityEvent(this, (byte) 6);
                    }
                }
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            } else if (this.getHealth() < this.getMaxHealth()) {
                if(!player.getAbilities().instabuild) stack.shrink(1);
                if (!this.level.isClientSide()) this.heal(2f);
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            }
            return InteractionResult.PASS;
        } else if(this.isTame() && this.isOwnedBy(player)) {
            if(!this.level.isClientSide()) {
                if(!this.isOrderedToSit()) this.setWatering(false);
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    public boolean isDrinking() {
        return getEntityData().get(DRINKING);
    }

    public boolean isWatering() {
        return getEntityData().get(WATERING);
    }

    public boolean hasWater() {
        return getEntityData().get(HAS_WATER);
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
        return pos != null && pos.distSqr(this.blockPosition()) < range * range;
    }

    public boolean isNearPlant() {
        return isNearBlock(getTargetPlant(), 3);
    }

    public void setDrinking(boolean bool) {
        getEntityData().set(DRINKING, bool);
    }

    public void setWatering(boolean bool) {
        getEntityData().set(WATERING, bool);
    }

    public void setIfHasWater(boolean bool) {
        getEntityData().set(HAS_WATER, bool);
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
            this.level.addParticle(SproutParticles.SNOOZE.get(), this.getX(), this.getY() + 0.8, this.getZ(), 0.01, 1, 0.01);
        }
    }

    @Environment(EnvType.CLIENT)
    private <E extends IAnimatable> void soundInitializer(SoundKeyframeEvent<E> event) {
        if (!this.level.isClientSide()) return;
        if ("sprout:sleep".equals(event.sound)) {
            this.level.playSound(Minecraft.getInstance().player, this, SproutSounds.SLEEP.get(), SoundSource.AMBIENT, 0.5f, 0.5f);
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
        return !(this.isDrinking() || this.isOrderedToSit() || this.isWatering());
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