package tech.thatgravyboat.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import tech.thatgravyboat.sprout.items.*;

import java.util.function.Supplier;

public class SproutItems {
    //Spawn Eggs
    public static final Supplier<Item> ELEPHANT_SPAWN_EGG = registerSpawnEgg("elephant_spawn_egg", SproutEntities.ELEPHANT_ENTITY_TYPE, 0x8198a0,0x52556c, groupSettings(CreativeModeTab.TAB_MISC));
    public static final Supplier<Item> BOUNCE_BUG_SPAWN_EGG = registerSpawnEgg("bouncebug_spawn_egg", SproutEntities.BOUNCE_BUG_ENTITY, 0x00b0a4, 0xC53439, groupSettings(CreativeModeTab.TAB_MISC));
    //Block Items
    public static final Supplier<Item> BOUNCE_BUG_JAR = registerItem("bounce_bug_jar", () -> new BounceBugBottleItem(new Item.Properties()));
    public static final Supplier<Item> CATTIAL = registerItem("cattail", () -> new DoubleHighBlockItem(SproutBlocks.CATTIAL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> DUNE_GRASS = registerItem("dune_grass", () -> new BlockItem(SproutBlocks.DUNE_GRASS.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> SPROUTS = registerItem("sprouts", () -> new BlockItem(SproutBlocks.SPROUTS.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> TALL_DEAD_BUSH = registerItem("tall_dead_bush", () -> new DoubleHighBlockItem(SproutBlocks.TALL_DEAD_BUSH.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> WATER_LENTIL = registerItem("water_lentil", () -> new PlaceOnWaterBlockItem(SproutBlocks.WATER_LENTIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    //Food Items
    public static final Supplier<Item> PEANUT = registerItem("peanut", () -> new PeanutItem(SproutBlocks.PEANUT_PLANT_BLOCK.get(), groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.SWEET_BERRIES)));
    public static final Supplier<Item> PEANUT_BUTTER = registerItem("peanut_butter", DrinkableFoodItem::new);
    public static final Supplier<Item> PBJ = registerItem("pbj", () -> new Item(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.COOKED_PORKCHOP)));
    public static final Supplier<Item> PEANUT_BUTTER_COOKIE = registerItem("peanut_butter_cookie", () -> new Item(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.COOKIE)));
    public static final Supplier<Item> GLOW_BERRY_JAM = registerItem("glow_berry_jam", DrinkableFoodItem::new);
    public static final Supplier<Item> GLOW_BERRY_PIE = registerItem("glow_berry_pie", () -> new Item(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.PUMPKIN_PIE)));
    public static final Supplier<Item> SWEET_BERRY_JAM = registerItem("sweet_berry_jam", DrinkableFoodItem::new);
    public static final Supplier<Item> SWEET_BERRY_PIE = registerItem("sweet_berry_pie", () -> new Item(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.PUMPKIN_PIE)));
    public static final Supplier<Item> APPLE_PIE = registerItem("apple_pie", () -> new Item(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.PUMPKIN_PIE)));
    public static final Supplier<Item> BUTTERSCOTCH = registerItem("butterscotch", () -> new Item(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.SWEET_BERRIES)));
    public static final Supplier<Item> CANDY_APPLE = registerItem("candy_apple", () -> new CandyApple(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.BAKED_POTATO)));
    public static final FoodProperties GOLDEN_APPLE_CANDY = (new FoodProperties.Builder()).nutrition(8).saturationMod(2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEat().build();
    public static final Supplier<Item> GOLDEN_CANDY_APPLE = registerItem("golden_candy_apple", () -> new CandyApple(groupSettings(CreativeModeTab.TAB_FOOD).food(GOLDEN_APPLE_CANDY)));
    public static final Supplier<Item> WATER_SAUSAGE = registerItem("water_sausage", () -> new WaterSausage(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.COD)));

    public static void onComplete() {
        Object2FloatMap<ItemLike> compostMap = ComposterBlock.COMPOSTABLES;
        compostMap.put(PEANUT.get(), 0.3f);
        compostMap.put(BUTTERSCOTCH.get(), 0.65f);
        compostMap.put(PBJ.get(), 0.85f);
        compostMap.put(PEANUT_BUTTER_COOKIE.get(), 0.85f);
        compostMap.put(CANDY_APPLE.get(), 0.85f);
        compostMap.put(GLOW_BERRY_PIE.get(), 1f);
        compostMap.put(SWEET_BERRY_PIE.get(), 1f);
        compostMap.put(APPLE_PIE.get(), 1f);
        compostMap.put(GOLDEN_CANDY_APPLE.get(), 1f);
        compostMap.put(WATER_SAUSAGE.get(), 1f);
    }

    private static Item.Properties groupSettings(CreativeModeTab group) {
        return new Item.Properties().tab(group);
    }

    @ExpectPlatform
    public static Supplier<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int primaryColor, int secondaryColor, Item.Properties settings) {
        throw new AssertionError();
    }

    public static void registerItems() {
        //initialize class
        //APPLE
    }
}
