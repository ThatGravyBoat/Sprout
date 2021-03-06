package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.BounceBugVariant;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.TrackedEnum;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.function.Supplier;

public class SproutEntities {
    public static final TrackedDataHandler<BounceBugVariant> BOUNCE_BUG_VARIANT = new TrackedEnum<>(BounceBugVariant.class);
    static {
        TrackedDataHandlerRegistry.register(BOUNCE_BUG_VARIANT);
    }
    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT_ENTITY_TYPE = registerEntity("elephant", ElephantEntity::new, SpawnGroup.AMBIENT, 0.9F, 0.9F);
    public static final Supplier<EntityType<BounceBugEntity>> BOUNCE_BUG_ENTITY = registerEntity("bounce_bug", BounceBugEntity::new, SpawnGroup.AMBIENT, 0.5F, 0.5F);

    public static void addSpawnRules() {
        setSpawnRules(ELEPHANT_ENTITY_TYPE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, ElephantEntity::canSpawn);
        setSpawnRules(BOUNCE_BUG_ENTITY.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, BounceBugEntity::canSpawn);
    }

    public static void addSpawns() {
        addEntityToBiome(BiomeKeys.CRIMSON_FOREST, new SpawnData(BOUNCE_BUG_ENTITY.get(), SpawnGroup.AMBIENT, 25, 1, 4));
        addEntityToBiome(BiomeKeys.WARPED_FOREST, new SpawnData(BOUNCE_BUG_ENTITY.get(), SpawnGroup.AMBIENT, 25, 1, 4));
        addEntityToBiome(BiomeKeys.MEADOW, new SpawnData(ELEPHANT_ENTITY_TYPE.get(), SpawnGroup.AMBIENT, 1, 0 , 1));
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addEntityToBiome(RegistryKey<Biome> biome, SpawnData data) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        throw new AssertionError();
    }

    public static void registerEntities() {
        // initialize class
    }

}
