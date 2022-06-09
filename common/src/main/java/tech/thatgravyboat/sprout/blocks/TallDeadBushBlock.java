package tech.thatgravyboat.sprout.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TallDeadBushBlock extends DoublePlantBlock {
    public TallDeadBushBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return floor.is(BlockTags.SAND) || floor.is(BlockTags.TERRACOTTA) || floor.is(BlockTags.DIRT);
    }
}
