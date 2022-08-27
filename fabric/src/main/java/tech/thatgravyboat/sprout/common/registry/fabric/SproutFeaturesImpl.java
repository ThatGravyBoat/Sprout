package tech.thatgravyboat.sprout.common.registry.fabric;

import tech.thatgravyboat.sprout.Sprout;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class SproutFeaturesImpl {

    public static <T extends FeatureConfiguration, F extends Feature<T>> Supplier<F> registerFeature(String id, Supplier<F> input) {
        F feature = Registry.register(Registry.FEATURE, new ResourceLocation(Sprout.MODID, id), input.get());
        return () -> feature;
    }
}
