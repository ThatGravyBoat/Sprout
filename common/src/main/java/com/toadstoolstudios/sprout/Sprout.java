package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.config.ConfigLoader;
import com.toadstoolstudios.sprout.configs.SproutConfig;
import com.toadstoolstudios.sprout.registry.*;
import software.bernie.geckolib3.GeckoLib;


public class Sprout {

	public static final SproutConfig CONFIG = new SproutConfig();
	public static final String MODID = "sprout";

	public static void init() {
		GeckoLib.initialize();
		SproutEntities.registerEntities();
		SproutItems.registerItems();
		SproutBlocks.registerBlocks();
		SproutSounds.registerSounds();
		SproutParticles.registerParticles();
		SproutFeatures.register();
		ConfigLoader.registerConfig(CONFIG);
	}
}
