package com.toadstoolstudios.sprout.entities;

import com.ibm.icu.util.DateRule;
import com.toadstoolstudios.sprout.entities.goals.*;
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

public abstract class ElephantBaseEntity extends TameableEntity implements IAnimatable {
    protected static final TrackedData<Boolean> EATING = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Integer> STANDING_TIMER = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Nullable
    private BlockPos waterPos;
    private BlockPos plantPos;

    private final AnimationFactory factory = new AnimationFactory(this);

    public ElephantBaseEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(EATING, false);
        this.dataTracker.startTracking(STANDING_TIMER, 0);
    }

    //Getting drinking and water states
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(EATING, nbt.getBoolean("Eating"));
        dataTracker.set(STANDING_TIMER, nbt.getInt("StandingTimer"));
    }
    //Setting drinking and water states
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Eating", dataTracker.get(EATING));
        nbt.putInt("StandingTimer", dataTracker.get(STANDING_TIMER));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.3));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, .3, this.getFoodItem(), false));
        this.goalSelector.add(3, new EatFoodGoal(this));
        this.goalSelector.add(4, new FindFoodGoal(this, this.getFoodItem()));
        this.goalSelector.add(8, new ElephantWanderGoal(this, 0.3));
        this.goalSelector.add(9, new ElephantLookAtGoal(this, PlayerEntity.class, 6.0F));
        //this.goalSelector.add(10, new LookAroundGoal(this));
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


    public BlockPos getPlantPos() {
        return plantPos;
    }

    public void setPlantPos(BlockPos plantPos) {
        this.plantPos = plantPos;
    }

    public boolean isNearBlock(BlockPos pos, int range) {
        return pos != null && pos.getSquaredDistance(this.getPos()) < range * range;
    }

    public boolean isNearPlant() {
        return isNearBlock(plantPos, 3);
    }

    abstract public Ingredient getFoodItem();

    abstract public boolean isPreocupied();

    @Nullable
    public ItemEntity isNearFood() {
        Box areaAround = Box.from(this.getPos()).expand(15, 5, 15);
        List<Entity> itemsAround = this.world.getOtherEntities(this, areaAround, entity -> {
            if(entity instanceof ItemEntity item) {
                return Arrays.stream(getFoodItem().getMatchingStacks()).anyMatch((stack) -> stack.isOf(item.getStack().getItem()));
            }
            return false;
        });
        if(!itemsAround.isEmpty()) return (ItemEntity) itemsAround.stream().findFirst().get();
        return null;
    }

    @Nullable
    public PlayerEntity isNearPlayer() {
        Box areaAround = Box.from(this.getPos()).expand(15, 5, 15);
        List<Entity> playersAround = this.world.getOtherEntities(this, areaAround, entity -> entity instanceof PlayerEntity);
        if(!playersAround.isEmpty()){
            playersAround.sort((o1, o2) -> {
                if(o1.getBlockPos().getManhattanDistance(this.getBlockPos()) > o2.getBlockPos().getManhattanDistance(this.getBlockPos())) {
                    return 1;
                }
                else return 0;
            });
            return (PlayerEntity) playersAround.stream().findFirst().get();
        }
        return null;
    }

    public void setEating(boolean eating) {
        dataTracker.set(EATING, eating);
    }

    public boolean getIfEating() {
        return dataTracker.get(EATING);
    }

    public boolean eatFoodAroundPlayer(PlayerEntity player) {
        if(this.random.nextInt(10) == 0) {
            this.setOwner(player);
            this.world.sendEntityStatus(this, (byte) 7);
            return true;
        } else {
            this.world.sendEntityStatus(this, (byte) 6);
            return false;
        }
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
        animationData.addAnimationController(new AnimationController<>(this, "walk_controller", 10, this::walkCycle));
        animationData.addAnimationController(new AnimationController<>(this, "sit_controller", 0, this::sitStand));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    //endregion
}
