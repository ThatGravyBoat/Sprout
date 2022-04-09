package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.Sprout;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class SproutSounds {

    public static final Supplier<SoundEvent> SLEEP = registerSound(new Identifier(Sprout.MODID, "sleep"));

    public static void registerSounds() {
        //initialize class
    }

    public static Supplier<SoundEvent> registerSound(Identifier id) {
        return registerSound(id, () -> new SoundEvent(id));
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(Identifier name, Supplier<SoundEvent> soundEventSupplier) {
        throw new AssertionError();
    }
}
