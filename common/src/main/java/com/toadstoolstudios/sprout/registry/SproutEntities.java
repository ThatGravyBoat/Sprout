package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.entities.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.*;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.function.Supplier;

public class SproutEntities {
    public static final TrackedDataHandler<BounceBugVariant> BOUNCE_BUG_VARIANT = new TrackedEnum<>(BounceBugVariant.class);
    static {
        TrackedDataHandlerRegistry.register(BOUNCE_BUG_VARIANT);
    }
    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT_ENTITY_TYPE = registerEntity("elephant", ElephantEntity::new, SpawnGroup.AMBIENT, 0.9F, 0.9F);
    public static final Supplier<EntityType<GlowflyEntity>> GLOWFLY_ENTITY_TYPE = registerEntity("glowfly", GlowflyEntity::new, SpawnGroup.AMBIENT, 0.5F, 0.5F);
    public static final Supplier<EntityType<BounceBugEntity>> BOUNCE_BUG_ENTITY = registerEntity("bounce_bug", BounceBugEntity::new, SpawnGroup.AMBIENT, 0.5F, 0.5F);

    @ExpectPlatform
    public static void addSpawnRules() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addEntityToBiome(Biome.Category category, SpawnData data) {
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
