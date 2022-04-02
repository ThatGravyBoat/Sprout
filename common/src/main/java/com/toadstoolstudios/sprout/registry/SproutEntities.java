package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.entities.MammothEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutEntities {
    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT_ENTITY_TYPE = registerEntity("elephant", ElephantEntity::new, SpawnGroup.AMBIENT, 0.9F, 0.9F);
    public static final Supplier<EntityType<MammothEntity>> MAMMOTH_ENTITY_TYPE = registerEntity("mammoth", MammothEntity::new, SpawnGroup.AMBIENT, 0.9F, 0.9F);
    public static final Supplier<EntityType<GlowflyEntity>> GLOWFLY_ENTITY_TYPE = registerEntity("glowfly", GlowflyEntity::new, SpawnGroup.AMBIENT, 0.5F, 0.5F);

    @ExpectPlatform
    public static void addSpawnRules() {
    }

    @NotNull
    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        return null;
    }

    public static void registerEntities() {
        // initialize class
    }
}
