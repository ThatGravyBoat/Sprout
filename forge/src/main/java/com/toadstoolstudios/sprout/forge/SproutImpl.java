package com.toadstoolstudios.sprout.forge;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class SproutImpl {
    public static ItemGroup registerItemGroup(Identifier id, Supplier<ItemStack> icon) {
        return new ItemGroup(id.getNamespace() + "." + id.getPath()) {
            @Override
            public ItemStack createIcon() {
                return icon.get();
            }
        };
    }
}
