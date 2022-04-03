package com.toadstoolstudios.sprout.blocks;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BounceBugBottle extends PlaceableGlassBottleBlock implements BlockEntityProvider {
    public BounceBugBottle(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BounceBugBottleBlockEntity(pos, state);
    }
}
