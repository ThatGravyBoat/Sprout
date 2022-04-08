package com.toadstoolstudios.sprout.forge;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.registry.SpawnData;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.SproutFeatures;
import com.toadstoolstudios.sprout.registry.forge.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod(Sprout.MODID)
public class SproutForge {

    private static boolean addSpawns = false;

    public SproutForge() {
        Sprout.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SproutEntitiesImpl.ENTITY_TYPES.register(bus);
        SproutBlocksImpl.BLOCKS.register(bus);
        SproutBlocksImpl.BLOCK_ENTITIES.register(bus);
        SproutItemsImpl.ITEMS.register(bus);
        SproutSoundsImpl.SOUNDS.register(bus);
        SproutParticlesImpl.PARTICLES.register(bus);
        bus.addListener(SproutForge::commonSetup);
        bus.addListener(SproutForgeClient::clientSetup);
        bus.addListener(SproutForge::onComplete);
        bus.addListener(SproutForge::entityAttributeStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        SproutFeatures.registerFeatures();
    }

    public static void onComplete(FMLLoadCompleteEvent event) {
        SproutEntities.addSpawnRules();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addSpawns(BiomeLoadingEvent event) {
        if (!addSpawns) {
            SproutEntities.addSpawns();
            addSpawns = true;
        }
        Identifier name = event.getName();
        if(name != null) {

            List<SpawnData> spawns = SproutEntitiesImpl.ENTITY_SPAWNS.get(name);

            for (SpawnData spawnData : spawns) {
                event.getSpawns().spawn(spawnData.group(), new SpawnSettings.SpawnEntry(spawnData.entityType(), spawnData.weight(), spawnData.min(), spawnData.max()));
            }

            List<SproutFeaturesImpl.FeaturePlacementData> features = SproutFeaturesImpl.FEATURES.get(name);

            for (SproutFeaturesImpl.FeaturePlacementData feature : features) {
                event.getGeneration().getFeatures(feature.feature()).add(feature.entry());
            }
        }
    }

    private static void entityAttributeStuff(EntityAttributeCreationEvent event) {
        event.put(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), ElephantEntity.createMobAttributes().build());
        event.put(SproutEntities.BOUNCE_BUG_ENTITY.get(), BounceBugEntity.createMobAttributes().build());
    }
}
