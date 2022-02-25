package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.items.WateringCanItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutItems {
    public static final Item ELEPHANT_SPAWN_EGG = new SpawnEggItem(SproutEntities.ELEPHANT_ENTITY_TYPE, 0x8198a0,0x52556c, genericSettings());
    public static final Item GLOWFLY_SPAWN_EGG = new SpawnEggItem(SproutEntities.GLOWFLY_ENTITY_TYPE, 0xFCE784,0x52556c, genericSettings());
    public static final Item PEANUT = new AliasedBlockItem(SproutBlocks.PEANUT_PLANT_BLOCK, genericSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build()));
    public static final Item WATERING_CAN = new WateringCanItem(genericSettings().maxCount(1));
    public static final Item GLASS_JAR_ITEM = new BlockItem(SproutBlocks.GLASS_JAR, genericSettings());
    public static final Item GLOWFLY_JAR_ITEM = new BlockItem(SproutBlocks.GLOWFLY_JAR, genericSettings());

    private static FabricItemSettings genericSettings() {
        return new FabricItemSettings().group(Sprout.SPROUT_TAB);
    }

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "elephant_spawn_egg"), ELEPHANT_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MODID, "glowfly_spawn_egg"), GLOWFLY_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MODID, "peanut"), PEANUT);
        Registry.register(Registry.ITEM, new Identifier(MODID, "watering_can"), WATERING_CAN);
        Registry.register(Registry.ITEM, new Identifier(MODID, "glass_jar"), GLASS_JAR_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MODID, "glowfly_jar"), GLOWFLY_JAR_ITEM);
    }
}
