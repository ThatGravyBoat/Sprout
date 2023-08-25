package tech.thatgravyboat.sprout.common.registry.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.sprout.common.registry.SpawnData;

public class SproutEntitiesImpl {

    public static void addEntityToBiome(ResourceKey<Biome> category, SpawnData data) {
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey().equals(category), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    public static <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, location, type, predicate);
    }
}
