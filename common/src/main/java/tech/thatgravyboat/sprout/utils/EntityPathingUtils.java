package tech.thatgravyboat.sprout.utils;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class EntityPathingUtils {
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
}
