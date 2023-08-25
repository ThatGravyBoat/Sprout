package tech.thatgravyboat.sprout.mixin;

import net.minecraft.world.level.block.FungusBlock;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({MushroomBlock.class, FungusBlock.class})
public abstract class ShelfMushroomFungusMixin extends Block {

    public ShelfMushroomFungusMixin(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        BlockState state = super.getStateForPlacement(ctx);
        if (ctx.getClickedFace() != Direction.UP) {
            if (this.asBlock() == Blocks.RED_MUSHROOM) {
                state = SproutBlocks.RED_SHELF_FUNGI.get().getStateForPlacement(ctx);
            }
            if (this.asBlock() == Blocks.BROWN_MUSHROOM) {
                state = SproutBlocks.BROWN_SHELF_FUNGI.get().getStateForPlacement(ctx);
            }
            if (this.asBlock() == Blocks.CRIMSON_FUNGUS) {
                state = SproutBlocks.CRIMSON_SHELF_FUNGI.get().getStateForPlacement(ctx);
            }
            if (this.asBlock() == Blocks.WARPED_FUNGUS) {
                state = SproutBlocks.WARPED_SHELF_FUNGI.get().getStateForPlacement(ctx);
            }
        }
        return state;
    }
}
