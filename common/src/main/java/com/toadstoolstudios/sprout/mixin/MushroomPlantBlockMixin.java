package com.toadstoolstudios.sprout.mixin;

import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MushroomPlantBlock.class)
public abstract class MushroomPlantBlockMixin extends Block {

    public MushroomPlantBlockMixin(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (ctx.getSide() != Direction.UP) {
            if (this.asBlock() == Blocks.RED_MUSHROOM) {
                state = SproutBlocks.RED_SHELF_FUNGI.get().getPlacementState(ctx);
            }
            if (this.asBlock() == Blocks.BROWN_MUSHROOM) {
                state = SproutBlocks.BROWN_SHELF_FUNGI.get().getPlacementState(ctx);
            }
        }
        return state;
    }
}
