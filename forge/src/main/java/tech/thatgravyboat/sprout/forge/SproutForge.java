package tech.thatgravyboat.sprout.forge;

import com.mojang.serialization.Codec;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.config.forge.ConfigLoaderImpl;
import tech.thatgravyboat.sprout.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.entities.ElephantEntity;
import tech.thatgravyboat.sprout.registry.SproutConfiguredFeatures;
import tech.thatgravyboat.sprout.registry.SproutEntities;
import tech.thatgravyboat.sprout.registry.SproutItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.sprout.registry.forge.*;

@Mod(Sprout.MODID)
public class SproutForge {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Sprout.MODID);
    public static final RegistryObject<Codec<SpawnsBiomeModifier>> SPAWN_MODIFIER = BIOME_MODIFIERS.register("spawns", SpawnsBiomeModifier::makeCodec);
    public static final RegistryObject<Codec<FeatureBiomeModifier>> FEATURE_MODIFIER = BIOME_MODIFIERS.register("features", FeatureBiomeModifier::makeCodec);

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
        BIOME_MODIFIERS.register(bus);
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
