package tech.thatgravyboat.sprout.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.core.Registry;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.items.BounceBugBottleItem;
import tech.thatgravyboat.sprout.common.items.ButterFlyBottleItem;
import tech.thatgravyboat.sprout.common.items.PeanutItem;
import tech.thatgravyboat.sprout.common.items.WaterSausage;

import java.util.function.Supplier;

import static tech.thatgravyboat.sprout.common.utils.PlatformRegistryHelper.createSpawnEgg;

public class SproutItems {

    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(Registry.ITEM, Sprout.MODID);

    //Spawn Eggs
    public static final Supplier<Item> ELEPHANT_SPAWN_EGG = ITEMS.register("elephant_spawn_egg", () -> createSpawnEgg(SproutEntities.ELEPHANT_ENTITY_TYPE, 0x8198a0, 0x52556c, groupSettings(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> BOUNCE_BUG_SPAWN_EGG = ITEMS.register("bouncebug_spawn_egg", () -> createSpawnEgg(SproutEntities.BOUNCE_BUG_ENTITY, 0x00b0a4, 0xC53439, groupSettings(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> BUTTERFLY_SPAWN_EGG = ITEMS.register("butterfly_spawn_egg", () -> createSpawnEgg(SproutEntities.BUTTERFLY, 0xEA9026, 0x2C1612, groupSettings(CreativeModeTab.TAB_MISC)));
    //Block Items
    public static final Supplier<Item> BOUNCE_BUG_JAR = ITEMS.register("bounce_bug_jar", () -> new BounceBugBottleItem(new Item.Properties()));
    public static final Supplier<Item> BUTTER_FLY_JAR = ITEMS.register("butterfly_jar", () -> new ButterFlyBottleItem(new Item.Properties()));
    public static final Supplier<Item> CATTIAL = ITEMS.register("cattail", () -> new DoubleHighBlockItem(SproutBlocks.CATTIAL.get(), groupSettings(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> DUNE_GRASS = ITEMS.register("dune_grass", () -> new BlockItem(SproutBlocks.DUNE_GRASS.get(), groupSettings(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> SPROUTS = ITEMS.register("sprouts", () -> new BlockItem(SproutBlocks.SPROUTS.get(), groupSettings(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> TALL_DEAD_BUSH = ITEMS.register("tall_dead_bush", () -> new DoubleHighBlockItem(SproutBlocks.TALL_DEAD_BUSH.get(), groupSettings(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> WATER_LENTIL = ITEMS.register("water_lentil", () -> new PlaceOnWaterBlockItem(SproutBlocks.WATER_LENTIL.get(), groupSettings(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> FLOWER_BOX = ITEMS.register("flower_box", () -> new BlockItem(SproutBlocks.FLOWER_BOX.get(), groupSettings(CreativeModeTab.TAB_DECORATIONS)));

    //Food Items
    public static final Supplier<Item> PEANUT = ITEMS.register("peanut", () -> new PeanutItem(SproutBlocks.PEANUT_PLANT_BLOCK.get(), groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.SWEET_BERRIES)));
    public static final Supplier<Item> WATER_SAUSAGE = ITEMS.register("water_sausage", () -> new WaterSausage(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.COD)));
    public static final Supplier<Item> PEANUT_BLOCK = ITEMS.register("peanut_block", () -> new BlockItem(SproutBlocks.PEANUT_BLOCK.get(), groupSettings(CreativeModeTab.TAB_FOOD)));

    public static void onComplete() {
        Object2FloatMap<ItemLike> compostMap = ComposterBlock.COMPOSTABLES;
        compostMap.put(PEANUT.get(), 0.3f);
        compostMap.put(WATER_SAUSAGE.get(), 1f);
        SproutFlowers.FLOWER_ITEMS.forEach(flower -> compostMap.put(flower.get(), 0.65f));
    }

    private static Item.Properties groupSettings(CreativeModeTab group) {
        return new Item.Properties().tab(group);
    }

    public static void registerItems() {
        SproutFlowers.registerItems();
        ITEMS.init();
    }
}
