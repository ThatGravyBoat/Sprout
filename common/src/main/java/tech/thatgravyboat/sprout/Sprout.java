package tech.thatgravyboat.sprout;

import tech.thatgravyboat.sprout.config.ConfigLoader;
import tech.thatgravyboat.sprout.configs.SproutConfig;
import software.bernie.geckolib3.GeckoLib;
import tech.thatgravyboat.sprout.registry.*;


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
