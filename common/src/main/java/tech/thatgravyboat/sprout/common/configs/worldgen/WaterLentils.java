package tech.thatgravyboat.sprout.common.configs.worldgen;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "water_lentils", translation = "Water Lentils")
public final class WaterLentils {

    @ConfigEntry(
        type = EntryType.FLOAT,
        id = "spread_chance",
        translation = "Spread Chance"
    )
    @Comment(
        value = "The chance that a water lentil will spread to a nearby block when bonemealed."
    )
    public static float chance = 0.4f;
}
