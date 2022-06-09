package tech.thatgravyboat.sprout.client.entity;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.entities.ElephantEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ElephantEntityModel extends AnimatedGeoModel<ElephantEntity> {
    @Override
    public ResourceLocation getModelResource(ElephantEntity object) {
        return new ResourceLocation(Sprout.MODID, "geo/elephant.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ElephantEntity object) {
        String name = switch (object.getName().getString().toLowerCase()) {
            case "lumpy" -> "lumpy";
            case "tree trunks" -> "tree_trunks";
            default -> "elephant";
        };
        return object.isInSittingPose() ? new ResourceLocation(Sprout.MODID, "textures/entity/sleeping_" + name + ".png") : new ResourceLocation(Sprout.MODID, "textures/entity/" + name + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ElephantEntity animatable) {
        return new ResourceLocation(Sprout.MODID, "animations/elephant.animation.json");
    }
}
