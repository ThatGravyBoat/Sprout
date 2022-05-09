package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.Supplier;

public class SproutFeaturesImpl {

    public static <T extends FeatureConfig, F extends Feature<T>> Supplier<F> registerFeature(String id, Supplier<F> input) {
        F feature = Registry.register(Registry.FEATURE, new Identifier(Sprout.MODID, id), input.get());
        return () -> feature;
    }
}
