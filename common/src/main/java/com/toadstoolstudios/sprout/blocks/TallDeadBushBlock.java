package com.toadstoolstudios.sprout.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TallDeadBushBlock extends TallPlantBlock {
    public TallDeadBushBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.RED_SAND) || floor.isOf(Blocks.TERRACOTTA) || floor.isOf(Blocks.WHITE_TERRACOTTA) || floor.isOf(Blocks.ORANGE_TERRACOTTA) || floor.isOf(Blocks.MAGENTA_TERRACOTTA) || floor.isOf(Blocks.LIGHT_BLUE_TERRACOTTA) || floor.isOf(Blocks.YELLOW_TERRACOTTA) || floor.isOf(Blocks.LIME_TERRACOTTA) || floor.isOf(Blocks.PINK_TERRACOTTA) || floor.isOf(Blocks.GRAY_TERRACOTTA) || floor.isOf(Blocks.LIGHT_GRAY_TERRACOTTA) || floor.isOf(Blocks.CYAN_TERRACOTTA) || floor.isOf(Blocks.PURPLE_TERRACOTTA) || floor.isOf(Blocks.BLUE_TERRACOTTA) || floor.isOf(Blocks.BROWN_TERRACOTTA) || floor.isOf(Blocks.GREEN_TERRACOTTA) || floor.isOf(Blocks.RED_TERRACOTTA) || floor.isOf(Blocks.BLACK_TERRACOTTA) || floor.isIn(BlockTags.DIRT);
    }
}
