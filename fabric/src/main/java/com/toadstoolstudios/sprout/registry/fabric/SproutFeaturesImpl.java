package com.toadstoolstudios.sprout.registry.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class SproutFeaturesImpl {

    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, RegistryKey<Biome> biome) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(biome), feature, entry.getKey().orElseThrow());
    }
}
