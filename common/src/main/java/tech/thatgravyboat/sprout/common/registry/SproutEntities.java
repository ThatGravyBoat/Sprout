package tech.thatgravyboat.sprout.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.common.entities.ButterFly;
import tech.thatgravyboat.sprout.common.entities.ElephantEntity;

import java.util.function.Supplier;

import static tech.thatgravyboat.sprout.common.utils.PlatformRegistryHelper.createEntityType;

public class SproutEntities {

    public static final ResourcefulRegistry<EntityType<?>> ENTITIES = ResourcefulRegistries.create(Registry.ENTITY_TYPE, Sprout.MODID);

    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT_ENTITY_TYPE = ENTITIES.register("elephant",
            () -> createEntityType(ElephantEntity::new, MobCategory.CREATURE, EntityDimensions.scalable(0.9F, 0.9F)));

    public static final Supplier<EntityType<BounceBugEntity>> BOUNCE_BUG_ENTITY = ENTITIES.register("bounce_bug",
            () -> createEntityType(BounceBugEntity::new, MobCategory.CREATURE, EntityDimensions.scalable(0.5F, 0.5F)));

    public static final Supplier<EntityType<ButterFly>> BUTTERFLY = ENTITIES.register("butterfly",
            () -> createEntityType(ButterFly::new, MobCategory.AMBIENT, EntityDimensions.scalable(0.55F, 0.55F)));

    public static void addSpawnRules() {
        setSpawnRules(ELEPHANT_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ElephantEntity::canSpawn);
        setSpawnRules(BOUNCE_BUG_ENTITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, BounceBugEntity::canSpawn);
        setSpawnRules(BUTTERFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ButterFly::canSpawn);
    }

    public static void addSpawns() {
        addEntityToBiome(Biomes.CRIMSON_FOREST, new SpawnData(BOUNCE_BUG_ENTITY.get(), MobCategory.CREATURE, 25, 1, 4));
        addEntityToBiome(Biomes.WARPED_FOREST, new SpawnData(BOUNCE_BUG_ENTITY.get(), MobCategory.CREATURE, 25, 1, 4));
        addEntityToBiome(Biomes.FLOWER_FOREST, new SpawnData(ELEPHANT_ENTITY_TYPE.get(), MobCategory.CREATURE, 1, 0 , 1));
        addEntityToBiome(Biomes.FLOWER_FOREST, new SpawnData(BUTTERFLY.get(), MobCategory.AMBIENT, 2, 0 , 1));
    }

    @ExpectPlatform
    public static void addEntityToBiome(ResourceKey<Biome> biome, SpawnData data) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        throw new AssertionError();
    }

}
