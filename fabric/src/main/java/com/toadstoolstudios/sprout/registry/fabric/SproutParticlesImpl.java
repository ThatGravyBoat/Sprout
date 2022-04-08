package com.toadstoolstudios.sprout.registry.fabric;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class SproutParticlesImpl {

    public static final Set<String> TEXTURES = new HashSet<>();

    public static Supplier<DefaultParticleType> registerParticle(String name, Supplier<DefaultParticleType> particleSupplier) {
        TEXTURES.add(name);
        var register = Registry.register(Registry.PARTICLE_TYPE, new Identifier(MODID, name), particleSupplier.get());
        return () -> register;
    }
}
