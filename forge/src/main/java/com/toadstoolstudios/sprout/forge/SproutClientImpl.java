package com.toadstoolstudios.sprout.forge;

import com.toadstoolstudios.sprout.client.GlowflyBlockEntityRenderer;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
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
    }

    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererFactory<T> renderer) {
    }
}