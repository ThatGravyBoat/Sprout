package tech.thatgravyboat.sprout.common.configs;

import tech.thatgravyboat.sprout.common.config.PropertyType;
import tech.thatgravyboat.sprout.common.config.annotations.Config;
import tech.thatgravyboat.sprout.common.config.annotations.Property;

@Config("sprout")
public class SproutConfig {

    @Property(type = PropertyType.CATEGORY, description = "World Generation Configurations.")
    public WorldGenConfig worldGen = new WorldGenConfig();
}
