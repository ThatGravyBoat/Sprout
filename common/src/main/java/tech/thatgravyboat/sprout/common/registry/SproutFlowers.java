package tech.thatgravyboat.sprout.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.thatgravyboat.sprout.common.flowers.FlowerColor;
import tech.thatgravyboat.sprout.common.flowers.FlowerType;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SproutFlowers {

    public static final Map<FlowerColor, Map<FlowerType, Supplier<Block>>> FLOWERS = Util.make(new HashMap<>(), map -> {
        map.compute(FlowerColor.WHITE, make(flowers -> flowers.put(FlowerType.TULIP, () -> Blocks.WHITE_TULIP)));
        map.compute(FlowerColor.WHITE, make(flowers -> flowers.put(FlowerType.LILY_OF_THE_VALLEY, () -> Blocks.LILY_OF_THE_VALLEY)));
        map.compute(FlowerColor.ORANGE, make(flowers -> flowers.put(FlowerType.TULIP, () -> Blocks.ORANGE_TULIP)));
        map.compute(FlowerColor.MAGENTA, make(flowers -> flowers.put(FlowerType.ALLIUM, () -> Blocks.ALLIUM)));
        map.compute(FlowerColor.LIGHT_BLUE, make(flowers -> flowers.put(FlowerType.ORCHID, () -> Blocks.BLUE_ORCHID)));
        map.compute(FlowerColor.YELLOW, make(flowers -> flowers.put(FlowerType.DANDELION, () -> Blocks.DANDELION)));
        map.compute(FlowerColor.PINK, make(flowers -> flowers.put(FlowerType.TULIP, () -> Blocks.PINK_TULIP)));
        map.compute(FlowerColor.LIGHT_GRAY, make(flowers -> flowers.put(FlowerType.AZURE_BLUET, () -> Blocks.AZURE_BLUET)));
        map.compute(FlowerColor.LIGHT_GRAY, make(flowers -> flowers.put(FlowerType.DAISY, () -> Blocks.OXEYE_DAISY)));
        map.compute(FlowerColor.BLUE, make(flowers -> flowers.put(FlowerType.CORNFLOWER, () -> Blocks.CORNFLOWER)));
        map.compute(FlowerColor.RED, make(flowers -> flowers.put(FlowerType.POPPY, () -> Blocks.POPPY)));
        map.compute(FlowerColor.RED, make(flowers -> flowers.put(FlowerType.TULIP, () -> Blocks.RED_TULIP)));
    });

    public static final List<Supplier<Block>> POTTED_FLOWERS = new ArrayList<>();

    public static void registerBlocks() {
        for (FlowerColor color : FlowerColor.values()) {
            for (FlowerType type : FlowerType.values()) {
                if (!color.bannedTypes.contains(type)) {
                    String id = color.name().toLowerCase(Locale.ROOT) + "_" + type.name().toLowerCase(Locale.ROOT);
                    Supplier<Block> block = SproutBlocks.registerBlock(id, () -> new FlowerBlock(MobEffects.ABSORPTION, 4, BlockBehaviour.Properties.copy(Blocks.POPPY)));
                    FLOWERS.compute(color, make(flowers -> flowers.put(type, block)));
                    POTTED_FLOWERS.add(registerFlowerPot("potted_" + id, block));
                }
            }
        }
    }

    public static void registerItems() {
        for (var color : FLOWERS.entrySet()) {
            FlowerColor flowerColor = color.getKey();
            for (var flowers : color.getValue().entrySet()) {
                if (flowerColor.bannedTypes.contains(flowers.getKey())) continue;
                String id = flowerColor.name().toLowerCase(Locale.ROOT) + "_" + flowers.getKey().name().toLowerCase(Locale.ROOT);
                SproutItems.registerItem(id, () -> new BlockItem(flowers.getValue().get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
            }
        }
    }

    private static <A, K, V> BiFunction<? super A, Map<K, V>, Map<K, V>> make(Consumer<Map<K, V>> map) {
        return (k, v) -> {
            if (v != null) {
                map.accept(v);
                return v;
            }
            Map<K, V> newMap = new HashMap<>();
            map.accept(newMap);
            return newMap;
        };
    }

    @ExpectPlatform
    private static Supplier<Block> registerFlowerPot(String id, Supplier<Block> block) {
        throw new AssertionError();
    }

}
