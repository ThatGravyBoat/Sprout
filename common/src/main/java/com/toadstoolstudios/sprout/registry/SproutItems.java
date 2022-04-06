package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.items.BounceBugBottleItem;
import com.toadstoolstudios.sprout.items.DrinkableFoodItem;
import com.toadstoolstudios.sprout.items.WateringCanItem;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutItems {
    //Spawn Eggs
    public static final Supplier<Item> ELEPHANT_SPAWN_EGG = registerSpawnEgg("elephant_spawn_egg", SproutEntities.ELEPHANT_ENTITY_TYPE, 0x8198a0,0x52556c, genericSettings());
    public static final Supplier<Item> BOUNCE_BUG_SPAWN_EGG = registerSpawnEgg("bouncebug_spawn_egg", SproutEntities.BOUNCE_BUG_ENTITY, 0x00b0a4, 0xde6407, genericSettings());
    //Block Items
    public static final Supplier<Item> BOUNCE_BUG_JAR = registerItem("bounce_bug_jar", () -> new BounceBugBottleItem(genericSettings()));

    //Food Items
    public static final Supplier<Item> PEANUT = registerItem("peanut", () -> new AliasedBlockItem(SproutBlocks.PEANUT_PLANT_BLOCK.get(), genericSettings().food(FoodComponents.SWEET_BERRIES)));
    public static final Supplier<Item> PEANUT_BUTTER = registerItem("peanut_butter", DrinkableFoodItem::new);
    public static final Supplier<Item> PBJ = registerItem("pbj", () -> new Item(genericSettings().food(FoodComponents.COOKED_PORKCHOP)));
    public static final Supplier<Item> PEANUT_BUTTER_COOKIE = registerItem("peanut_butter_cookie", () -> new Item(genericSettings().food(FoodComponents.COOKIE)));
    public static final Supplier<Item> GLOW_BERRY_JAM = registerItem("glow_berry_jam", DrinkableFoodItem::new);
    public static final Supplier<Item> GLOW_BERRY_PIE = registerItem("glow_berry_pie", () -> new Item(genericSettings().food(FoodComponents.PUMPKIN_PIE)));
    public static final Supplier<Item> SWEET_BERRY_JAM = registerItem("sweet_berry_jam", DrinkableFoodItem::new);
    public static final Supplier<Item> SWEET_BERRY_PIE = registerItem("sweet_berry_pie", () -> new Item(genericSettings().food(FoodComponents.PUMPKIN_PIE)));
    public static final Supplier<Item> APPLE_PIE = registerItem("apple_pie", () -> new Item(genericSettings().food(FoodComponents.PUMPKIN_PIE)));
    public static final Supplier<Item> BUTTERSCOTCH = registerItem("butterscotch", () -> new Item(genericSettings().food(FoodComponents.SWEET_BERRIES)));

    private static Item.Settings genericSettings() {
        return new Item.Settings().group(Sprout.SPROUT_TAB);
    }

    @ExpectPlatform
    public static Supplier<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends MobEntity>> entityTypeSupplier, int primaryColor, int secondaryColor, Item.Settings settings) {
        throw new AssertionError();
    }

    public static void registerItems() {
        //initialize class
    }
}
