package tech.thatgravyboat.sprout.common.flowers;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tech.thatgravyboat.sprout.common.registry.SproutFlowers;
import tech.thatgravyboat.sprout.common.utils.RandomCollection;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FlowerBreedTree {

    public static final Map<FlowerBreed, RandomCollection<Block>> BREEDS = new HashMap<>();

    static {
        var normalFlowers = FlowerColor.stream().filter(FlowerColor::isNormal)
                .flatMap(color -> FlowerType.stream().map(type -> Pair.of(type, color)))
                .toList();
        var specialFlowers = FlowerColor.stream().filter(FlowerColor::isSpecial)
                .flatMap(color -> FlowerType.stream().map(type -> Pair.of(type, color)))
                .toList();
        var combinedFlowers = Stream.concat(specialFlowers.stream(), normalFlowers.stream()).toList();

        for (var flower1 : normalFlowers) {
            for (var flower2 : normalFlowers) {
                addFlowers(flower1, flower2, 40, 10);
            }
        }

        for (var flower1 : specialFlowers) {
            for (var flower2 : combinedFlowers) {
                addFlowers(flower1, flower2, 49, 1);
            }
        }

        for (var flower2 : combinedFlowers) {
            Block block2 = SproutFlowers.FLOWERS.get(flower2.getSecond()).get(flower2.getFirst()).get();
            Builder builder = new Builder(Blocks.WITHER_ROSE, block2);
            builder.add(Blocks.WITHER_ROSE, 49.5);
            builder.add(block2, 49.5);
            builder.add(SproutFlowers.FLOWERS.get(FlowerColor.BLACK).get(flower2.getFirst()), 0.5);
            builder.add(SproutFlowers.FLOWERS.get(FlowerColor.BROWN).get(flower2.getFirst()), 0.5);
            register(builder);
        }

        register(new Builder(Blocks.WITHER_ROSE, Blocks.WITHER_ROSE).add(Blocks.WITHER_ROSE, 1));
    }

    private static void addFlowers(Pair<FlowerType, FlowerColor> flower1, Pair<FlowerType, FlowerColor> flower2, double normal, double special) {
        var flower1Type = SproutFlowers.FLOWERS.get(flower1.getSecond());
        var flower2Type = SproutFlowers.FLOWERS.get(flower2.getSecond());
        Block block1 = flower1Type.get(flower1.getFirst()).get();
        Block block2 = flower2Type.get(flower2.getFirst()).get();
        if (block1 == block2) {
            register(new Builder(block1, block2).add(block1, 1));
            return;
        }
        Builder builder = new Builder(block1, block2);
        builder.add(block1, normal);
        builder.add(block2, normal);
        builder.add(flower1Type.get(flower2.getFirst()), special);
        builder.add(flower2Type.get(flower1.getFirst()), special);
        FlowerColor combined = flower1.getSecond().combined(flower2.getSecond());
        if (combined != null) {
            var cominbedType = SproutFlowers.FLOWERS.get(combined);
            builder.add(cominbedType.get(flower2.getFirst()), normal / 2);
            builder.add(cominbedType.get(flower1.getFirst()), normal / 2);
        }
        register(builder);
    }

    public static void register(Builder builder) {
        BREEDS.put(builder.breed, builder.flowers);
    }

    private static class Builder {

        private final FlowerBreed breed;
        private final RandomCollection<Block> flowers = new RandomCollection<>();

        public Builder(Block flower1, Block flower2) {
            this.breed = FlowerBreed.of(flower1, flower2);
        }

        public Builder add(Supplier<Block> flower, double weight) {
            flowers.add(weight, flower.get());
            return this;
        }

        public Builder add(Block flower, double weight) {
            flowers.add(weight, flower);
            return this;
        }
    }

}
