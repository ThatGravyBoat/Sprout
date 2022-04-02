package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutBlocksImpl {
    @NotNull
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        var register = Registry.register(Registry.BLOCK, new Identifier(Sprout.MODID, name), blockSupplier.get());
        return () -> register;
    }
}
