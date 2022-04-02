package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutBlocksImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Sprout.MODID);
    @NotNull
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }
}
