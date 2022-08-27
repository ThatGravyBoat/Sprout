package tech.thatgravyboat.sprout.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DuneGrass extends BasicGrassBlock {

    public DuneGrass(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return floor.is(BlockTags.SAND) || floor.is(BlockTags.DIRT);
    }
}
