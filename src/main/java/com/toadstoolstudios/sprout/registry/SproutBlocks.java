package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.blocks.GlassJar;
import com.toadstoolstudios.sprout.blocks.GlowflyJar;
import com.toadstoolstudios.sprout.blocks.GlowflyJarBlockEntity;
import com.toadstoolstudios.sprout.blocks.PeanutCrop;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutBlocks {
    public static final Block PEANUT_PLANT_BLOCK = new PeanutCrop(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision());
    public static final Block GLASS_JAR = new GlassJar(FabricBlockSettings.of(Material.GLASS).solidBlock((state, world, pos) -> false));
    public static final Block GLOWFLY_JAR = new GlowflyJar(FabricBlockSettings.of(Material.GLASS).luminance(15).solidBlock((state, world, pos) -> false));
    public static BlockEntityType<GlowflyJarBlockEntity> GLOWFLY_JAR_BLOCK_ENTITY_BLOCK_ENTITY_TYPE;


    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(MODID, "peanut_plant"), PEANUT_PLANT_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "glass_jar"), GLASS_JAR);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "glowfly_jar"), GLOWFLY_JAR);
        GLOWFLY_JAR_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "glowfly_jar_block_entity"), FabricBlockEntityTypeBuilder.create(GlowflyJarBlockEntity::new, GLOWFLY_JAR).build(null));
    }
}
