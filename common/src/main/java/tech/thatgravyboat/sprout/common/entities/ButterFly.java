package tech.thatgravyboat.sprout.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import tech.thatgravyboat.sprout.common.registry.SproutItems;

public class ButterFly extends AmbientCreature implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public static final int TICKS_PER_FLAP = Mth.ceil(2.4166098F);

    private static final EntityDataAccessor<Byte> COLOR = SynchedEntityData.defineId(ButterFly.class, EntityDataSerializers.BYTE);

    @Nullable
    private BlockPos nextPos;
    private boolean fromBottle;

    public ButterFly(EntityType<? extends ButterFly> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR, (byte) 0);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType type, @Nullable SpawnGroupData otherGroup, @Nullable CompoundTag tag) {
        SpawnGroupData group = super.finalizeSpawn(level, difficulty, type, otherGroup, tag);
        this.entityData.set(COLOR, (byte)ButterFlyColor.COLORS.next().ordinal());
        return group;
    }

    public void setFromBottle(boolean fromBottle) {
        this.fromBottle = fromBottle;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBottle;
    }

    public boolean isFlapping() {
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    public float getVoicePitch() {
        return super.getVoicePitch() * 0.95F;
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity entity) {}

    protected void pushEntities() {}

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("Color", this.entityData.get(COLOR));
        tag.putBoolean("FromBottle", this.fromBottle);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(COLOR, tag.getByte("Color"));
        this.fromBottle = tag.getBoolean("FromBottle");
    }

    public byte getColor() {
        return this.entityData.get(COLOR);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0);
    }

    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();

        if (this.nextPos != null && (!this.level.isEmptyBlock(this.nextPos) || this.nextPos.getY() <= this.level.getMinBuildHeight())) {
            this.nextPos = null;
        }

        if (this.nextPos == null || this.random.nextInt(30) == 0 || this.nextPos.closerToCenterThan(this.position(), 2.0)) {
            this.nextPos = new BlockPos(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
        }

        double d = (double)this.nextPos.getX() + 0.5 - this.getX();
        double e = (double)this.nextPos.getY() + 0.1 - this.getY();
        double f = (double)this.nextPos.getZ() + 0.5 - this.getZ();
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec32 = vec3.add((Math.signum(d) * 0.5 - vec3.x) * 0.10000000149011612, (Math.signum(e) * 0.699999988079071 - vec3.y) * 0.10000000149011612, (Math.signum(f) * 0.5 - vec3.z) * 0.10000000149011612);
        this.setDeltaMovement(vec32);
        float g = (float)(Mth.atan2(vec32.z, vec32.x) * 57.2957763671875) - 90.0F;
        float h = Mth.wrapDegrees(g - this.getYRot());
        this.zza = 0.5F;
        this.setYRot(this.getYRot() + h);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.GLASS_BOTTLE)) {
            if (!this.level.isClientSide()) {
                stack.shrink(1);
                CompoundTag tag = new CompoundTag();
                this.saveAsPassenger(tag);
                tag.remove("UUID");
                ItemStack bottle = new ItemStack(SproutItems.BUTTER_FLY_JAR.get());
                bottle.getOrCreateTag().put("entity", tag);
                player.getInventory().placeItemBackInInventory(bottle);
                this.discard();
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    public boolean causeFallDamage(float f, float g, @NotNull DamageSource source) {
        return false;
    }

    protected void checkFallDamage(double d, boolean bl, @NotNull BlockState source, @NotNull BlockPos pos) {
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height / 2.0F;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "cycle", 0, event -> {
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.butterfly.fly", true));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static boolean canSpawn(EntityType<ButterFly> ignoredType, ServerLevelAccessor level, MobSpawnType ignoredReason, BlockPos pos, RandomSource random) {
        return random.nextInt(10) == 0 && level.getRawBrightness(pos, 0) > 8 && level.getBlockState(pos.below()).is(BlockTags.LEAVES);
    }
}