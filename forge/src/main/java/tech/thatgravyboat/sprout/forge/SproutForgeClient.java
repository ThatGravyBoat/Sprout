package tech.thatgravyboat.sprout.forge;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.SproutClient;
import tech.thatgravyboat.sprout.common.configs.SproutConfig;


@Mod.EventBusSubscriber(modid=Sprout.MODID, value=Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SproutForgeClient {

    public static void clientSetup(FMLClientSetupEvent event) {
        SproutClient.init();

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
                    ResourcefulConfig config = Sprout.CONFIGURATOR.getConfig(SproutConfig.class);
                    if (config == null) {
                        return null;
                    }
                    return new ConfigScreen(null, config);
                })
        );
    }

    @SubscribeEvent
    public static void particleFactoryRegistry(RegisterParticleProvidersEvent event) {
        SproutClient.initParticleFactories();
        SproutClientImpl.PARTICLES.forEach((particle, factory) -> event.register(particle.get(), factory::create));
    }

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        SproutClient.initItemColors();
        SproutClientImpl.ITEM_COLOR_PROVIDERS.forEach(colors -> event.register(colors.getFirst(), colors.getSecond()));
    }

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        SproutClient.initBlockColors();
        SproutClientImpl.BLOCK_COLOR_PROVIDERS.forEach(colors -> event.register(colors.getFirst(), colors.getSecond()));
    }
}
