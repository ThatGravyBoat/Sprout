package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import static com.toadstoolstudios.sprout.registry.SproutBlocks.registerBlocks;
import static com.toadstoolstudios.sprout.registry.SproutEntities.addSpawnRules;
import static com.toadstoolstudios.sprout.registry.SproutEntities.registerEntities;
import static com.toadstoolstudios.sprout.registry.SproutFeatures.registerFeatures;
import static com.toadstoolstudios.sprout.registry.SproutItems.registerItems;
import static com.toadstoolstudios.sprout.registry.SproutSounds.registerSounds;

public class Sprout implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "sprout";
	public static final ItemGroup SPROUT_TAB = FabricItemGroupBuilder.build(new Identifier(MODID, "item_group"), () -> new ItemStack(Items.JUNGLE_SAPLING));
	@Override
	public void onInitialize() {
		registerEntities();
		addSpawnRules();
		registerItems();
		registerBlocks();
		registerSounds();
		registerFeatures();
		FabricDefaultAttributeRegistry.register(SproutEntities.ELEPHANT_ENTITY_TYPE, ElephantEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(SproutEntities.GLOWFLY_ENTITY_TYPE, GlowflyEntity.createGlowflyAttributes());
	}
}
