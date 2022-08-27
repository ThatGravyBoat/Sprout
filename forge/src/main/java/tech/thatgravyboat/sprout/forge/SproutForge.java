package tech.thatgravyboat.sprout.forge;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.config.forge.ConfigLoaderImpl;
import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.common.entities.ElephantEntity;
import tech.thatgravyboat.sprout.common.registry.SproutConfiguredFeatures;
import tech.thatgravyboat.sprout.common.registry.SproutEntities;
import tech.thatgravyboat.sprout.common.registry.SproutItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.sprout.common.registry.forge.*;

@Mod(Sprout.MODID)
public class SproutForge {

    public SproutForge() {
        Sprout.init();

        ConfigLoaderImpl.initialize();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SproutEntitiesImpl.ENTITY_TYPES.register(bus);
        SproutBlocksImpl.BLOCKS.register(bus);
        SproutBlocksImpl.BLOCK_ENTITIES.register(bus);
        SproutItemsImpl.ITEMS.register(bus);
        SproutSoundsImpl.SOUNDS.register(bus);
        SproutParticlesImpl.PARTICLES.register(bus);
        SproutFeaturesImpl.FEATURES.register(bus);
        bus.addListener(SproutForge::commonSetup);
        bus.addListener(SproutForgeClient::clientSetup);
        bus.addListener(SproutForge::onComplete);
        bus.addListener(SproutForge::entityAttributeStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        SproutConfiguredFeatures.registerFeatures();
    }

    public static void onComplete(FMLLoadCompleteEvent event) {
        SproutEntities.addSpawnRules();
        SproutItems.onComplete();
    }

    private static void entityAttributeStuff(EntityAttributeCreationEvent event) {
        event.put(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), ElephantEntity.createMobAttributes().build());
        event.put(SproutEntities.BOUNCE_BUG_ENTITY.get(), BounceBugEntity.createMobAttributes().build());
    }
}
