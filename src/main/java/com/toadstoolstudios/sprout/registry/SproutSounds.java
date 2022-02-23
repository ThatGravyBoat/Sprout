package com.toadstoolstudios.sprout.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutSounds {
    public static final Identifier GLOWFLY_SOUND_LOC = new Identifier(MODID, "glowfly_fly");
    public static final SoundEvent GLOWFLY_SOUND = new SoundEvent(GLOWFLY_SOUND_LOC);

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, GLOWFLY_SOUND_LOC, GLOWFLY_SOUND);
    }
}
