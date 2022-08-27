package tech.thatgravyboat.sprout.common.entities;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface Herbivore {
    boolean isNotBusy();

    default boolean specialPredicate() {
        return true;
    }

    void setTargetPlant(BlockPos pos);

    @Nullable
    BlockPos getTargetPlant();
}
