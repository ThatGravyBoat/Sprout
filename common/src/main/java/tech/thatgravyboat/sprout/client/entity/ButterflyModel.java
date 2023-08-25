package tech.thatgravyboat.sprout.client.entity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.entities.ButterFly;

public class ButterflyModel extends AnimatedGeoModel<ButterFly> {

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/red.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/orange.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/yellow.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/lime.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/blue.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/purple.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/pink.png"),
            new ResourceLocation(Sprout.MODID, "textures/entity/butterfly/white.png")
    };

    @Override
    public ResourceLocation getModelResource(ButterFly object) {
        return new ResourceLocation(Sprout.MODID, "geo/butterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ButterFly object) {
        final int color = object.getColor();
        return color >= 0 && color < TEXTURES.length ? TEXTURES[color] : TEXTURES[0];
    }

    @Override
    public ResourceLocation getAnimationResource(ButterFly animatable) {
        return new ResourceLocation(Sprout.MODID, "animations/butterfly.animation.json");
    }
}
