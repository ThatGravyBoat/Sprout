package tech.thatgravyboat.sprout.common.blocks;

import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.common.registry.SproutEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BounceBugBottle extends PlaceableGlassBottleBlock implements EntityBlock {
    public BounceBugBottle(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        if (world.getBlockEntity(pos) instanceof BounceBugBottleBlockEntity bugBottleBlock) {
            var nbt = itemStack.getTag();
            if (nbt != null && itemStack.hasTag() && nbt.contains("bug", 10)) {
                bugBottleBlock.setEntity(nbt.getCompound("bug"));
            }
        }
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (world.getBlockEntity(pos) instanceof BounceBugBottleBlockEntity bugBottleBlock) {
                BounceBugEntity bug = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world);
                bugBottleBlock.getEntity().ifPresent(bug::load);
                bug.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                world.addFreshEntity(bug);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new BounceBugBottleBlockEntity(pos, state);
    }
}
