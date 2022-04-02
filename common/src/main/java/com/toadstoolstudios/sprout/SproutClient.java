package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.client.ElephantEntityRenderer;
import com.toadstoolstudios.sprout.client.GlowflyBlockEntityRenderer;
import com.toadstoolstudios.sprout.client.GlowflyEntityRenderer;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SproutClient {

    public static void init() {
        renderBlockRenderers(SproutBlocks.PEANUT_PLANT_BLOCK, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.GLOWFLY_JAR, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.GLASS_JAR, RenderLayer.getCutout());
        registerEntityRenderer(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntityRenderer::new);
        registerEntityRenderer(SproutEntities.GLOWFLY_ENTITY_TYPE, GlowflyEntityRenderer::new);
        registerBlockEntityRenderer(SproutBlocks.GLOWFLY_JAR_BLOCK_ENTITY, new GlowflyBlockEntityRenderer());
    }

    @ExpectPlatform
    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererFactory<T> renderer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRenderer<T> blockEntityRenderer) {
        throw new AssertionError();
    }
}
