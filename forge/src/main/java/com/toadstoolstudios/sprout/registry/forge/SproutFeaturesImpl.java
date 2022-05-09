package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutFeaturesImpl {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Sprout.MODID);

    public static <T extends FeatureConfig, F extends Feature<T>> Supplier<F> registerFeature(String id, Supplier<F> feature) {
        return FEATURES.register(id, feature);
    }
}
