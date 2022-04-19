package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

import java.util.Locale;
import java.util.Random;

public enum BounceBugVariant {
    WARPED("textures/entity/warped_bug.png", ParticleTypes.WARPED_SPORE),
    CRIMSON("textures/entity/crimson_bug.png", ParticleTypes.CRIMSON_SPORE);

    public Identifier model, texture;
    public ParticleEffect particleEffect;

    BounceBugVariant(Identifier model, Identifier texture, ParticleEffect particleEffect) {
        this.model = model;
        this.texture = texture;
        this.particleEffect = particleEffect;
    }

    BounceBugVariant(String texture, ParticleEffect particleEffect) {
        this(new Identifier(Sprout.MODID, "geo/bounce_bug.geo.json"), new Identifier(Sprout.MODID, texture), particleEffect);
    }

    public static BounceBugVariant getVariant(String id) {
        try {
            return BounceBugVariant.valueOf(id.toUpperCase(Locale.ROOT));
        }catch (Exception e) {
            return BounceBugVariant.WARPED;
        }
    }

    public static BounceBugVariant random(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
