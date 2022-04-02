package com.toadstoolstudios.sprout.entities;

import net.minecraft.util.math.BlockPos;
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
