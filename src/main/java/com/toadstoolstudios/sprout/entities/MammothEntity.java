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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MammothEntity extends TameableEntity implements IAnimatable {
    protected static final TrackedData<Boolean> SWINGING = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> WATERING = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> HAS_WATER = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final Ingredient PEANUT_TEMPT_ITEM = Ingredient.ofItems(SproutItems.PEANUT);

    @Nullable
    private BlockPos waterPos;
    private BlockPos plantPos;

    private final AnimationFactory factory = new AnimationFactory(this);

    public MammothEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SWINGING, false);
        this.dataTracker.startTracking(WATERING, false);
        this.dataTracker.startTracking(HAS_WATER, false);
    }

    //Getting drinking and water states
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(SWINGING, nbt.getBoolean("Drinking"));
        dataTracker.set(WATERING, nbt.getBoolean("Watering"));
    }
    //Setting drinking and water states
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Drinking", dataTracker.get(SWINGING));
        nbt.putBoolean("Watering", dataTracker.get(WATERING));
        nbt.putBoolean("HasWater", dataTracker.get(HAS_WATER));
    }

    @Override
    protected void initGoals() {
        /*
        this.goalSelector.add(0, new EscapeDangerGoal(this, speed + .3));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new FindWaterGoal(this));
        this.goalSelector.add(3, new DrinkWaterGoal(this, 3));
        this.goalSelector.add(3, new FindPlantGoal(this));
        this.goalSelector.add(3, new SprayWaterGoal(this, 6));
        this.goalSelector.add(7, new TemptGoal(this, .5, PEANUT_TEMPT_ITEM, false));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.3));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));

         */
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

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
