package tech.thatgravyboat.sprout.forge;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.SproutClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid=Sprout.MODID, value=Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class SproutForgeClient {

    public static void clientSetup(FMLClientSetupEvent event) {
        SproutClient.init();
    }

    @SubscribeEvent
    public static void particleFactoryRegistry(ParticleFactoryRegisterEvent event) {
        SproutClient.initParticleFactories();
        SproutClientImpl.PARTICLES.forEach((particle, factory) ->
                Minecraft.getInstance().particleEngine.register(particle.get(), factory::create));
    }

    @SubscribeEvent
    public static void onColors(ColorHandlerEvent.Item event) {
        SproutClient.initColors();
        SproutClientImpl.ITEM_COLOR_PROVIDERS.forEach(colors -> event.getItemColors().register(colors.getFirst(), colors.getSecond()));
        SproutClientImpl.BLOCK_COLOR_PROVIDERS.forEach(colors -> event.getBlockColors().register(colors.getFirst(), colors.getSecond()));
    }
}
