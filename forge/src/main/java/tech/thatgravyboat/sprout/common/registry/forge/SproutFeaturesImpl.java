package tech.thatgravyboat.sprout.common.registry.forge;

import tech.thatgravyboat.sprout.Sprout;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutFeaturesImpl {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Sprout.MODID);

    public static <T extends FeatureConfiguration, F extends Feature<T>> Supplier<F> registerFeature(String id, Supplier<F> feature) {
        return FEATURES.register(id, feature);
    }
}
