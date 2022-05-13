package com.toadstoolstudios.sprout.configs;

import com.toadstoolstudios.sprout.config.PropertyType;
import com.toadstoolstudios.sprout.config.annotations.Category;
import com.toadstoolstudios.sprout.config.annotations.Property;
import com.toadstoolstudios.sprout.configs.worldgen.Sprouts;

@Category("World Generation")
public class WorldGenConfig {

    @Property(type = PropertyType.CATEGORY, description = "Sprouts Generation Configurations")
    public Sprouts sprouts = new Sprouts();

}
