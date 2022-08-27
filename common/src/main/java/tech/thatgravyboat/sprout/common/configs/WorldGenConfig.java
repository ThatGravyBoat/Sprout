package tech.thatgravyboat.sprout.common.configs;

import tech.thatgravyboat.sprout.common.config.PropertyType;
import tech.thatgravyboat.sprout.common.config.annotations.Category;
import tech.thatgravyboat.sprout.common.config.annotations.Property;
import tech.thatgravyboat.sprout.common.configs.worldgen.Sprouts;

@Category("World Generation")
public class WorldGenConfig {

    @Property(type = PropertyType.CATEGORY, description = "Sprouts Generation Configurations")
    public Sprouts sprouts = new Sprouts();

}
