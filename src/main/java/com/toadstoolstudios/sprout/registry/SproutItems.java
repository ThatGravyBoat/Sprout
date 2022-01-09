package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.items.WateringCanItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.toadstoolstudios.sprout.Sprout.MOD_ID;

public class SproutItems {
    public static final Item ELEPHANT_SPAWN_EGG = new SpawnEggItem(SproutEntities.ELEPHANT_ENTITY_TYPE, 0x8198a0,0x52556c, genericSettings());
    public static final Item PEANUT = new Item(genericSettings());
    public static final Item WATERING_CAN = new WateringCanItem(genericSettings().maxCount(1));
    private static FabricItemSettings genericSettings() {
        return new FabricItemSettings().group(Sprout.SPROUT_TAB);
    }

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "elephant_spawn_egg"), ELEPHANT_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "peanut"), PEANUT);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "watering_can"), WATERING_CAN);
    }
}
