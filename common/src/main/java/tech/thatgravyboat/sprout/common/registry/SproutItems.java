package tech.thatgravyboat.sprout.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import tech.thatgravyboat.sprout.common.items.BounceBugBottleItem;
import tech.thatgravyboat.sprout.common.items.PeanutItem;
import tech.thatgravyboat.sprout.common.items.WaterSausage;

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
    public static final Supplier<Item> FLOWER_BOX = registerItem("flower_box", () -> new BlockItem(SproutBlocks.FLOWER_BOX.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    //Food Items
    public static final Supplier<Item> PEANUT = registerItem("peanut", () -> new PeanutItem(SproutBlocks.PEANUT_PLANT_BLOCK.get(), groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.SWEET_BERRIES)));
    public static final Supplier<Item> WATER_SAUSAGE = registerItem("water_sausage", () -> new WaterSausage(groupSettings(CreativeModeTab.TAB_FOOD).food(Foods.COD)));

    public static void onComplete() {
        Object2FloatMap<ItemLike> compostMap = ComposterBlock.COMPOSTABLES;
        compostMap.put(PEANUT.get(), 0.3f);
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
        SproutFlowers.registerItems();
    }
}
