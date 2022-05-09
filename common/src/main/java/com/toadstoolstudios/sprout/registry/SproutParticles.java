package com.toadstoolstudios.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.particle.DefaultParticleType;

import java.util.function.Supplier;

public class SproutParticles {

    public static final Supplier<DefaultParticleType> SNOOZE = registerParticle("snooze", () -> new DefaultParticleType(false){});
    public static final Supplier<DefaultParticleType> SHOOTS = registerParticle("shoots", () -> new DefaultParticleType(false){});

    public static void registerParticles() {
        //initialize class
    }

    @ExpectPlatform
    public static Supplier<DefaultParticleType> registerParticle(String name, Supplier<DefaultParticleType> particleSupplier) {
        throw new AssertionError();
    }
}
