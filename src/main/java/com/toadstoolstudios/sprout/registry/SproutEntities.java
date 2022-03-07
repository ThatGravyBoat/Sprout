package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.entities.MammothEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutEntities {
    public static final EntityType<ElephantEntity> ELEPHANT_ENTITY_TYPE = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, ElephantEntity::new).dimensions(EntityDimensions.changing(0.9F,0.9F)).build();
    public static final EntityType<MammothEntity> MAMMOTH_ENTITY_TYPE = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, MammothEntity::new).dimensions(EntityDimensions.changing(0.9F,0.9F)).build();
    public static final EntityType<GlowflyEntity> GLOWFLY_ENTITY_TYPE = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, GlowflyEntity::new).dimensions(EntityDimensions.changing(0.5F,0.5F)).build();

    public static void registerEntities() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "elephant"), ELEPHANT_ENTITY_TYPE);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "mammoth"), MAMMOTH_ENTITY_TYPE);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "glowfly"), GLOWFLY_ENTITY_TYPE);
    }

    public static void addSpawnRules() {
        SpawnRestrictionAccessor.callRegister(ELEPHANT_ENTITY_TYPE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, spawnReason, pos, random) -> {
            BlockState state = world.getBlockState(pos.down());
            boolean isGrassLike = state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.PODZOL) || state.isOf(Blocks.MYCELIUM) || state.isOf(Blocks.DIRT);
            return pos.getY() > world.getSeaLevel() && isGrassLike;
        });
        SpawnRestrictionAccessor.callRegister(GLOWFLY_ENTITY_TYPE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, (type, world, spawnReason, pos, random) -> {
            BlockState state = world.getBlockState(pos);
            return pos.getY() > world.getSeaLevel() && (state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof TallFlowerBlock || state.getBlock() instanceof LeavesBlock) && state.getLuminance() < 7;
        });
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.SAVANNA), SpawnGroup.AMBIENT, ELEPHANT_ENTITY_TYPE, 25, 0, 1);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.PLAINS), SpawnGroup.AMBIENT, GLOWFLY_ENTITY_TYPE, 25, 8, 12);
    }
}
