package tech.thatgravyboat.sprout.common.registry;

import tech.thatgravyboat.sprout.Sprout;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SproutSounds {

    public static final Supplier<SoundEvent> SLEEP = registerSound(new ResourceLocation(Sprout.MODID, "sleep"));

    public static void registerSounds() {
        //initialize class
    }

    public static Supplier<SoundEvent> registerSound(ResourceLocation id) {
        return registerSound(id, () -> new SoundEvent(id));
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(ResourceLocation name, Supplier<SoundEvent> soundEventSupplier) {
        throw new AssertionError();
    }
}
