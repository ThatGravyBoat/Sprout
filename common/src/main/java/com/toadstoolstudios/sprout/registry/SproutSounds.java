package com.toadstoolstudios.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class SproutSounds {

    public static void registerSounds() {
        //initialize class
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(Identifier name, Supplier<SoundEvent> soundEventSupplier) {
        throw new AssertionError();
    }
}
