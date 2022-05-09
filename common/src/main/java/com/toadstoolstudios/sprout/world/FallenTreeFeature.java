package com.toadstoolstudios.sprout.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FallenTreeFeature extends Feature<FallenTreeFeature.FallenTreeConfig> {

    private static final int[] DIR = {-1, 1};
    private static final int[] DIR_REVERSED = {1, -1};

    public FallenTreeFeature(Codec<FallenTreeConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<FallenTreeConfig> context) {
        Random random = context.getRandom();
        BlockPos pos = context.getOrigin();
        StructureWorldAccess level = context.getWorld();

        Direction.Axis axis = Direction.Type.HORIZONTAL.random(random).getAxis();
        List<BlockPos> validPosInLine = new ArrayList<>(5);
        int count = 0;
        for (int i = 0; i < 5; i++) {
            BlockPos offset = pos.offset(axis, i);
            if (!level.getBlockState(offset).getMaterial().isReplaceable() || level.getBlockState(offset.down()).getMaterial().isReplaceable()) {
                count = 0;
            } else {
                validPosInLine.add(offset);
                count++;
            }
        }
        if (count < 3) return false;
        BlockState state = context.getConfig().log().getDefaultState();
        if (state.contains(Properties.AXIS)) {
            state = state.with(Properties.AXIS, axis);
        }

        Direction.Axis oppositeAxis = opposite(axis);

        for (BlockPos blockPos : validPosInLine) {
            if (level.setBlockState(blockPos, state, 3)) place(random, blockPos, oppositeAxis, level, context.getConfig());
        }

        return true;
    }

    private static void place(Random random, BlockPos pos, Direction.Axis axis, StructureWorldAccess level, FallenTreeConfig config) {
        float red = config.redMushroom();
        float brown = config.brownMushroom();
        if (random.nextFloat() < config.moss() && level.isAir(pos.up())) {
            level.setBlockState(pos.up(), Blocks.MOSS_CARPET.getDefaultState(), 3);
            red /= 2f;
            brown /= 2f;
        }
        boolean placeRed = true;
        if (random.nextFloat() < brown) {
            if (place(random, pos, axis, level, SproutBlocks.BROWN_SHELF_FUNGI.get().getDefaultState())) {
                placeRed = false;
            }
        }
        if (placeRed && random.nextFloat() < red) {
            place(random, pos, axis, level, SproutBlocks.RED_SHELF_FUNGI.get().getDefaultState());
        }
    }

    private static Direction.Axis opposite(Direction.Axis axis) {
        return axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
    }

    private static boolean place(Random random, BlockPos pos, Direction.Axis axis, StructureWorldAccess level, BlockState state) {
        for (int i : (random.nextBoolean() ? DIR_REVERSED : DIR)) {
            if (state.contains(HorizontalFacingBlock.FACING)) {
                state = state.with(HorizontalFacingBlock.FACING, Direction.from(axis, i > 0 ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE));
            }
            if (level.getBlockState(pos.offset(axis, i)).getMaterial().isReplaceable() && level.setBlockState(pos.offset(axis, i), state, 3)) {
                return true;
            }
        }
        return false;
    }

    public static record FallenTreeConfig(Block log, float moss, float redMushroom, float brownMushroom) implements FeatureConfig {
        public static final Codec<FallenTreeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Registry.BLOCK.getCodec().fieldOf("log").forGetter(FallenTreeConfig::log),
                Codec.floatRange(0f, 1f).fieldOf("moss").orElse(0f).forGetter(FallenTreeConfig::moss),
                Codec.floatRange(0f, 1f).fieldOf("redMushroom").orElse(0f).forGetter(FallenTreeConfig::redMushroom),
                Codec.floatRange(0f, 1f).fieldOf("brownMushroom").orElse(0f).forGetter(FallenTreeConfig::brownMushroom)
        ).apply(instance, FallenTreeConfig::new));
    }
}
