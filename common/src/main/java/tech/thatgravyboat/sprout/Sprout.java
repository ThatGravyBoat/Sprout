package tech.thatgravyboat.sprout;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import software.bernie.geckolib3.GeckoLib;
import tech.thatgravyboat.sprout.common.configs.SproutConfig;
import tech.thatgravyboat.sprout.common.registry.*;

public class Sprout {

	public static final Configurator CONFIGURATOR = new Configurator(true);

	public static final String MODID = "sprout";

	public static void init() {
		CONFIGURATOR.registerConfig(SproutConfig.class);
		GeckoLib.initialize();
		SproutEntities.ENTITIES.init();
		SproutBlocks.registerBlocks();
		SproutItems.registerItems();
		SproutSounds.SOUNDS.init();
		SproutParticles.PARTICLES.init();
		SproutFeatures.FEATURES.init();
	}
}
