package com.toadstoolstudios.sprout.blocks;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GlowflyJar extends GlassJar implements BlockEntityProvider {
    public GlowflyJar(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GlowflyJarBlockEntity(pos, state);
    }
}
