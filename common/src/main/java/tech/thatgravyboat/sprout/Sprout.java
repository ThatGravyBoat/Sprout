package tech.thatgravyboat.sprout;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import software.bernie.geckolib3.GeckoLib;
import tech.thatgravyboat.sprout.common.config.ConfigLoader;
import tech.thatgravyboat.sprout.common.configs.SproutConfig;
import tech.thatgravyboat.sprout.common.registry.*;


public class Sprout {

	public static final SproutConfig CONFIG = new SproutConfig();
	public static final String MODID = "sprout";

	public static void init() {
		GeckoLib.initialize();
		SproutEntities.registerEntities();
		SproutBlocks.registerBlocks();
		SproutItems.registerItems();
		SproutSounds.registerSounds();
		SproutParticles.registerParticles();
		SproutFeatures.register();
		if ("fabric".equals(ArchitecturyTarget.getCurrentTarget())) {
			ConfigLoader.registerConfig(CONFIG);
		}
	}
}
