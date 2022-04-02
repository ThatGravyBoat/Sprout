package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GlowflyJarBlockEntity extends BlockEntity {
    @Nullable
    private Entity renderEntity;

    public GlowflyJarBlockEntity(BlockPos pos, BlockState state) {
        super(SproutBlocks.GLOWFLY_JAR_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, pos, state);
    }

    public Entity getOrCreateEntity(World world) {
        if(renderEntity == null) {
            renderEntity = new GlowflyEntity(SproutEntities.GLOWFLY_ENTITY_TYPE.get(), world);
        }
        return renderEntity;
    }
}
