package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
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

    public static Supplier<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends MobEntity>> entityTypeSupplier, int primaryColor, int secondaryColor, Item.Settings settings) {
       var registry = Registry.register(Registry.ITEM, new Identifier(MODID, name), new SpawnEggItem(entityTypeSupplier.get(), primaryColor,secondaryColor, settings));
       return () -> registry;
    }
}
