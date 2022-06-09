package tech.thatgravyboat.sprout.fabric;

import tech.thatgravyboat.sprout.SproutClient;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class SproutClientImpl {

    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererProvider<T> blockEntityRenderer) {
        BlockEntityRendererRegistry.register(blockEntity.get(), blockEntityRenderer);
    }

    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderType renderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(blockSupplier.get(), renderLayer);
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererProvider<T> renderer) {
        EntityRendererRegistry.register(entityTypeSupplier.get(), renderer);
    }

    public static void registerItemProperty(Supplier<Item> itemSupplier, ResourceLocation name, ClampedItemPropertyFunction provider) {
        ItemProperties.register(itemSupplier.get(), name, provider);
    }

    public static void registerParticleFactory(Supplier<SimpleParticleType> particle, SproutClient.SpriteAwareFactory<SimpleParticleType> factory) {
        ParticleFactoryRegistry.getInstance().register(particle.get(), factory::create);
    }

    public static void registerBlockColor(BlockColor provider, Block... items) {
        ColorProviderRegistry.BLOCK.register(provider, items);
    }

    public static void registerItemColor(ItemColor provider, Item... items) {
        ColorProviderRegistry.ITEM.register(provider, items);
    }
}
