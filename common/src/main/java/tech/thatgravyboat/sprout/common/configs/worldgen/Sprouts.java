package tech.thatgravyboat.sprout.common.configs.worldgen;

import tech.thatgravyboat.sprout.common.config.annotations.Category;
import tech.thatgravyboat.sprout.common.config.annotations.Property;
import tech.thatgravyboat.sprout.common.config.PropertyType;

@Category("Sprouts")
public class Sprouts {

    @Property(type = PropertyType.BOOLEAN, description = "Weather you want sprouts or not.")
    public boolean enabled = true;

    @Property(type = PropertyType.INT, description = "The frequency that sprouts will spawn. Note: 32 is vanilla grass.")
    public int frequency = 32;
}
