package tech.thatgravyboat.sprout.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.common.configs.worldgen.WaterLentils;

public class SpreadingWaterlilyBlock extends WaterlilyBlock implements BonemealableBlock {

    public SpreadingWaterlilyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean bl) {
        return WaterLentils.chance > 0;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return (double)level.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        mutable.set(pos);
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                if (random.nextFloat() < WaterLentils.chance) {
                    mutable.setX(pos.getX() + x);
                    mutable.setZ(pos.getZ() + z);
                    if (level.getBlockState(mutable).isAir() && this.defaultBlockState().canSurvive(level, mutable)) {
                        level.setBlockAndUpdate(mutable, this.defaultBlockState());
                    }
                }
            }
        }
    }
}
