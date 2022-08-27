package tech.thatgravyboat.sprout.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.Sprout;

import java.util.function.Supplier;

public class SproutItemsImpl {
    @NotNull
    public static Supplier<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        var register = Registry.register(Registry.ITEM, new ResourceLocation(Sprout.MODID, name), itemSupplier.get());
        return () -> register;
    }

    public static Supplier<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int primaryColor, int secondaryColor, Item.Properties settings) {
       var registry = Registry.register(Registry.ITEM, new ResourceLocation(Sprout.MODID, name), new SpawnEggItem(entityTypeSupplier.get(), primaryColor,secondaryColor, settings));
       return () -> registry;
    }
}
