package tech.thatgravyboat.sprout.forge;

import com.mojang.datafixers.util.Pair;
import tech.thatgravyboat.sprout.SproutClient;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SproutClientImpl {

    public static final Map<Supplier<SimpleParticleType>, SproutClient.SpriteAwareFactory<SimpleParticleType>> PARTICLES = new HashMap<>();
    public static final List<Pair<ItemColor, Item[]>> ITEM_COLOR_PROVIDERS = new ArrayList<>();
    public static final List<Pair<BlockColor, Block[]>> BLOCK_COLOR_PROVIDERS = new ArrayList<>();

    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererProvider<T> blockEntityRenderer) {
        BlockEntityRenderers.register(blockEntity.get(), blockEntityRenderer);
    }

    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderType renderLayer) {
        //TODO Move to model json.
        ItemBlockRenderTypes.setRenderLayer(blockSupplier.get(), renderLayer);
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererProvider<T> renderer) {
        EntityRenderers.register(entityTypeSupplier.get(), renderer);
    }

    public static void registerParticleFactory(Supplier<SimpleParticleType> particle, SproutClient.SpriteAwareFactory<SimpleParticleType> factory) {
        PARTICLES.put(particle, factory);
    }

    public static void registerBlockColor(BlockColor provider, Block... items) {
        BLOCK_COLOR_PROVIDERS.add(new Pair<>(provider, items));
    }

    public static void registerItemColor(ItemColor provider, Item... items) {
        ITEM_COLOR_PROVIDERS.add(new Pair<>(provider, items));
    }
}
