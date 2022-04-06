package com.toadstoolstudios.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutSounds {
    public static final Identifier GLOWFLY_SOUND_LOC = new Identifier(MODID, "glowfly_fly");
    public static final Supplier<SoundEvent> GLOWFLY_SOUND = registerSound(GLOWFLY_SOUND_LOC, () -> new SoundEvent(GLOWFLY_SOUND_LOC));

    public static void registerSounds() {
        //initialize class
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(Identifier name, Supplier<SoundEvent> soundEventSupplier) {
        throw new AssertionError();
    }
}
