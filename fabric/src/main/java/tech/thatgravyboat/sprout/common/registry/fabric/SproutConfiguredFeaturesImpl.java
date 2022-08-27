package tech.thatgravyboat.sprout.common.registry.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SproutConfiguredFeaturesImpl {

    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, ResourceKey<Biome> biome) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(biome), feature, entry.unwrapKey().orElseThrow());
    }

    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, TagKey<Biome> category) {
        BiomeModifications.addFeature(BiomeSelectors.tag(category), feature, entry.unwrapKey().orElseThrow());
    }

    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, ResourceLocation biome) {
        BiomeModifications.addFeature(ctx -> ctx.getBiomeKey().location().equals(biome), feature, entry.unwrapKey().orElseThrow());
    }
}
