package tech.thatgravyboat.sprout.configs;

import tech.thatgravyboat.sprout.config.PropertyType;
import tech.thatgravyboat.sprout.config.annotations.Category;
import tech.thatgravyboat.sprout.config.annotations.Property;
import tech.thatgravyboat.sprout.configs.worldgen.Sprouts;

@Category("World Generation")
public class WorldGenConfig {

    @Property(type = PropertyType.CATEGORY, description = "Sprouts Generation Configurations")
    public Sprouts sprouts = new Sprouts();

}
