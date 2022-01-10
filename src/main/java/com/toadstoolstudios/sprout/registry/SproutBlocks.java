package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.blocks.PeanutCrop;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.toadstoolstudios.sprout.Sprout.MOD_ID;

public class SproutBlocks {
    public static final Block PEANUT_PLANT_BLOCK = new PeanutCrop(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision());

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "peanut_plant"), PEANUT_PLANT_BLOCK);
    }
}
