package tech.thatgravyboat.sprout.client.entity;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.entities.BounceBugEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BounceBugModel extends AnimatedGeoModel<BounceBugEntity> {
    @Override
    public ResourceLocation getModelResource(BounceBugEntity object) {
        return object.getBounceBugVariant().model;
    }

    @Override
    public ResourceLocation getTextureResource(BounceBugEntity object) {
        return object.getBounceBugVariant().texture;
    }

    @Override
    public ResourceLocation getAnimationResource(BounceBugEntity animatable) {
        return new ResourceLocation(Sprout.MODID, "animations/bounce_bug.animation.json");
    }
}
