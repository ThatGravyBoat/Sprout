package com.toadstoolstudios.sprout.fabric;

import com.toadstoolstudios.sprout.SproutClient;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class SproutClientImpl {

    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererFactory<T> blockEntityRenderer) {
        BlockEntityRendererRegistry.register(blockEntity.get(), blockEntityRenderer);
    }

    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), renderLayer);
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererFactory<T> renderer) {
        EntityRendererRegistry.register(entityTypeSupplier.get(), renderer);
    }

    public static void registerItemProperty(Supplier<Item> itemSupplier, Identifier name, UnclampedModelPredicateProvider provider) {
        ModelPredicateProviderRegistry.register(itemSupplier.get(), name, provider);
    }

    public static void registerParticleFactory(Supplier<DefaultParticleType> particle, SproutClient.SpriteAwareFactory<DefaultParticleType> factory) {
        ParticleFactoryRegistry.getInstance().register(particle.get(), factory::create);
    }

    public static void registerBlockColor(BlockColorProvider provider, Block... items) {
        ColorProviderRegistry.BLOCK.register(provider, items);
    }

    public static void registerItemColor(ItemColorProvider provider, Item... items) {
        ColorProviderRegistry.ITEM.register(provider, items);
    }
}
