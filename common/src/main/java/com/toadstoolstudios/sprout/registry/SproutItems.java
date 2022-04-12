package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.items.BounceBugBottleItem;
import com.toadstoolstudios.sprout.items.DrinkableFoodItem;
import com.toadstoolstudios.sprout.items.PeanutItem;
import dev.architectury.injectables.annotations.ExpectPlatform;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;

import java.util.function.Supplier;

public class SproutItems {
    //Spawn Eggs
    public static final Supplier<Item> ELEPHANT_SPAWN_EGG = registerSpawnEgg("elephant_spawn_egg", SproutEntities.ELEPHANT_ENTITY_TYPE, 0x8198a0,0x52556c, groupSettings(ItemGroup.MISC));
    public static final Supplier<Item> BOUNCE_BUG_SPAWN_EGG = registerSpawnEgg("bouncebug_spawn_egg", SproutEntities.BOUNCE_BUG_ENTITY, 0x00b0a4, 0xC53439, groupSettings(ItemGroup.MISC));
    //Block Items
    public static final Supplier<Item> BOUNCE_BUG_JAR = registerItem("bounce_bug_jar", () -> new BounceBugBottleItem(new Item.Settings()));

    //Food Items
    public static final Supplier<Item> PEANUT = registerItem("peanut", () -> new PeanutItem(SproutBlocks.PEANUT_PLANT_BLOCK.get(), groupSettings(ItemGroup.FOOD).food(FoodComponents.SWEET_BERRIES)));
    public static final Supplier<Item> PEANUT_BUTTER = registerItem("peanut_butter", DrinkableFoodItem::new);
    public static final Supplier<Item> PBJ = registerItem("pbj", () -> new Item(groupSettings(ItemGroup.FOOD).food(FoodComponents.COOKED_PORKCHOP)));
    public static final Supplier<Item> PEANUT_BUTTER_COOKIE = registerItem("peanut_butter_cookie", () -> new Item(groupSettings(ItemGroup.FOOD).food(FoodComponents.COOKIE)));
    public static final Supplier<Item> GLOW_BERRY_JAM = registerItem("glow_berry_jam", DrinkableFoodItem::new);
    public static final Supplier<Item> GLOW_BERRY_PIE = registerItem("glow_berry_pie", () -> new Item(groupSettings(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
    public static final Supplier<Item> SWEET_BERRY_JAM = registerItem("sweet_berry_jam", DrinkableFoodItem::new);
    public static final Supplier<Item> SWEET_BERRY_PIE = registerItem("sweet_berry_pie", () -> new Item(groupSettings(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
    public static final Supplier<Item> APPLE_PIE = registerItem("apple_pie", () -> new Item(groupSettings(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
    public static final Supplier<Item> BUTTERSCOTCH = registerItem("butterscotch", () -> new Item(groupSettings(ItemGroup.FOOD).food(FoodComponents.SWEET_BERRIES)));

    public static void onComplete() {
        Object2FloatMap<ItemConvertible> compostMap = ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE;
        compostMap.put(PEANUT.get(), 0.3f);
        compostMap.put(BUTTERSCOTCH.get(), 0.65f);
        compostMap.put(PBJ.get(), 0.85f);
        compostMap.put(PEANUT_BUTTER_COOKIE.get(), 0.85f);
        compostMap.put(GLOW_BERRY_PIE.get(), 1f);
        compostMap.put(SWEET_BERRY_PIE.get(), 1f);
        compostMap.put(APPLE_PIE.get(), 1f);
    }

    private static Item.Settings groupSettings(ItemGroup group) {
        return new Item.Settings().group(group);
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
