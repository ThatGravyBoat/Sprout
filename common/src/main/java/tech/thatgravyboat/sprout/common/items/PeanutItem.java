package tech.thatgravyboat.sprout.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PeanutItem extends ItemNameBlockItem {

    public PeanutItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    protected boolean canPlace(@NotNull BlockPlaceContext context, @NotNull BlockState state) {
        return super.canPlace(context, state) && canPlaceOn(context.getLevel(), context.getClickedPos());
    }

    public boolean canPlaceOn(BlockGetter world, BlockPos pos) {
        return world.getBlockState(pos.below()).is(Blocks.FARMLAND);
    }
}
