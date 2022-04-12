package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.entities.goals.FindPlantGoal;
import com.toadstoolstudios.sprout.entities.goals.SpreadShroomOrGrowWartGoal;
import com.toadstoolstudios.sprout.entities.goals.SproutWanderGoal;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.SproutItems;
import net.minecraft.block.Block;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class BounceBugEntity extends TameableEntity implements IAnimatable, Herbivore {

    private static final TrackedData<BounceBugVariant> BOUNCE_BUG_VARIANT = DataTracker.registerData(BounceBugEntity.class, SproutEntities.BOUNCE_BUG_VARIANT);
    protected static final TrackedData<Boolean> SPREADING_SPORES = DataTracker.registerData(BounceBugEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    public static boolean canSpawn(EntityType<BounceBugEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.NYLIUM);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> biome = world.getBiome(this.getBlockPos());
        if(biome.matchesKey(BiomeKeys.CRIMSON_FOREST)) this.setBounceBugVariant(BounceBugVariant.CRIMSON);
        if (SpawnReason.SPAWN_EGG.equals(spawnReason) && !biome.matchesKey(BiomeKeys.WARPED_FOREST)) {
            this.setBounceBugVariant(BounceBugVariant.random(world.getRandom()));
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.isOf(Items.GLASS_BOTTLE)) {
            if(!this.world.isClient()) {
                this.setOwner(player);
                this.setIfSpreadingSpores(false);
                stack.decrement(1);
                NbtCompound bugNbt = new NbtCompound();
                this.saveSelfNbt(bugNbt);
                ItemStack bugBottle = new ItemStack(SproutItems.BOUNCE_BUG_JAR.get());
                bugBottle.getOrCreateNbt().put("bug", bugNbt);
                player.getInventory().offerOrDrop(bugBottle);
                this.discard();
                return ActionResult.success(this.world.isClient());
            }
        } else if(this.getOwner() != null) {
            if (!this.isSitting()) {
                Block blockFromItem = Block.getBlockFromItem(stack.getItem());
                boolean isMushroom = blockFromItem instanceof FungusBlock || blockFromItem instanceof MushroomPlantBlock;
                if (isMushroom) {
                    if (!world.isClient) this.setSitting(true);
                    this.setIfSpreadingSpores(false);
                    ItemStack heldItem = stack.copy();
                    stack.decrement(1);
                    heldItem.setCount(1);
                    this.setStackInHand(Hand.MAIN_HAND, heldItem);
                    return ActionResult.success(this.world.isClient());
                }
            } else {
                if(!world.isClient) this.setSitting(false);
                player.getInventory().offerOrDrop(this.getStackInHand(Hand.MAIN_HAND).copy());
                this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                return ActionResult.success(this.world.isClient());
            }
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOUNCE_BUG_VARIANT, BounceBugVariant.WARPED);
        this.dataTracker.startTracking(SPREADING_SPORES, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("bugType", this.getBounceBugVariant().name());
        nbt.putBoolean("isSpreadingSpores", this.isSpreadingSpores());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("bugType")) {
            this.setBounceBugVariant(BounceBugVariant.valueOf(nbt.getString("bugType")));
        }
        if(nbt.contains("isSpreadingSpores")) {
            this.setIfSpreadingSpores(nbt.getBoolean("isSpreadingSpores"));
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.3));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(2, new FindPlantGoal<>(this, block -> block instanceof NetherWartBlock || block instanceof MushroomPlantBlock || block instanceof FungusBlock));
        this.goalSelector.add(3, new SpreadShroomOrGrowWartGoal(this));
        this.goalSelector.add(8, new SproutWanderGoal<>(this, 0.2));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(this.isInvulnerableTo(source)) return false;
        this.setSitting(false);
        this.setIfSpreadingSpores(false);
        return super.damage(source, amount);
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
        dataTracker.set(BOUNCE_BUG_VARIANT, variant);
    }

    public BounceBugVariant getBounceBugVariant() {
        return dataTracker.get(BOUNCE_BUG_VARIANT);
    }

    public void setIfSpreadingSpores(boolean isSpreadingSpores) {
        dataTracker.set(SPREADING_SPORES, isSpreadingSpores);
    }

    public boolean isSpreadingSpores() {
        return dataTracker.get(SPREADING_SPORES);
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
