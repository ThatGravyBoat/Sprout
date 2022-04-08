package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutParticlesImpl {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Sprout.MODID);

    public static Supplier<DefaultParticleType> registerParticle(String name, Supplier<DefaultParticleType> particleSupplier) {
        return PARTICLES.register(name, particleSupplier);
    }
}
