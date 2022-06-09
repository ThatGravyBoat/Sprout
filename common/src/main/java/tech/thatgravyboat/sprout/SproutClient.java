package tech.thatgravyboat.sprout;

import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.client.BounceBugBottleBlockEntityRenderer;
import tech.thatgravyboat.sprout.client.ShootParticle;
import tech.thatgravyboat.sprout.client.SnoozeParticle;
import tech.thatgravyboat.sprout.client.entity.BounceBugEntityRenderer;
import tech.thatgravyboat.sprout.client.entity.ElephantEntityModel;
import tech.thatgravyboat.sprout.client.entity.MobEntityRenderer;
import tech.thatgravyboat.sprout.items.BounceBugBottleItem;
import tech.thatgravyboat.sprout.registry.SproutBlocks;
import tech.thatgravyboat.sprout.registry.SproutEntities;
import tech.thatgravyboat.sprout.registry.SproutItems;
import tech.thatgravyboat.sprout.registry.SproutParticles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SproutClient {

    public static void init() {
        renderBlockRenderers(SproutBlocks.PEANUT_PLANT_BLOCK, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.BOUNCE_BUG_BOTTLE, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.CATTIAL, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.TALL_DEAD_BUSH, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.WATER_LENTIL, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.SPROUTS, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.DUNE_GRASS, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.RED_SHELF_FUNGI, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.BROWN_SHELF_FUNGI, RenderType.cutout());
        renderBlockRenderers(SproutBlocks.DUNE_GRASS, RenderType.cutout());
        registerEntityRenderer(SproutEntities.ELEPHANT_ENTITY_TYPE, ctx -> new MobEntityRenderer<>(ctx, new ElephantEntityModel()));
        registerEntityRenderer(SproutEntities.BOUNCE_BUG_ENTITY, BounceBugEntityRenderer::new);
        registerBlockEntityRenderer(SproutBlocks.BOUNCE_BUG_JAR_BLOCK_ENTITY, (ctx) -> new BounceBugBottleBlockEntityRenderer());
        registerItemProperty(SproutItems.BOUNCE_BUG_JAR, new ResourceLocation(Sprout.MODID, "bug_type"), (stack, world, entity, seed) -> BounceBugBottleItem.getTextureId(stack));
    }

    public static void initColors() {
        registerBlockColor((state, world, pos, tintIndex) -> {
            if (tintIndex == 1) return -1;
            if (world == null || pos == null) return GrassColor.get(0.5D, 1.0D);
            return BiomeColors.getAverageGrassColor(world, pos);
        }, SproutBlocks.CATTIAL.get(), SproutBlocks.WATER_LENTIL.get(), SproutBlocks.SPROUTS.get());
        registerItemColor((stack, tintIndex) -> GrassColor.get(0.5D, 1.0D), SproutItems.WATER_LENTIL.get(), SproutItems.SPROUTS.get());
    }

    public static void initParticleFactories() {
        registerParticleFactory(SproutParticles.SNOOZE, SnoozeParticle.Factory::new);
        registerParticleFactory(SproutParticles.SHOOTS, ShootParticle.Factory::new);
    }

    @ExpectPlatform
    public static void registerBlockColor(BlockColor provider, Block... items) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemColor(ItemColor provider, Item... items) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void renderBlockRenderers(Supplier<Block> blockSupplier, RenderType renderLayer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererProvider<T> renderer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntity, BlockEntityRendererProvider<T> blockEntityRenderer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemProperty(Supplier<Item> itemSupplier, ResourceLocation name, ClampedItemPropertyFunction provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerParticleFactory(Supplier<SimpleParticleType> particle, SpriteAwareFactory<SimpleParticleType> factory) {
        throw new AssertionError();
    }

    @FunctionalInterface
    @Environment(EnvType.CLIENT)
    public interface SpriteAwareFactory<T extends ParticleOptions> {
        @NotNull ParticleProvider<T> create(SpriteSet spriteProvider);
    }
}
