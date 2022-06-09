package tech.thatgravyboat.sprout.configs.worldgen;

import tech.thatgravyboat.sprout.config.PropertyType;
import tech.thatgravyboat.sprout.config.annotations.Category;
import tech.thatgravyboat.sprout.config.annotations.Property;

@Category("Sprouts")
public class Sprouts {

    @Property(type = PropertyType.BOOLEAN, description = "Weather you want sprouts or not.")
    public boolean enabled = true;

    @Property(type = PropertyType.INT, description = "The frequency that sprouts will spawn. Note: 32 is vanilla grass.")
    public int frequency = 32;
}
