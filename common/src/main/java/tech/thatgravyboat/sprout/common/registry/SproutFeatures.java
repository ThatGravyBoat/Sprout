package tech.thatgravyboat.sprout.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.world.FallenTreeFeature;

import java.util.function.Supplier;

public class SproutFeatures {

    public static final ResourcefulRegistry<Feature<?>> FEATURES = ResourcefulRegistries.create(Registry.FEATURE, Sprout.MODID);

    public static final Supplier<Feature<FallenTreeFeature.FallenTreeConfig>> FALLEN_TREE = FEATURES.register("fallen_tree", () -> new FallenTreeFeature(FallenTreeFeature.FallenTreeConfig.CODEC));
}
