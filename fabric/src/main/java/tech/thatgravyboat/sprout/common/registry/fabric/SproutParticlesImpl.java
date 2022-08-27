package tech.thatgravyboat.sprout.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static tech.thatgravyboat.sprout.Sprout.MODID;

public class SproutParticlesImpl {

    public static final Set<String> TEXTURES = new HashSet<>();

    public static Supplier<SimpleParticleType> registerParticle(String name, Supplier<SimpleParticleType> particleSupplier) {
        TEXTURES.add(name);
        var register = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(MODID, name), particleSupplier.get());
        return () -> register;
    }
}
