package tech.thatgravyboat.sprout.entities;

import tech.thatgravyboat.sprout.Sprout;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.Locale;

public enum BounceBugVariant {
    WARPED("textures/entity/warped_bug.png", ParticleTypes.WARPED_SPORE),
    CRIMSON("textures/entity/crimson_bug.png", ParticleTypes.CRIMSON_SPORE);

    public final ResourceLocation model;
    public final ResourceLocation texture;
    public final ParticleOptions particleEffect;

    BounceBugVariant(ResourceLocation model, ResourceLocation texture, ParticleOptions particleEffect) {
        this.model = model;
        this.texture = texture;
        this.particleEffect = particleEffect;
    }

    BounceBugVariant(String texture, ParticleOptions particleEffect) {
        this(new ResourceLocation(Sprout.MODID, "geo/bounce_bug.geo.json"), new ResourceLocation(Sprout.MODID, texture), particleEffect);
    }

    public static BounceBugVariant getVariant(String id) {
        try {
            return BounceBugVariant.valueOf(id.toUpperCase(Locale.ROOT));
        }catch (Exception e) {
            return BounceBugVariant.WARPED;
        }
    }

    public static BounceBugVariant random(RandomSource random) {
        return values()[random.nextInt(values().length)];
    }
}
