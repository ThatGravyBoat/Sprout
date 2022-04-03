package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.items.WateringCanItem;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutItems {
    public static final Supplier<Item> ELEPHANT_SPAWN_EGG = registerItem("elephant_spawn_egg", () -> new SpawnEggItem(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), 0x8198a0,0x52556c, genericSettings()));
    public static final Supplier<Item> BOUNCE_BUG_SPAWN_EGG = registerItem("bouncebug_spawn_egg", () -> new SpawnEggItem(SproutEntities.BOUNCE_BUG_ENTITY.get(), 0xFCE784,0x52556c, genericSettings()));
    public static final Supplier<Item> PEANUT = registerItem("peanut", () -> new AliasedBlockItem(SproutBlocks.PEANUT_PLANT_BLOCK.get(), genericSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
    public static final Supplier<Item> WATERING_CAN = registerItem("watering_can", () -> new WateringCanItem(genericSettings().maxCount(1)));
    public static final Supplier<Item> GLOWFLY_JAR_ITEM = registerItem("glowfly_jar", () -> new BlockItem(SproutBlocks.BOUNCE_BUG_BOTTLE.get(), genericSettings()));
    public static final Supplier<Item> BASKET_BLOCK_ITEM = registerItem("basket_block", () -> new BlockItem(SproutBlocks.BASKET_BLOCK.get(), genericSettings()));

    private static Item.Settings genericSettings() {
        return new Item.Settings().group(Sprout.SPROUT_TAB);
    }

    @NotNull
    @ExpectPlatform
    public static Supplier<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        throw new AssertionError();
    }

    public static void registerItems() {
        //initialize class
    }
}
