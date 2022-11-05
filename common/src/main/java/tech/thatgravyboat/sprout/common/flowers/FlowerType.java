package tech.thatgravyboat.sprout.common.flowers;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum FlowerType {
    DANDELION(() -> MobEffects.SATURATION),
    POPPY(() -> MobEffects.NIGHT_VISION),
    ORCHID(() -> MobEffects.SATURATION),
    ALLIUM(() -> MobEffects.FIRE_RESISTANCE),
    AZURE_BLUET(() -> MobEffects.BLINDNESS),
    TULIP(() -> MobEffects.WEAKNESS),
    DAISY(() -> MobEffects.REGENERATION),
    CORNFLOWER(() -> MobEffects.JUMP),
    LILY_OF_THE_VALLEY(() -> MobEffects.POISON);

    public final Supplier<MobEffect> stewEffect;

    FlowerType(Supplier<MobEffect> stewEffect) {
        this.stewEffect = stewEffect;
    }

    public static Stream<FlowerType> stream() {
        return Arrays.stream(values());
    }
}
