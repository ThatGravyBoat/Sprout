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

    public FlowerColor combined(FlowerColor color) {
        return switch (this) {
            case RED -> {
                if (color == YELLOW) yield ORANGE;
                if (color == BLUE) yield PURPLE;
                yield null;
            }
            case WHITE -> {
                if (color == GREEN) yield LIME;
                if (color == BLUE) yield LIGHT_BLUE;
                if (color == GRAY) yield LIGHT_GRAY;
                if (color == BLACK) yield GRAY;
                yield null;
            }
            case GREEN -> {
                if (color == WHITE) yield LIME;
                if (color == BLUE) yield CYAN;
                yield null;
            }
            case BLUE -> {
                if (color == WHITE) yield LIGHT_BLUE;
                if (color == GREEN) yield CYAN;
                if (color == RED) yield PURPLE;
                if (color == YELLOW) yield GREEN;
                yield null;
            }
            case YELLOW -> {
                if (color == RED) yield ORANGE;
                if (color == WHITE) yield LIME;
                yield null;
            }
            case BLACK -> {
                if (color == WHITE) yield GRAY;
                yield null;
            }
            default -> null;
        };
    }
}
