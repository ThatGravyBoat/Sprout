package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.client.BounceBugBottleBlockEntityRenderer;
import com.toadstoolstudios.sprout.client.ShootParticle;
import com.toadstoolstudios.sprout.client.SnoozeParticle;
import com.toadstoolstudios.sprout.client.entity.BounceBugEntityRenderer;
import com.toadstoolstudios.sprout.client.entity.ElephantEntityRenderer;
import com.toadstoolstudios.sprout.items.BounceBugBottleItem;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.SproutItems;
import com.toadstoolstudios.sprout.registry.SproutParticles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SproutClient {

    public static void init() {
        renderBlockRenderers(SproutBlocks.PEANUT_PLANT_BLOCK, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.BOUNCE_BUG_BOTTLE, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.CATTIAL, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.TALL_DEAD_BUSH, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.WATER_LENTIL, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.SPROUTS, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.DUNE_GRASS, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.RED_SHELF_FUNGI, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.BROWN_SHELF_FUNGI, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.DUNE_GRASS, RenderLayer.getCutout());
        registerEntityRenderer(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntityRenderer::new);
        registerEntityRenderer(SproutEntities.BOUNCE_BUG_ENTITY, BounceBugEntityRenderer::new);
        registerBlockEntityRenderer(SproutBlocks.BOUNCE_BUG_JAR_BLOCK_ENTITY, (ctx) -> new BounceBugBottleBlockEntityRenderer());
        registerItemProperty(SproutItems.BOUNCE_BUG_JAR, new Identifier(Sprout.MODID, "bug_type"), (stack, world, entity, seed) -> BounceBugBottleItem.getTextureId(stack));
    }

    public static void initColors() {
        registerBlockColor((state, world, pos, tintIndex) -> {
            if (tintIndex == 1) return -1;
            if (world == null || pos == null) return GrassColors.getColor(0.5D, 1.0D);
            return BiomeColors.getGrassColor(world, pos);
        }, SproutBlocks.CATTIAL.get(), SproutBlocks.WATER_LENTIL.get(), SproutBlocks.SPROUTS.get());
        registerItemColor((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D),
                SproutItems.WATER_LENTIL.get(), SproutItems.SPROUTS.get());

    }

    public static void initParticleFactories() {
        registerParticleFactory(SproutParticles.SNOOZE, SnoozeParticle.Factory::new);
        registerParticleFactory(SproutParticles.SHOOTS, ShootParticle.Factory::new);
    }

    @ExpectPlatform
    public static void registerBlockColor(BlockColorProvider provider, Block... items) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemColor(ItemColorProvider provider, Item... items) {
        throw new AssertionError();
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
    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererFactory<T> blockEntityRenderer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemProperty(Supplier<Item> itemSupplier, Identifier name, UnclampedModelPredicateProvider provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerParticleFactory(Supplier<DefaultParticleType> particle, SpriteAwareFactory<DefaultParticleType> factory) {
        throw new AssertionError();
    }

    @FunctionalInterface
    @Environment(EnvType.CLIENT)
    public interface SpriteAwareFactory<T extends ParticleEffect> {
        ParticleFactory<T> create(SpriteProvider spriteProvider);
    }
}
