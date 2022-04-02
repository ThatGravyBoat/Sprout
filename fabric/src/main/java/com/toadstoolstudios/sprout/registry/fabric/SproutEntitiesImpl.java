package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.registry.SpawnData;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.function.Supplier;

public class SproutEntitiesImpl {

    public static void addSpawnRules() {
        SpawnRestrictionAccessor.callRegister(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, spawnReason, pos, random) -> {
            BlockState state = world.getBlockState(pos.down());
            boolean isGrassLike = state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.PODZOL) || state.isOf(Blocks.MYCELIUM) || state.isOf(Blocks.DIRT);
            return pos.getY() > world.getSeaLevel() && isGrassLike;
        });
        SpawnRestrictionAccessor.callRegister(SproutEntities.GLOWFLY_ENTITY_TYPE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, (type, world, spawnReason, pos, random) -> {
            BlockState state = world.getBlockState(pos);
            return pos.getY() > world.getSeaLevel() && (state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof TallFlowerBlock || state.getBlock() instanceof LeavesBlock) && state.getLuminance() < 7;
        });
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.getBiomeKey().getValue().getPath().equals("flower_forest"), SpawnGroup.AMBIENT, SproutEntities.ELEPHANT_ENTITY_TYPE.get(), 25, 0, 1);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        var register = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sprout.MODID, name), FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.fixed(width, height)).build());
        return () -> register;
    }

    public static void addEntityToBiome(Biome.Category category, SpawnData data) {
        BiomeModifications.addSpawn(BiomeSelectors.categories(category), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    public static <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType, location, type, predicate);
    }
}
