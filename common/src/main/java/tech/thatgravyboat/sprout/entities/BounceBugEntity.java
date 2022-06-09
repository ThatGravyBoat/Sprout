package tech.thatgravyboat.sprout.entities;

import tech.thatgravyboat.sprout.entities.goals.FindPlantGoal;
import tech.thatgravyboat.sprout.entities.goals.SpreadShroomOrGrowWartGoal;
import tech.thatgravyboat.sprout.entities.goals.SproutWanderGoal;
import tech.thatgravyboat.sprout.registry.SproutEntities;
import tech.thatgravyboat.sprout.registry.SproutItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BounceBugEntity extends TamableAnimal implements IAnimatable, Herbivore {

    private static final EntityDataAccessor<BounceBugVariant> BOUNCE_BUG_VARIANT = SynchedEntityData.defineId(BounceBugEntity.class, SproutEntities.BOUNCE_BUG_VARIANT);
    protected static final EntityDataAccessor<Boolean> SPREADING_SPORES = SynchedEntityData.defineId(BounceBugEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private BlockPos shroomPos;
    public final boolean isInJar;


    public BounceBugEntity(EntityType<BounceBugEntity> entityType, Level world) {
        this(entityType, world, false);
    }

    public BounceBugEntity(EntityType<BounceBugEntity> entityType, Level world, boolean isInJar) {
        super(entityType, world);
        this.isInJar = isInJar;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static boolean canSpawn(EntityType<BounceBugEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return world.getBlockState(pos.below()).is(BlockTags.NYLIUM);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        Holder<Biome> biome = world.getBiome(this.blockPosition());
        if(biome.is(Biomes.CRIMSON_FOREST)) this.setBounceBugVariant(BounceBugVariant.CRIMSON);
        if (MobSpawnType.SPAWN_EGG.equals(spawnReason) && !biome.is(Biomes.WARPED_FOREST)) {
            this.setBounceBugVariant(BounceBugVariant.random(world.getRandom()));
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(stack.is(Items.GLASS_BOTTLE)) {
            if(!this.level.isClientSide()) {
                this.setIfSpreadingSpores(false);
                stack.shrink(1);
                CompoundTag bugNbt = new CompoundTag();
                this.saveAsPassenger(bugNbt);
                ItemStack bugBottle = new ItemStack(SproutItems.BOUNCE_BUG_JAR.get());
                bugBottle.getOrCreateTag().put("bug", bugNbt);
                player.getInventory().placeItemBackInInventory(bugBottle);
                this.discard();
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            }
        } else if(this.getOwner() != null) {
            if (!this.isOrderedToSit()) {
                Block blockFromItem = Block.byItem(stack.getItem());
                if (blockFromItem instanceof FungusBlock || blockFromItem instanceof MushroomBlock) {
                    if (!level.isClientSide()) this.setOrderedToSit(true);
                    this.setIfSpreadingSpores(false);
                    ItemStack heldItem = stack.copy();
                    stack.shrink(1);
                    heldItem.setCount(1);
                    this.setItemInHand(InteractionHand.MAIN_HAND, heldItem);
                    return InteractionResult.sidedSuccess(this.level.isClientSide());
                }
            } else {
                if(!level.isClientSide()) this.setOrderedToSit(false);
                player.getInventory().placeItemBackInInventory(this.getItemInHand(InteractionHand.MAIN_HAND).copy());
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BOUNCE_BUG_VARIANT, BounceBugVariant.WARPED);
        this.getEntityData().define(SPREADING_SPORES, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("bugType", this.getBounceBugVariant().name());
        nbt.putBoolean("isSpreadingSpores", this.isSpreadingSpores());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if(nbt.contains("bugType")) {
            this.setBounceBugVariant(BounceBugVariant.getVariant(nbt.getString("bugType")));
        }
        if(nbt.contains("isSpreadingSpores")) {
            this.setIfSpreadingSpores(nbt.getBoolean("isSpreadingSpores"));
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 0.3));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FindPlantGoal<>(this, block -> block instanceof NetherWartBlock || block instanceof MushroomBlock || block instanceof FungusBlock));
        this.goalSelector.addGoal(3, new SpreadShroomOrGrowWartGoal(this));
        this.goalSelector.addGoal(8, new SproutWanderGoal<>(this, 0.2));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if(this.isInvulnerableTo(source)) return false;
        this.setOrderedToSit(false);
        this.setIfSpreadingSpores(false);
        return super.hurt(source, amount);
    }


    public boolean isNearBlock(BlockPos pos, int range) {
        return pos != null && pos.distSqr(this.blockPosition()) < range * range;
    }

    public boolean isNearShroom() {
        return isNearBlock(getTargetPlant(), 3);
    }

    @Nullable
    @Override
    public BounceBugEntity getBreedOffspring(@NotNull ServerLevel world, @NotNull AgeableMob entity) {
        return null;
    }

    @Override
    public boolean isNotBusy() {
        return !isSpreadingSpores();
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
        getEntityData().set(BOUNCE_BUG_VARIANT, variant);
    }

    public BounceBugVariant getBounceBugVariant() {
        return getEntityData().get(BOUNCE_BUG_VARIANT);
    }

    public void setIfSpreadingSpores(boolean isSpreadingSpores) {
        getEntityData().set(SPREADING_SPORES, isSpreadingSpores);
    }

    public boolean isSpreadingSpores() {
        return getEntityData().get(SPREADING_SPORES);
    }

    @Override
    public boolean specialPredicate() {
        return !this.isSpreadingSpores();
    }

    private <E extends IAnimatable>PlayState walkCycle(AnimationEvent<E> event) {
        if(!isInSittingPose()) {
            if(event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bounce_bug.walking", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bounce_bug.idling", true));
            }
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState sitStand(AnimationEvent<E> event) {
        if(isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bounce_bug.sitting", true));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable>PlayState actions(AnimationEvent<E> event) {
        if(!event.isMoving() && !isInSittingPose()) {
            if (this.isSpreadingSpores()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bounce_bug.wiggling", true));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "walk_cycle", 0, this::walkCycle));
        animationData.addAnimationController(new AnimationController<>(this, "sitting_cycle", 0, this::sitStand));
        animationData.addAnimationController(new AnimationController<>(this, "action_cycles", 5, this::actions));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
