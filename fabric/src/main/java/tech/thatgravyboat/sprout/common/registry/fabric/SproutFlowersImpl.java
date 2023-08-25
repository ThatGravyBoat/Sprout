package tech.thatgravyboat.sprout.common.registry.fabric;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;

import java.util.function.Supplier;

public class SproutFlowersImpl {
    public static Supplier<Block> registerFlowerPot(String id, Supplier<Block> block) {
        return SproutBlocks.BLOCKS.register(id, () -> new FlowerPotBlock(block.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
    }
}
