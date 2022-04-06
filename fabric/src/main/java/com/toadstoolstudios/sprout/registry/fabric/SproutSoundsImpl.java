package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.registry.SproutSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class SproutSoundsImpl {


    public static Supplier<SoundEvent> registerSound(Identifier name, Supplier<SoundEvent> soundEventSupplier) {
        var registry = Registry.register(Registry.SOUND_EVENT, name, soundEventSupplier.get());
        return () -> registry;
    }
}
