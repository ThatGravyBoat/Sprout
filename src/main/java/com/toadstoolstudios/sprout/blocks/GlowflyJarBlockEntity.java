package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class GlowflyJarBlockEntity extends BlockEntity {
    public GlowflyJarBlockEntity(BlockPos pos, BlockState state) {
        super(SproutBlocks.GLOWFLY_JAR_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, pos, state);
    }
}
