package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.entities.goals.FindPlantGoal;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BounceBugEntity extends TameableEntity implements IAnimatable, Herbivore {

    private BlockPos shroomPos;

    public BounceBugEntity(EntityType<BounceBugEntity> entityType, World world) {
        super(entityType, world);
    }

    private final AnimationFactory factory = new AnimationFactory(this);

    @Override
    protected void initGoals() {
        goalSelector.add(2, new FindPlantGoal<>(this, block -> block instanceof NetherWartBlock || block instanceof MushroomPlantBlock));
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
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
}
