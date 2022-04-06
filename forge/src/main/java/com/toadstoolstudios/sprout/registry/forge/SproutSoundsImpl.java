package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutSoundsImpl {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Sprout.MODID);

    public static Supplier<SoundEvent> registerSound(Identifier name, Supplier<SoundEvent> soundEventSupplier) {
        return SOUNDS.register(name.getPath(), soundEventSupplier);
    }
}
