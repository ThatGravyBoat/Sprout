package tech.thatgravyboat.sprout.common.flowers;

import net.minecraft.world.level.block.Block;

public record FlowerBreed(Block flower1, Block flower2) {
    public static FlowerBreed of(Block flower1, Block flower2) {
        return flower1.hashCode() > flower2.hashCode() ? new FlowerBreed(flower1, flower2) : new FlowerBreed(flower2, flower1);
    }
}