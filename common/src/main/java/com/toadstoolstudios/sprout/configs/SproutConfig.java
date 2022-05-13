package com.toadstoolstudios.sprout.configs;

import com.toadstoolstudios.sprout.config.PropertyType;
import com.toadstoolstudios.sprout.config.annotations.Config;
import com.toadstoolstudios.sprout.config.annotations.Property;

@Config("sprout")
public class SproutConfig {

    @Property(type = PropertyType.CATEGORY, description = "World Generation Configurations.")
    public WorldGenConfig worldGen = new WorldGenConfig();
}
