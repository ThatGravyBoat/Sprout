package tech.thatgravyboat.sprout.registry.forge;

import tech.thatgravyboat.sprout.Sprout;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutSoundsImpl {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Sprout.MODID);

    public static Supplier<SoundEvent> registerSound(ResourceLocation name, Supplier<SoundEvent> soundEventSupplier) {
        return SOUNDS.register(name.getPath(), soundEventSupplier);
    }
}
