package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.entities.ElephantEntityRenderer;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class SproutClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(SproutBlocks.PEANUT_PLANT_BLOCK, RenderLayer.getCutout());
        EntityRendererRegistry.register(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntityRenderer::new);
    }
}
