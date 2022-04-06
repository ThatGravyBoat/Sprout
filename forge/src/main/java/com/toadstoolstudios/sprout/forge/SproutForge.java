package com.toadstoolstudios.sprout.forge;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.SproutClient;
import com.toadstoolstudios.sprout.blocks.PeanutCrop;
import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.registry.SpawnData;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.forge.SproutBlocksImpl;
import com.toadstoolstudios.sprout.registry.forge.SproutEntitiesImpl;
import com.toadstoolstudios.sprout.registry.forge.SproutItemsImpl;
import com.toadstoolstudios.sprout.registry.forge.SproutSoundsImpl;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod(Sprout.MODID)
public class SproutForge {
    public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> peanutPatch;
    public static RegistryEntry<PlacedFeature> placedFeature;

    public SproutForge() {
        Sprout.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SproutEntitiesImpl.ENTITY_TYPES.register(bus);
        SproutBlocksImpl.BLOCKS.register(bus);
        SproutBlocksImpl.BLOCK_ENTITIES.register(bus);
        SproutItemsImpl.ITEMS.register(bus);
        SproutSoundsImpl.SOUNDS.register(bus);
        bus.addListener(SproutForge::commonSetup);
        bus.addListener(SproutForge::clientSetup);
        bus.addListener(SproutForge::onComplete);
        bus.addListener(SproutForge::entityAttributeStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        peanutPatch = ConfiguredFeatures.register("patch_peanut", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(SproutBlocks.PEANUT_PLANT_BLOCK.get().getDefaultState().with(PeanutCrop.AGE, 2))), List.of(Blocks.GRASS_BLOCK)));
        placedFeature = PlacedFeatures.register("peanut_patch_common", peanutPatch, RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        SproutClient.init();
    }

    public static void onComplete(FMLLoadCompleteEvent event) {
        SproutEntities.addSpawnRules();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addSpawns(BiomeLoadingEvent event) {
        if(event.getName() != null) {
            String biome = event.getName().getPath();

            if (biome.equals("crimson_forest") || biome.equals("warped_forest")) {
                event.getSpawns().spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(SproutEntities.BOUNCE_BUG_ENTITY.get(), 45, 1, 4));
            }

            if (biome.equals("meadow")) {
                event.getSpawns().spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), 23, 0, 1));
                event.getGeneration().getFeatures(GenerationStep.Feature.VEGETAL_DECORATION).add(placedFeature);
            }

        }
    }

    private static void entityAttributeStuff(EntityAttributeCreationEvent event) {
        event.put(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), ElephantEntity.createMobAttributes().build());
        event.put(SproutEntities.BOUNCE_BUG_ENTITY.get(), BounceBugEntity.createMobAttributes().build());
    }
}
