package com.toadstoolstudios.sprout.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class SproutImpl {
    public static ItemGroup registerItemGroup(Identifier id, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(id, icon);
    }
}
