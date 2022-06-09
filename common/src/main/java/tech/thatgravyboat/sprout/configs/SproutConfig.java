package tech.thatgravyboat.sprout.configs;

import tech.thatgravyboat.sprout.config.PropertyType;
import tech.thatgravyboat.sprout.config.annotations.Config;
import tech.thatgravyboat.sprout.config.annotations.Property;

@Config("sprout")
public class SproutConfig {

    @Property(type = PropertyType.CATEGORY, description = "World Generation Configurations.")
    public WorldGenConfig worldGen = new WorldGenConfig();
}
