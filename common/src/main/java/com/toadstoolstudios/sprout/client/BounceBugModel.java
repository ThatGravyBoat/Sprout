package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BounceBugModel extends AnimatedGeoModel<BounceBugEntity> {
    @Override
    public Identifier getModelLocation(BounceBugEntity object) {
        return object.getBounceBugVariant().model;
    }

    @Override
    public Identifier getTextureLocation(BounceBugEntity object) {
        return object.getBounceBugVariant().texture;
    }

    @Override
    public Identifier getAnimationFileLocation(BounceBugEntity animatable) {
        return new Identifier(Sprout.MODID, "animations/bounce_bug.animation.json");
    }
}
