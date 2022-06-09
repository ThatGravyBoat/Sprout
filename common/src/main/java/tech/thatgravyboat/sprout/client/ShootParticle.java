package tech.thatgravyboat.sprout.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class ShootParticle extends TextureSheetParticle {

    ShootParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, double a, double b, double c) {
        super(world, x, y, z, a, b, c);
        this.setSize(1f, 1f);
        this.lifetime = 100;
        this.gravity = 0.05f;
        this.friction = 0.4f;
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public float getQuadSize(float tickDelta) {
        return (this.quadSize * 2) * (1f - ((float) this.age / this.lifetime));
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(@NotNull SimpleParticleType parameters, @NotNull ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ShootParticle(world, x, y, z, spriteProvider, velocityX, velocityY, velocityZ);
        }
    }
}
