package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BounceBugBottle extends PlaceableGlassBottleBlock implements BlockEntityProvider {
    public BounceBugBottle(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BounceBugBottleBlockEntity bugBottleBlock) {
            var nbt = itemStack.getNbt();
            if (nbt != null && itemStack.hasNbt() && nbt.contains("bug", 10)) {
                bugBottleBlock.setEntity(nbt.getCompound("bug"));
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (world.getBlockEntity(pos) instanceof BounceBugBottleBlockEntity bugBottleBlock) {
                BounceBugEntity bug = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world);
                bugBottleBlock.getEntity().ifPresent(bug::readNbt);
                bug.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                world.spawnEntity(bug);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BounceBugBottleBlockEntity(pos, state);
    }
}
