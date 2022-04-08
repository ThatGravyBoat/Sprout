package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.blocks.PeanutCrop;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class SproutFeatures {

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

    public static void registerFeatures() {
        registerFeature(GenerationStep.Feature.VEGETAL_DECORATION, PLACED_PEANUT_PATCH, BiomeKeys.MEADOW);
    }

    @ExpectPlatform
    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, RegistryKey<Biome> biome) {
        throw new AssertionError();
    }
}
