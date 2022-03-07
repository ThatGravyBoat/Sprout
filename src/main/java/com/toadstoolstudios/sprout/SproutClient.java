package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.client.*;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class SproutClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(SproutBlocks.PEANUT_PLANT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SproutBlocks.GLOWFLY_JAR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SproutBlocks.GLASS_JAR, RenderLayer.getCutout());
        EntityRendererRegistry.register(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntityRenderer::new);
        EntityRendererRegistry.register(SproutEntities.MAMMOTH_ENTITY_TYPE, MammothEntityRenderer::new);
        EntityRendererRegistry.register(SproutEntities.GLOWFLY_ENTITY_TYPE, GlowflyEntityRenderer::new);
        BlockEntityRendererRegistry.register(SproutBlocks.GLOWFLY_JAR_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, (ctx) -> new GlowflyBlockEntityRenderer());
    }
}
