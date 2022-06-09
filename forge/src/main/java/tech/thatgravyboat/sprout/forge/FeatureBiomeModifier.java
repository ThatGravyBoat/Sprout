package tech.thatgravyboat.sprout.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record FeatureBiomeModifier(HolderSet<Biome> biomes, GenerationStep.Decoration decoration, HolderSet<PlacedFeature> features) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> arg, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase.equals(Phase.ADD) && biomes().contains(arg)) {
            features().forEach(feature -> {
                if (!builder.getGenerationSettings().getFeatures(decoration()).contains(feature)) {
                    builder.getGenerationSettings().addFeature(decoration(), feature);
                }
            });
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SproutForge.FEATURE_MODIFIER.get();
    }

    public static final Codec<GenerationStep.Decoration> DECORATION_CODEC = StringRepresentable.fromEnum(GenerationStep.Decoration::values);

    public static Codec<FeatureBiomeModifier> makeCodec() {
        return RecordCodecBuilder.create(instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(FeatureBiomeModifier::biomes),
            DECORATION_CODEC.fieldOf("decoration").orElse(GenerationStep.Decoration.VEGETAL_DECORATION).forGetter(FeatureBiomeModifier::decoration),
            RegistryCodecs.homogeneousList(Registry.PLACED_FEATURE_REGISTRY).fieldOf("features").forGetter(FeatureBiomeModifier::features)
        ).apply(instance, FeatureBiomeModifier::new));
    }
}
