package tech.thatgravyboat.sprout.common.configs;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

@Config("sprout")
public final class SproutConfig {

    @InlineCategory
    public static WorldGenConfig worldGen;
}
