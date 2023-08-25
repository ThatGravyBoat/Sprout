package tech.thatgravyboat.sprout.common.entities;

import com.teamresourceful.resourcefullib.common.collections.WeightedCollection;

public enum ButterFlyColor {
    RED(30),
    ORANGE(80),
    YELLOW(30),
    LIME(10),
    BLUE(5),
    PURPLE(10),
    PINK(30),
    WHITE(10);

    public static final WeightedCollection<ButterFlyColor> COLORS = new WeightedCollection<>();
    static {
        for (ButterFlyColor color : values()) {
            COLORS.add(color.weight, color);
        }
    }

    private final int weight;

    ButterFlyColor(int weight) {
        this.weight = weight;
    }
}
