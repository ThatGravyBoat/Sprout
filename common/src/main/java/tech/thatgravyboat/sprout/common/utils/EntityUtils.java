package tech.thatgravyboat.sprout.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import tech.thatgravyboat.sprout.Sprout;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {

    private static final TagKey<Block> INVALID_BONEMEAL_TARGETS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Sprout.MODID, "invalid_bonemeal_targets"));

    public static List<BlockPos> getPositionalOffsets(int range) {
        ArrayList<BlockPos> offsets = new ArrayList<>();

        for (int i = 0; (double) i <= range; i = i > 0 ? -i : 1 - i) {
            for (int j = 0; (double) j < range; ++j) {
                for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        offsets.add(new BlockPos(k, i - 1, l));
                    }
                }
            }
        }
        return offsets;
    }

    public static boolean validBoneMealTarget(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof BonemealableBlock block && !state.is(INVALID_BONEMEAL_TARGETS)) {
            if (block.isValidBonemealTarget(level, pos, state, level.isClientSide)) {
                if (level instanceof ServerLevel) {
                    return block.isBonemealSuccess(level, level.random, pos, state);
                }
            }
        }
        return false;
    }
}
