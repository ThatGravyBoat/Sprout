package com.toadstoolstudios.sprout.registry.forge;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class SproutConfiguredFeaturesImpl {

    public static final ListMultimap<Identifier, SproutConfiguredFeaturesImpl.FeaturePlacementData> FEATURES = ArrayListMultimap.create();
    public static final ListMultimap<Biome.Category, SproutConfiguredFeaturesImpl.FeaturePlacementData> CATEGORY_FEATURES = ArrayListMultimap.create();

    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, RegistryKey<Biome> biome) {
        FEATURES.put(biome.getValue(), new SproutConfiguredFeaturesImpl.FeaturePlacementData(feature, entry));
    }

    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, Biome.Category category) {
        CATEGORY_FEATURES.put(category, new SproutConfiguredFeaturesImpl.FeaturePlacementData(feature, entry));
    }

    public static void registerFeature(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry, Identifier biome) {
        FEATURES.put(biome, new SproutConfiguredFeaturesImpl.FeaturePlacementData(feature, entry));
    }

    public static record FeaturePlacementData(GenerationStep.Feature feature, RegistryEntry<PlacedFeature> entry){}
}
