package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.client.BounceBugEntityRenderer;
import com.toadstoolstudios.sprout.client.ElephantEntityRenderer;
import com.toadstoolstudios.sprout.client.BounceBugBottleBlockEntityRenderer;
import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.BounceBugVariant;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.SproutItems;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SproutClient {

    public static void init() {
        renderBlockRenderers(SproutBlocks.PEANUT_PLANT_BLOCK, RenderLayer.getCutout());
        renderBlockRenderers(SproutBlocks.BOUNCE_BUG_BOTTLE, RenderLayer.getCutout());
        registerEntityRenderer(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntityRenderer::new);
        registerEntityRenderer(SproutEntities.BOUNCE_BUG_ENTITY, BounceBugEntityRenderer::new);
        registerBlockEntityRenderer(SproutBlocks.BOUNCE_BUG_JAR_BLOCK_ENTITY, (ctx) -> new BounceBugBottleBlockEntityRenderer());
        registerItemProperty(SproutItems.BOUNCE_BUG_JAR, new Identifier(Sprout.MODID, "bug_type"), (stack, world, entity, seed) -> stack.hasNbt() ? (float) BounceBugVariant.valueOf(stack.getNbt().getCompound("bug").getString("bugType")).ordinal() / BounceBugVariant.values().length : 0);
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
}
