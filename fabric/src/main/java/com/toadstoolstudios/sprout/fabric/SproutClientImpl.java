package com.toadstoolstudios.sprout.fabric;

import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

public class SproutClientImpl {

    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRenderer<T> blockEntityRenderer) {
        BlockEntityRendererRegistry.register(blockEntity.get(), (ctx) -> blockEntityRenderer);
    }

    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), renderLayer);
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererFactory<T> renderer) {
        EntityRendererRegistry.register(entityTypeSupplier.get(), renderer);
    }
}
