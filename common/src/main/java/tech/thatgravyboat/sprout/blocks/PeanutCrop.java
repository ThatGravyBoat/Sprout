package tech.thatgravyboat.sprout.blocks;

import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.registry.SproutItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PeanutCrop extends CarrotBlock {

    public PeanutCrop(Properties settings) {
        super(settings);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SproutItems.PEANUT.get();
    }

    @Override
    public boolean mayPlaceOn(BlockState floor, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return floor.is(Blocks.FARMLAND) || floor.is(BlockTags.DIRT);
    }
}
