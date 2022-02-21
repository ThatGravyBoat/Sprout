package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.toadstoolstudios.sprout.Sprout.MOD_ID;

public class GlowflyEntityModel extends AnimatedGeoModel<GlowflyEntity> {

    @Override
    public Identifier getModelLocation(GlowflyEntity object) {
        return new Identifier(MOD_ID, "geo/glowfly.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GlowflyEntity object) {
        return new Identifier(MOD_ID, "textures/entity/glowfly.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GlowflyEntity animatable) {
        return new Identifier(Sprout.MOD_ID, "animations/glowfly.animation.json");
    }
}
