package com.toadstoolstudios.sprout.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class PeanutItem extends AliasedBlockItem {

    public PeanutItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return super.canPlace(context, state) && canPlaceOn(context.getWorld(), context.getBlockPos());
    }

    public boolean canPlaceOn(WorldView world, BlockPos pos) {
        BlockState floor = world.getBlockState(pos.down());
        return floor.isOf(Blocks.FARMLAND);
    }
}
