package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class GlowflyEntityModel extends AnimatedGeoModel<GlowflyEntity> {

    @Override
    public Identifier getModelLocation(GlowflyEntity glowfly) {
        return new Identifier(MODID, "geo/glowfly.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GlowflyEntity glowfly) {
        return glowfly.isInAir() ? new Identifier(MODID, "textures/entity/glowfly.png") : new Identifier(MODID, "textures/entity/glowfly_standing.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GlowflyEntity glowfly) {
        return new Identifier(Sprout.MODID, "animations/glowfly.animation.json");
    }
}
