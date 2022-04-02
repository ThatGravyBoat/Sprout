package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.entities.MammothEntity;
import com.toadstoolstudios.sprout.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;


public class Sprout {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "sprout";
	public static final ItemGroup SPROUT_TAB = FabricItemGroupBuilder.build(new Identifier(MODID, "item_group"), () -> new ItemStack(Items.JUNGLE_SAPLING));

	public static void init() {
		SproutEntities.registerEntities();
		SproutEntities.addSpawnRules();
		SproutItems.registerItems();
		SproutBlocks.registerBlocks();
		SproutSounds.registerSounds();
		SproutFeatures.registerFeatures();
	}
}
