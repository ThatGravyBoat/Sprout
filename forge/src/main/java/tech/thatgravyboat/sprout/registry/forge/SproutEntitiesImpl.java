package tech.thatgravyboat.sprout.registry.forge;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.registry.SpawnData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutEntitiesImpl {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Sprout.MODID);

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, group).sized(width, height).build(name));
    }

    public static void addEntityToBiome(ResourceKey<Biome> biome, SpawnData data) {

    }

    public static <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, location, type, predicate);
    }

}
