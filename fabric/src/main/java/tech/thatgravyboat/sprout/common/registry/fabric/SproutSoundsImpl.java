package tech.thatgravyboat.sprout.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SproutSoundsImpl {


    public static Supplier<SoundEvent> registerSound(ResourceLocation name, Supplier<SoundEvent> soundEventSupplier) {
        var registry = Registry.register(Registry.SOUND_EVENT, name, soundEventSupplier.get());
        return () -> registry;
    }
}
