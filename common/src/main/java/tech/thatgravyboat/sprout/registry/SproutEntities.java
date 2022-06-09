package tech.thatgravyboat.sprout.registry;

import tech.thatgravyboat.sprout.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.entities.BounceBugVariant;
import tech.thatgravyboat.sprout.entities.ElephantEntity;
import tech.thatgravyboat.sprout.entities.TrackedEnum;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class SproutEntities {
    public static final EntityDataSerializer<BounceBugVariant> BOUNCE_BUG_VARIANT = new TrackedEnum<>(BounceBugVariant.class);
    static {
        EntityDataSerializers.registerSerializer(BOUNCE_BUG_VARIANT);
    }
    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT_ENTITY_TYPE = registerEntity("elephant", ElephantEntity::new, MobCategory.AMBIENT, 0.9F, 0.9F);
    public static final Supplier<EntityType<BounceBugEntity>> BOUNCE_BUG_ENTITY = registerEntity("bounce_bug", BounceBugEntity::new, MobCategory.AMBIENT, 0.5F, 0.5F);

    public static void addSpawnRules() {
        setSpawnRules(ELEPHANT_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ElephantEntity::canSpawn);
        setSpawnRules(BOUNCE_BUG_ENTITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, BounceBugEntity::canSpawn);
    }

    public static void addSpawns() {
        addEntityToBiome(Biomes.CRIMSON_FOREST, new SpawnData(BOUNCE_BUG_ENTITY.get(), MobCategory.AMBIENT, 25, 1, 4));
        addEntityToBiome(Biomes.WARPED_FOREST, new SpawnData(BOUNCE_BUG_ENTITY.get(), MobCategory.AMBIENT, 25, 1, 4));
        addEntityToBiome(Biomes.MEADOW, new SpawnData(ELEPHANT_ENTITY_TYPE.get(), MobCategory.AMBIENT, 1, 0 , 1));
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addEntityToBiome(ResourceKey<Biome> biome, SpawnData data) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        throw new AssertionError();
    }

    public static void registerEntities() {
        // initialize class
    }

}
