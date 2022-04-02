package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.blocks.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class SproutBlocks {
    public static final Supplier<Block> PEANUT_PLANT_BLOCK = registerBlock( "peanut_plant", () -> new PeanutCrop(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision()));
    public static final Supplier<Block> GLASS_JAR = registerBlock( "glass_jar", () -> new GlassJar(AbstractBlock.Settings.of(Material.GLASS).solidBlock((state, world, pos) -> false)));
    public static final Supplier<Block> GLOWFLY_JAR = registerBlock( "glowfly_jar", () -> new GlowflyJar(AbstractBlock.Settings.of(Material.GLASS).luminance(value -> 15).solidBlock((state, world, pos) -> false)));
    public static final Supplier<Block> BASKET_BLOCK = registerBlock( "basket_block", () -> new BasketBlock(AbstractBlock.Settings.of(Material.WOOD).solidBlock((state, world, pos) -> false)));
    public static Supplier<BlockEntityType<GlowflyJarBlockEntity>> GLOWFLY_JAR_BLOCK_ENTITY = registerBlockEntity("glowfy_jar_block_entity", () -> createBlockEntityType(GlowflyJarBlockEntity::new, GLOWFLY_JAR.get()));
    public static Supplier<BlockEntityType<BasketBlockEntity>> BASKET_BLOCK_ENTITY = registerBlockEntity("basket_block_entity", () -> createBlockEntityType(BasketBlockEntity::new, BASKET_BLOCK.get()));

    public static void registerBlocks() {
        //initialize class
    }

    @ExpectPlatform
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityFactory<T> factory, Block ... block) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }
}
