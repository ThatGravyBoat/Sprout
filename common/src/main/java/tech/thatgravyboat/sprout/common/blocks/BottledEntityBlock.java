package tech.thatgravyboat.sprout.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

@SuppressWarnings("deprecation")
public class BottledEntityBlock extends PlaceableGlassBottleBlock implements EntityBlock {

    private final BiFunction<BlockPos, BlockState, BottledEntityBlockEntity> factory;

    public BottledEntityBlock(BlockBehaviour.Properties settings, BiFunction<BlockPos, BlockState, BottledEntityBlockEntity> factory) {
        super(settings);
        this.factory = factory;
    }

    @Override
    public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        if (world.getBlockEntity(pos) instanceof BottledEntityBlockEntity block) {
            var tag = getPossibleTag(itemStack.getTag());
            if (tag != null) {
                block.setEntity(tag);
            }
        }
    }

    private static CompoundTag getPossibleTag(CompoundTag tag) {
        if (tag != null) {
            if (tag.contains("bug", 10)) {
                return tag.getCompound("bug");
            }
            if (tag.contains("entity", 10)) {
                return tag.getCompound("entity");
            }
        }
        return null;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof BottledEntityBlockEntity block) {
                Entity entity = block.getFactory().apply(level);
                block.getEntity().ifPresent(entity::load);
                entity.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                level.addFreshEntity(entity);
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return factory.apply(pos, state);
    }
}
