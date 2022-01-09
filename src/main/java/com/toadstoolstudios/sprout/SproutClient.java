package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.entities.ElephantEntityRenderer;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SproutClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntityRenderer::new);
    }
}
