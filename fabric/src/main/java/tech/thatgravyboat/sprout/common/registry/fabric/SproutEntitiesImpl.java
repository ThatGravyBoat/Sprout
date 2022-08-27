package tech.thatgravyboat.sprout.common.registry.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.registry.SpawnData;

import java.util.function.Supplier;

public class SproutEntitiesImpl {

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        var register = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Sprout.MODID, name), FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.fixed(width, height)).build());
        return () -> register;
    }

    public static void addEntityToBiome(ResourceKey<Biome> category, SpawnData data) {
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey().equals(category), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    public static <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, location, type, predicate);
    }
}
