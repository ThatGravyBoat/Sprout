package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.registry.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Supplier;


public class Sprout {

	public static final String MODID = "sprout";

	public static void init() {
		GeckoLib.initialize();
		SproutEntities.registerEntities();
		SproutItems.registerItems();
		SproutBlocks.registerBlocks();
		SproutSounds.registerSounds();
		SproutParticles.registerParticles();
		SproutFeatures.register();
	}
}
