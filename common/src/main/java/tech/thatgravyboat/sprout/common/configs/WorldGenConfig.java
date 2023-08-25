package tech.thatgravyboat.sprout.common.configs;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;
import tech.thatgravyboat.sprout.common.configs.worldgen.Sprouts;

@Category(id = "World Generation", translation = "World Generation")
public final class WorldGenConfig {

    @InlineCategory
    public static Sprouts sprouts;

}
