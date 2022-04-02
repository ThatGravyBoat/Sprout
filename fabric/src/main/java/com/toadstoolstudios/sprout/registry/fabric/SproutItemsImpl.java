package com.toadstoolstudios.sprout.registry.fabric;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutItemsImpl {
    @NotNull
    public static Supplier<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        var register = Registry.register(Registry.ITEM, new Identifier(MODID, name), itemSupplier.get());
        return () -> register;
    }
}
