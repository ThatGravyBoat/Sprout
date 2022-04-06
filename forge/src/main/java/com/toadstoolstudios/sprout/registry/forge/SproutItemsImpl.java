package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutItemsImpl {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Sprout.MODID);
    @NotNull
    public static Supplier<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        return ITEMS.register(name, itemSupplier);
    }

    public static Supplier<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends MobEntity>> entityTypeSupplier, int primaryColor, int secondaryColor, Item.Settings settings) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(entityTypeSupplier, primaryColor, secondaryColor, settings));
    }
}
