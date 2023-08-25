package tech.thatgravyboat.sprout.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import tech.thatgravyboat.sprout.Sprout;

import java.util.function.Supplier;

public class SproutParticles {

    public static final ResourcefulRegistry<ParticleType<?>> PARTICLES = ResourcefulRegistries.create(Registry.PARTICLE_TYPE, Sprout.MODID);

    public static final Supplier<SimpleParticleType> SNOOZE = PARTICLES.register("snooze", () -> new SimpleParticleType(false){});
    public static final Supplier<SimpleParticleType> SHOOTS = PARTICLES.register("shoots", () -> new SimpleParticleType(false){});
}
