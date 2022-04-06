package com.toadstoolstudios.sprout.registry.forge;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.registry.SpawnData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutEntitiesImpl {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Sprout.MODID);
    public static final ListMultimap<String, SpawnData> ENTITY_SPAWNS = ArrayListMultimap.create();

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.create(factory, group).setDimensions(width, height).build(name));
    }

    public static void addEntityToBiome(Biome.Category category, SpawnData data) {
        //please ignore
    }

    public static void addEntityToBiome(RegistryKey<Biome> biome, SpawnData data) {
        ENTITY_SPAWNS.put(biome.getRegistryName().getPath(), data);
    }

    public static <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestriction.register(entityType, location, type, predicate);
    }

}
