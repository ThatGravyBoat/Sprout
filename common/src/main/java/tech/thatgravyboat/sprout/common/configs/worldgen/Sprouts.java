package tech.thatgravyboat.sprout.common.configs.worldgen;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "Sprouts", translation = "Sprouts")
public final class Sprouts {

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "enabled",
        translation = "Enabled"
    )
    @Comment(
        value = "Weather you want sprouts or not."
    )
    public static boolean enabled = true;

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "frequency",
        translation = "Frequency"
    )
    @Comment(
        value = "The frequency that sprouts will spawn. Note: 32 is vanilla grass."
    )
    public static int frequency = 32;
}
