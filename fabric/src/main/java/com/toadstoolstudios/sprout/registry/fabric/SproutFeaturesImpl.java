package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.blocks.PeanutCrop;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class SproutFeaturesImpl {

    public static void registerFeatures() {
        var patch = ConfiguredFeatures.register("patch_peanut", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(SproutBlocks.PEANUT_PLANT_BLOCK.get().getDefaultState().with(PeanutCrop.AGE, 2))), List.of(Blocks.GRASS_BLOCK)));
        var placedFeature = PlacedFeatures.register("peanut_patch_common", patch, RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.SAVANNA), GenerationStep.Feature.VEGETAL_DECORATION, placedFeature.getKey().get());
    }
}
