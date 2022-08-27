package tech.thatgravyboat.sprout.common.flowers;

import java.util.Arrays;
import java.util.stream.Stream;

public enum FlowerType {
    DANDELION,
    POPPY,
    ORCHID,
    ALLIUM,
    AZURE_BLUET,
    TULIP,
    DAISY,
    CORNFLOWER,
    LILY_OF_THE_VALLEY;

    public static Stream<FlowerType> stream() {
        return Arrays.stream(values());
    }
}
