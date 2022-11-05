package tech.thatgravyboat.sprout.common.registry;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.blocks.PeanutCrop;
import tech.thatgravyboat.sprout.common.world.FallenTreeFeature;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class SproutConfiguredFeatures {

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PEANUT_PATCH = FeatureUtils.register(
            "sprout:patch_peanut", Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    BlockStateProvider.simple(SproutBlocks.PEANUT_PLANT_BLOCK.get().defaultBlockState().setValue(PeanutCrop.AGE, 2))
                ),
                List.of(Blocks.GRASS_BLOCK),
                32
            )
    );

    public static final Holder<PlacedFeature> PLACED_PEANUT_PATCH = PlacementUtils.register(
            "sprout:peanut_patch_common", PEANUT_PATCH,
            RarityFilter.onAverageOnceEvery(16),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> TALL_DEAD_BUSH = FeatureUtils.register(
            "sprout:tall_dead_bush", Feature.SIMPLE_RANDOM_SELECTOR,
            new SimpleRandomFeatureConfiguration(
                HolderSet.direct(
                    PlacementUtils.onlyWhenEmpty(
                        Feature.RANDOM_PATCH,
                        FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(SproutBlocks.TALL_DEAD_BUSH.get())), List.of(Blocks.TERRACOTTA, Blocks.SAND, Blocks.RED_SAND), 30)
                    )
                )
            )
    );

    public static final Holder<PlacedFeature> PLACED_TALL_DEAD_BUSH = PlacementUtils.register(
            "sprout:tall_dead_bush_common", TALL_DEAD_BUSH,
            RarityFilter.onAverageOnceEvery(16),
            NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DUNE_GRASS = FeatureUtils.register(
            "sprout:dune_grass", Feature.RANDOM_PATCH,
            createRandomPatchConfiguration(BlockStateProvider.simple(SproutBlocks.DUNE_GRASS.get()))
    );

    public static final Holder<PlacedFeature> PLACED_DUNE_GRASS = PlacementUtils.register(
            "sprout:dune_grass_common", DUNE_GRASS,
            NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> CATTAILS = FeatureUtils.register(
            "sprout:cattails", Feature.SIMPLE_RANDOM_SELECTOR,
            createSingleBlockConfig(SproutBlocks.CATTIAL.get(), Blocks.GRASS_BLOCK)
    );

    public static final Holder<PlacedFeature> PLACED_CATTAILS = PlacementUtils.register(
            "sprout:cattails_common", CATTAILS,
            RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SPROUTS = FeatureUtils.register(
            "sprout:sprouts", Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(SproutBlocks.SPROUTS.get())), List.of(Blocks.GRASS_BLOCK), Sprout.CONFIG.worldGen.sprouts.frequency)
    );

    public static final Holder<PlacedFeature> PLACED_SPROUTS = PlacementUtils.register(
            "sprout:sprouts_common", SPROUTS,
            NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    );

    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> WATER_LENTILS = FeatureUtils.register(
            "sprout:water_lentils", Feature.SIMPLE_RANDOM_SELECTOR,
            createSingleBlockConfig(SproutBlocks.WATER_LENTIL.get(), Blocks.WATER)
    );

    public static final Holder<PlacedFeature> PLACED_WATER_LENTILS = PlacementUtils.register(
            "sprout:water_lentils_common", WATER_LENTILS,
            NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> PLACED_OAK_FALLEN_TREE = createFallenTree(Blocks.OAK_LOG, 0.5f, 0f, 0.3f, "oak");
    public static final Holder<PlacedFeature> PLACED_OAK_MOSS_FALLEN_TREE = createFallenTree(Blocks.OAK_LOG, 0.5f, 0f, 0f, "oak_moss");
    public static final Holder<PlacedFeature> PLACED_BIRCH_FALLEN_TREE = createFallenTree(Blocks.BIRCH_LOG, 0.5f, 0f, 0.1f, "birch");
    public static final Holder<PlacedFeature> PLACED_DARK_OAK_FALLEN_TREE = createFallenTree(Blocks.DARK_OAK_LOG, 0.5f, 0.3f, 0.3f, "dark_oak");
    public static final Holder<PlacedFeature> PLACED_SPRUCE_FALLEN_TREE = createFallenTree(Blocks.SPRUCE_LOG, 0.3f, 0f, 0.4f, "spruce");


    public static void registerFeatures() {
        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_PEANUT_PATCH, Biomes.MEADOW);

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_TALL_DEAD_BUSH, Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS);
        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_DUNE_GRASS, Biomes.DESERT);

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_CATTAILS, Biomes.SWAMP);

        if (Sprout.CONFIG.worldGen.sprouts.enabled) {
            registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_SPROUTS,
                Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.FLOWER_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST,
                Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE,
                Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS,
                Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU,
                Biomes.TAIGA,
                Biomes.SWAMP
            );
        }

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_WATER_LENTILS, Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_OAK_FALLEN_TREE, Biomes.FOREST, Biomes.FLOWER_FOREST);

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_BIRCH_FALLEN_TREE, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_DARK_OAK_FALLEN_TREE, Biomes.DARK_FOREST);

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_SPRUCE_FALLEN_TREE, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA);

        registerFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PLACED_OAK_MOSS_FALLEN_TREE, Biomes.SWAMP);
    }

    private static Holder<PlacedFeature> createFallenTree(Block log, float moss, float red, float brown, String id) {
        Holder<ConfiguredFeature<FallenTreeFeature.FallenTreeConfig, ?>> tree = FeatureUtils.register(
                "sprout:"+id+"_fallen_tree", SproutFeatures.FALLEN_TREE.get(),
                new FallenTreeFeature.FallenTreeConfig(log, moss, red, brown)
        );

        return PlacementUtils.register(
                "sprout:"+id+"_fallen_tree_common", tree,
                RarityFilter.onAverageOnceEvery(64),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }

    private static SimpleRandomFeatureConfiguration createSingleBlockConfig(Block block, Block... allowedBlocks) {
        return new SimpleRandomFeatureConfiguration(
                HolderSet.direct(
                        PlacementUtils.onlyWhenEmpty(
                                Feature.RANDOM_PATCH,
                                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(BlockStateProvider.simple(block)), List.of(allowedBlocks))
                        )
                )
        );
    }

    private static RandomPatchConfiguration createRandomPatchConfiguration(BlockStateProvider block) {
        return FeatureUtils.simpleRandomPatchConfiguration(5, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

    @SafeVarargs
    @ExpectPlatform
    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, ResourceKey<Biome>... biome) {
        throw new AssertionError();
    }
}
