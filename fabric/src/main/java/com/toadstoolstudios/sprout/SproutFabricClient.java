package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.client.ElephantEntityRenderer;
import com.toadstoolstudios.sprout.client.GlowflyBlockEntityRenderer;
import com.toadstoolstudios.sprout.client.GlowflyEntityRenderer;
import com.toadstoolstudios.sprout.client.MammothEntityRenderer;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class SproutFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SproutClient.init();
    }
}
