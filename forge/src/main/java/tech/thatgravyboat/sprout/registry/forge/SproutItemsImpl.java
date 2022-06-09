package tech.thatgravyboat.sprout.registry.forge;

import tech.thatgravyboat.sprout.Sprout;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
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

    public static Supplier<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int primaryColor, int secondaryColor, Item.Properties settings) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(entityTypeSupplier, primaryColor, secondaryColor, settings));
    }
}
