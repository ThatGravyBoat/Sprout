package tech.thatgravyboat.sprout.common.flowers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public enum FlowerColor {
    WHITE(FlowerType.TULIP, FlowerType.LILY_OF_THE_VALLEY),
    ORANGE(FlowerType.TULIP),
    MAGENTA(FlowerType.ALLIUM),
    LIGHT_BLUE(FlowerType.ORCHID),
    YELLOW(FlowerType.DANDELION),
    LIME(),
    PINK(FlowerType.TULIP),
    GRAY(),
    LIGHT_GRAY(FlowerType.AZURE_BLUET, FlowerType.DAISY),
    CYAN(),
    PURPLE(),
    BLUE(FlowerType.CORNFLOWER),
    BROWN(true),
    GREEN(),
    RED(FlowerType.POPPY, FlowerType.TULIP),
    BLACK(true);

    public final boolean special;
    public final Set<FlowerType> bannedTypes;

    FlowerColor(FlowerType... bannedTypes) {
        this(false, bannedTypes);
    }

    FlowerColor(boolean special, FlowerType... bannedTypes) {
        this.special = special;
        this.bannedTypes = Set.of(bannedTypes);
    }

    public boolean isNormal() {
        return !isSpecial();
    }

    public boolean isSpecial() {
        return this.special;
    }

    public static Stream<FlowerColor> stream() {
        return Arrays.stream(values());
    }
}
