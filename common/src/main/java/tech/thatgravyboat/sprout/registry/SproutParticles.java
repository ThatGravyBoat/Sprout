package tech.thatgravyboat.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class SproutParticles {

    public static final Supplier<SimpleParticleType> SNOOZE = registerParticle("snooze", () -> new SimpleParticleType(false){});
    public static final Supplier<SimpleParticleType> SHOOTS = registerParticle("shoots", () -> new SimpleParticleType(false){});

    public static void registerParticles() {
        //initialize class
    }

    @ExpectPlatform
    public static Supplier<SimpleParticleType> registerParticle(String name, Supplier<SimpleParticleType> particleSupplier) {
        throw new AssertionError();
    }
}
