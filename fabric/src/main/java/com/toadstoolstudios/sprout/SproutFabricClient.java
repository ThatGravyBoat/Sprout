package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.registry.fabric.SproutParticlesImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlas, registry) ->
                SproutParticlesImpl.TEXTURES.stream()
                        .map(id -> new Identifier(MODID, "particle/" + id))
                        .forEachOrdered(registry::register)
        );
        SproutClient.init();
        SproutClient.initParticleFactories();
    }
}
