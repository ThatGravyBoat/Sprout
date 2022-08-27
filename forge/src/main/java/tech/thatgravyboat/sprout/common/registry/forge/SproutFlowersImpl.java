package tech.thatgravyboat.sprout.common.registry.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.blocks.FlowerBoxBlock;

import java.util.function.Supplier;

public class SproutFlowersImpl {
    public static Supplier<Block> registerFlowerPot(String id, Supplier<Block> block) {
        var register = SproutBlocksImpl.BLOCKS.register(id, () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, block, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(Sprout.MODID, id), register);
        return register::get;
    }
}
