package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BounceBugBottleBlockEntity extends BlockEntity {
    @Nullable
    private Entity renderEntity;

    public BounceBugBottleBlockEntity(BlockPos pos, BlockState state) {
        super(SproutBlocks.BOUNCE_BUG_JAR_BLOCK_ENTITY.get(), pos, state);
    }

    public Entity getOrCreateEntity(World world) {
        if(renderEntity == null) {
            renderEntity = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world, true);
        }
        return renderEntity;
    }
}
