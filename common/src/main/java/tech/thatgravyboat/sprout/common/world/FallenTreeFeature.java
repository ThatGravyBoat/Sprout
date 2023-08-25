package tech.thatgravyboat.sprout.common.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FallenTreeFeature extends Feature<FallenTreeFeature.FallenTreeConfig> {

    private static final int[] DIR = {-1, 1};
    private static final int[] DIR_REVERSED = {1, -1};

    public FallenTreeFeature(Codec<FallenTreeConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenTreeConfig> context) {
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();

        Direction.Axis axis = Direction.Plane.HORIZONTAL.getRandomDirection(random).getAxis();
        List<BlockPos> validPosInLine = new ArrayList<>(5);
        int count = 0;
        for (int i = 0; i < 5; i++) {
            BlockPos offset = pos.relative(axis, i);
            if (!level.getBlockState(offset).getMaterial().isReplaceable() || level.getBlockState(offset.below()).getMaterial().isReplaceable()) {
                count = 0;
            } else {
                validPosInLine.add(offset);
                count++;
            }
        }
        if (count < 3) return false;
        BlockState state = context.config().log().defaultBlockState();
        if (state.hasProperty(BlockStateProperties.AXIS)) {
            state = state.setValue(BlockStateProperties.AXIS, axis);
        }

        Direction.Axis oppositeAxis = opposite(axis);

        for (BlockPos blockPos : validPosInLine) {
            if (level.setBlock(blockPos, state, 3)) place(random, blockPos, oppositeAxis, level, context.config());
        }

        return true;
    }

    private static void place(RandomSource random, BlockPos pos, Direction.Axis axis, WorldGenLevel level, FallenTreeConfig config) {
        boolean placedMoss = false;
        if (random.nextFloat() < config.moss() && level.isEmptyBlock(pos.above())) {
            level.setBlock(pos.above(), Blocks.MOSS_CARPET.defaultBlockState(), 3);
            placedMoss = true;
        }

        for (FungiConfig fungus : config.fungus) {
            float chance = fungus.chance();
            if (placedMoss) chance /= 2;
            if (random.nextFloat() < chance) {
                place(random, pos, axis, level, fungus.fungi().defaultBlockState());
                break;
            }
        }
    }

    private static Direction.Axis opposite(Direction.Axis axis) {
        return axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
    }

    private static void place(RandomSource random, BlockPos pos, Direction.Axis axis, WorldGenLevel level, BlockState state) {
        for (int i : (random.nextBoolean() ? DIR_REVERSED : DIR)) {
            if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
                state = state.setValue(HorizontalDirectionalBlock.FACING, Direction.fromAxisAndDirection(axis, i > 0 ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE));
            }
            if (level.getBlockState(pos.relative(axis, i)).getMaterial().isReplaceable() && level.setBlock(pos.relative(axis, i), state, 3)) {
                return;
            }
        }
    }

    public record FallenTreeConfig(Block log, float moss, List<FungiConfig> fungus) implements FeatureConfiguration {
        public static final Codec<FallenTreeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Registry.BLOCK.byNameCodec().fieldOf("log").forGetter(FallenTreeConfig::log),
                Codec.floatRange(0f, 1f).fieldOf("moss").orElse(0f).forGetter(FallenTreeConfig::moss),
                FungiConfig.CODEC.listOf().fieldOf("fungus").orElse(new ArrayList<>()).forGetter(FallenTreeConfig::fungus)
        ).apply(instance, FallenTreeConfig::new));
    }

    public record FungiConfig(Block fungi, float chance) implements FeatureConfiguration {
        public static final Codec<FungiConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Registry.BLOCK.byNameCodec().fieldOf("fungi").forGetter(FungiConfig::fungi),
                Codec.floatRange(0f, 1f).fieldOf("chance").orElse(0f).forGetter(FungiConfig::chance)
        ).apply(instance, FungiConfig::new));
    }
}
