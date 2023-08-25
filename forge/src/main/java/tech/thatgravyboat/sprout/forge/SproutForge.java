package tech.thatgravyboat.sprout.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.common.entities.ButterFly;
import tech.thatgravyboat.sprout.common.entities.ElephantEntity;
import tech.thatgravyboat.sprout.common.registry.SproutConfiguredFeatures;
import tech.thatgravyboat.sprout.common.registry.SproutEntities;
import tech.thatgravyboat.sprout.common.registry.SproutItems;

@Mod(Sprout.MODID)
public class SproutForge {

    public SproutForge() {
        Sprout.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
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
        event.put(SproutEntities.BUTTERFLY.get(), ButterFly.createAttributes().build());
    }
}
