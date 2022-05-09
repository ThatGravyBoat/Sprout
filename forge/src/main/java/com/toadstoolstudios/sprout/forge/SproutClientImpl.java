package com.toadstoolstudios.sprout.forge;

import com.toadstoolstudios.sprout.SproutClient;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SproutClientImpl {

    public static final Map<Supplier<DefaultParticleType>, SproutClient.SpriteAwareFactory<DefaultParticleType>> PARTICLES = new HashMap<>();
    public static final List<Pair<ItemColorProvider, Item[]>> ITEM_COLOR_PROVIDERS = new ArrayList<>();
    public static final List<Pair<BlockColorProvider, Block[]>> BLOCK_COLOR_PROVIDERS = new ArrayList<>();

    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererFactory<T> blockEntityRenderer) {
        BlockEntityRendererFactories.register(blockEntity.get(), blockEntityRenderer);
    }

    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderLayer renderLayer) {
        RenderLayers.setRenderLayer(blockSupplier.get(), renderLayer);
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererFactory<T> renderer) {
        EntityRenderers.register(entityTypeSupplier.get(), renderer);
    }

    public static void registerItemProperty(Supplier<Item> itemSupplier, Identifier name, UnclampedModelPredicateProvider provider) {
        ModelPredicateProviderRegistry.register(itemSupplier.get(), name, provider);
    }

    public static void registerParticleFactory(Supplier<DefaultParticleType> particle, SproutClient.SpriteAwareFactory<DefaultParticleType> factory) {
        PARTICLES.put(particle, factory);
    }

    public static void registerBlockColor(BlockColorProvider provider, Block... items) {
        BLOCK_COLOR_PROVIDERS.add(new Pair<>(provider, items));
    }

    public static void registerItemColor(ItemColorProvider provider, Item... items) {
        ITEM_COLOR_PROVIDERS.add(new Pair<>(provider, items));
    }
}
