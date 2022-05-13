package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.blocks.PeanutCrop;
import com.toadstoolstudios.sprout.world.FallenTreeFeature;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class SproutConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PEANUT_PATCH = ConfiguredFeatures.register(
            "sprout:patch_peanut", Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                            BlockStateProvider.of(SproutBlocks.PEANUT_PLANT_BLOCK.get().getDefaultState().with(PeanutCrop.AGE, 2))
                    ), List.of(Blocks.GRASS_BLOCK), 32)
    );

    public static final RegistryEntry<PlacedFeature> PLACED_PEANUT_PATCH = PlacedFeatures.register(
            "sprout:peanut_patch_common", PEANUT_PATCH,
            RarityFilterPlacementModifier.of(16),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> TALL_DEAD_BUSH = ConfiguredFeatures.register(
            "sprout:tall_dead_bush", Feature.SIMPLE_RANDOM_SELECTOR,
            new SimpleRandomFeatureConfig(
                    RegistryEntryList.of(
                            PlacedFeatures.createEntry(
                                    Feature.RANDOM_PATCH,
                                    ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                                            new SimpleBlockFeatureConfig(BlockStateProvider.of(SproutBlocks.TALL_DEAD_BUSH.get())), List.of(Blocks.TERRACOTTA, Blocks.SAND, Blocks.RED_SAND), 30)
                            )
                    )
            )
    );

    public static final RegistryEntry<PlacedFeature> PLACED_TALL_DEAD_BUSH = PlacedFeatures.register(
            "sprout:tall_dead_bush_common", TALL_DEAD_BUSH,
            RarityFilterPlacementModifier.of(16),
            NoiseThresholdCountPlacementModifier.of(-0.8D, 5, 10),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> DUNE_GRASS = ConfiguredFeatures.register(
            "sprout:dune_grass", Feature.RANDOM_PATCH,
            createRandomPatchFeatureConfig(BlockStateProvider.of(SproutBlocks.DUNE_GRASS.get()), 5)
    );

    public static final RegistryEntry<PlacedFeature> PLACED_DUNE_GRASS = PlacedFeatures.register(
            "sprout:dune_grass_common", DUNE_GRASS,
            NoiseThresholdCountPlacementModifier.of(-0.8D, 5, 10),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> CATTAILS = ConfiguredFeatures.register(
            "sprout:cattails", Feature.SIMPLE_RANDOM_SELECTOR,
            createSingleBlockConfig(SproutBlocks.CATTIAL.get(), Blocks.GRASS_BLOCK)
    );

    public static final RegistryEntry<PlacedFeature> PLACED_CATTAILS = PlacedFeatures.register(
            "sprout:cattails_common", CATTAILS,
            RarityFilterPlacementModifier.of(6),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> SPROUTS = ConfiguredFeatures.register(
            "sprout:sprouts", Feature.RANDOM_PATCH,
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(SproutBlocks.SPROUTS.get())), List.of(Blocks.GRASS_BLOCK), Sprout.CONFIG.worldGen.sprouts.frequency)
    );

    public static final RegistryEntry<PlacedFeature> PLACED_SPROUTS = PlacedFeatures.register(
            "sprout:sprouts_common", SPROUTS,
            NoiseThresholdCountPlacementModifier.of(-0.8D, 5, 10),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> WATER_LENTILS = ConfiguredFeatures.register(
            "sprout:water_lentils", Feature.SIMPLE_RANDOM_SELECTOR,
            createSingleBlockConfig(SproutBlocks.WATER_LENTIL.get(), Blocks.WATER)
    );

    public static final RegistryEntry<PlacedFeature> PLACED_WATER_LENTILS = PlacedFeatures.register(
            "sprout:water_lentils_common", WATER_LENTILS,
            NoiseThresholdCountPlacementModifier.of(-0.8D, 5, 10),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<PlacedFeature> PLACED_OAK_FALLEN_TREE = createFallenTree(Blocks.OAK_LOG, 0.5f, 0f, 0.3f, "oak");
    public static final RegistryEntry<PlacedFeature> PLACED_OAK_MOSS_FALLEN_TREE = createFallenTree(Blocks.OAK_LOG, 0.5f, 0f, 0f, "oak_moss");
    public static final RegistryEntry<PlacedFeature> PLACED_BIRCH_FALLEN_TREE = createFallenTree(Blocks.BIRCH_LOG, 0.5f, 0f, 0.1f, "birch");
    public static final RegistryEntry<PlacedFeature> PLACED_DARK_OAK_FALLEN_TREE = createFallenTree(Blocks.DARK_OAK_LOG, 0.5f, 0.3f, 0.3f, "dark_oak");
    public static final RegistryEntry<PlacedFeature> PLACED_SPRUCE_FALLEN_TREE = createFallenTree(Blocks.SPRUCE_LOG, 0.3f, 0f, 0.4f, "spruce");


    public static void registerFeatures() {
        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_PEANUT_PATCH, BiomeKeys.MEADOW);

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_TALL_DEAD_BUSH, Biome.Category.MESA);
        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_DUNE_GRASS, Biome.Category.DESERT);

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_CATTAILS, Biome.Category.SWAMP);

        if (Sprout.CONFIG.worldGen.sprouts.enabled) {
            registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPROUTS, Biome.Category.FOREST);
            registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPROUTS, Biome.Category.SWAMP);
            registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPROUTS, Biome.Category.PLAINS);
            registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPROUTS, Biome.Category.TAIGA);
            registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPROUTS, Biome.Category.SAVANNA);
            registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPROUTS, Biome.Category.JUNGLE);
        }

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_WATER_LENTILS, Biome.Category.SWAMP);

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_OAK_FALLEN_TREE, BiomeKeys.FOREST);
        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_OAK_FALLEN_TREE, BiomeKeys.FLOWER_FOREST);


        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_BIRCH_FALLEN_TREE, BiomeKeys.BIRCH_FOREST);
        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_BIRCH_FALLEN_TREE, BiomeKeys.OLD_GROWTH_BIRCH_FOREST);

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_DARK_OAK_FALLEN_TREE, BiomeKeys.DARK_FOREST);

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPRUCE_FALLEN_TREE, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_SPRUCE_FALLEN_TREE, BiomeKeys.OLD_GROWTH_PINE_TAIGA);

        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_OAK_MOSS_FALLEN_TREE, Biome.Category.SWAMP);
    }

    private static RegistryEntry<PlacedFeature> createFallenTree(Block log, float moss, float red, float brown, String id) {
        RegistryEntry<ConfiguredFeature<FallenTreeFeature.FallenTreeConfig, ?>> tree = ConfiguredFeatures.register(
                "sprout:"+id+"_fallen_tree", SproutFeatures.FALLEN_TREE.get(),
                new FallenTreeFeature.FallenTreeConfig(log, moss, red, brown)
        );

        return PlacedFeatures.register(
                "sprout:"+id+"_fallen_tree_common", tree,
                RarityFilterPlacementModifier.of(64),
                SquarePlacementModifier.of(),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
    }

    private static SimpleRandomFeatureConfig createSingleBlockConfig(Block block, Block... allowedBlocks) {
        return new SimpleRandomFeatureConfig(
                RegistryEntryList.of(
                        PlacedFeatures.createEntry(
                                Feature.RANDOM_PATCH,
                                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                                        new SimpleBlockFeatureConfig(BlockStateProvider.of(block)), List.of(allowedBlocks))
                        )
                )
        );
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }

    @ExpectPlatform
    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, RegistryKey<Biome> biome) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, Identifier biome) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, Biome.Category category) {
        throw new AssertionError();
    }
}
