package tech.thatgravyboat.sprout.common.registry.forge;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SproutConfiguredFeaturesImpl {
    @SafeVarargs
    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, ResourceKey<Biome>... biome) {
    }

    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, TagKey<Biome> category) {
    }

    public static void registerFeature(GenerationStep.Decoration feature, Holder<PlacedFeature> entry, ResourceLocation biome) {
    }
}
