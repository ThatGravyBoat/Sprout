package tech.thatgravyboat.sprout.registry;

import tech.thatgravyboat.sprout.world.FallenTreeFeature;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class SproutFeatures {

    public static final Supplier<Feature<FallenTreeFeature.FallenTreeConfig>> FALLEN_TREE = registerFeature("fallen_tree", () -> new FallenTreeFeature(FallenTreeFeature.FallenTreeConfig.CODEC));

    public static void register() {
        //Initialize class
    }

    @ExpectPlatform
    public static <T extends FeatureConfiguration, F extends Feature<T>> Supplier<F> registerFeature(String id, Supplier<F> feature) {
        throw new AssertionError();
    }
}
