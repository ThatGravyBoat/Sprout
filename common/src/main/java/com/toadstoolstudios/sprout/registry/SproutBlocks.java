package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.blocks.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutBlocks {
    public static final Supplier<Block> PEANUT_PLANT_BLOCK = registerBlock( "peanut_plant", () -> new PeanutCrop(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision()));
    public static final Supplier<Block> GLASS_JAR = registerBlock( "glass_jar", () -> new GlassJar(AbstractBlock.Settings.of(Material.GLASS).solidBlock((state, world, pos) -> false)));
    public static final Supplier<Block> GLOWFLY_JAR = registerBlock( "glowfly_jar", () -> new GlowflyJar(AbstractBlock.Settings.of(Material.GLASS).luminance(value -> 15).solidBlock((state, world, pos) -> false)));
    public static final Supplier<Block> BASKET_BLOCK = registerBlock( "basket_block", () -> new BasketBlock(AbstractBlock.Settings.of(Material.WOOD).solidBlock((state, world, pos) -> false)));
    public static BlockEntityType<GlowflyJarBlockEntity> GLOWFLY_JAR_BLOCK_ENTITY_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<BasketBlockEntity> BASKET_BLOCK_ENTITY;

    @NotNull
    @ExpectPlatform
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        return null;
    }

    public static void registerBlocks() {
        //initialize class
    }
}
