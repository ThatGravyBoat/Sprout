package com.toadstoolstudios.sprout.client;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.NotNull;

public class SnoozeParticle extends SpriteBillboardParticle {

    SnoozeParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, double a, double b, double c) {
        super(world, x, y, z, a, b, c);
        this.setBoundingBoxSpacing(1f, 1f);
        this.maxAge = 100;
        this.gravityStrength = 0.05f;
        this.velocityMultiplier = 0.4f;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * (1f - ((float) this.age / this.maxAge));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public @NotNull Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SnoozeParticle(world, x, y, z, spriteProvider, velocityX, velocityY, velocityZ);
        }
    }
}
