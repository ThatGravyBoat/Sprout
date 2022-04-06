package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.registry.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Supplier;


public class Sprout {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "sprout";
	public static final ItemGroup SPROUT_TAB = registerItemGroup(new Identifier(MODID, "itemgroup"), () -> new ItemStack(SproutItems.PEANUT.get()));

	public static void init() {
		GeckoLib.initialize();
		SproutEntities.registerEntities();
		SproutItems.registerItems();
		SproutBlocks.registerBlocks();
		SproutSounds.registerSounds();
	}

	@ExpectPlatform
	public static ItemGroup registerItemGroup(Identifier id, Supplier<ItemStack> icon) {
		throw new AssertionError();
	}
}
