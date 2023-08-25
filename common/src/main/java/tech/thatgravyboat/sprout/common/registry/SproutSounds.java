package tech.thatgravyboat.sprout.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.sprout.Sprout;

import java.util.function.Supplier;

public class SproutSounds {

    public static final ResourcefulRegistry<SoundEvent> SOUNDS = ResourcefulRegistries.create(Registry.SOUND_EVENT, Sprout.MODID);

    public static final Supplier<SoundEvent> SLEEP = SOUNDS.register("sleep", () -> new SoundEvent(new ResourceLocation(Sprout.MODID, "sleep")));
}
