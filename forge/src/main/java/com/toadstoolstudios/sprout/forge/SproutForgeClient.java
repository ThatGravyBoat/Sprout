package com.toadstoolstudios.sprout.forge;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.SproutClient;
import net.minecraft.client.MinecraftClient;
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
                MinecraftClient.getInstance().particleManager.registerFactory(particle.get(), factory::create));
    }

    @SubscribeEvent
    public static void onColors(ColorHandlerEvent.Item event) {
        SproutClient.initColors();
        SproutClientImpl.ITEM_COLOR_PROVIDERS.forEach(colors -> event.getItemColors().register(colors.getLeft(), colors.getRight()));
        SproutClientImpl.BLOCK_COLOR_PROVIDERS.forEach(colors -> event.getBlockColors().registerColorProvider(colors.getLeft(), colors.getRight()));
    }
}
