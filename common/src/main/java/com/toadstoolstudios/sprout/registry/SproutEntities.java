package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.*;

import java.util.function.Supplier;

public class SproutEntities {
    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT_ENTITY_TYPE = registerEntity("elephant", ElephantEntity::new, SpawnGroup.AMBIENT, 0.9F, 0.9F);
    public static final Supplier<EntityType<GlowflyEntity>> GLOWFLY_ENTITY_TYPE = registerEntity("glowfly", GlowflyEntity::new, SpawnGroup.AMBIENT, 0.5F, 0.5F);

    @ExpectPlatform
    public static void addSpawnRules() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        throw new AssertionError();
    }

    public static void registerEntities() {
        // initialize class
    }
}
