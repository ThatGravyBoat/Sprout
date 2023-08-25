package tech.thatgravyboat.sprout.client.entity;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.entities.ElephantEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ElephantEntityModel extends AnimatedGeoModel<ElephantEntity> {

    private static final ResourceLocation NORMAL = new ResourceLocation(Sprout.MODID, "textures/entity/elephant.png");
    private static final ResourceLocation NORMAL_SLEEPING = new ResourceLocation(Sprout.MODID, "textures/entity/sleeping_elephant.png");
    private static final ResourceLocation LUMPY = new ResourceLocation(Sprout.MODID, "textures/entity/lumpy.png");
    private static final ResourceLocation LUMPY_SLEEPING = new ResourceLocation(Sprout.MODID, "textures/entity/sleeping_lumpy.png");
    private static final ResourceLocation TREE_TRUNKS = new ResourceLocation(Sprout.MODID, "textures/entity/tree_trunks.png");
    private static final ResourceLocation TREE_TRUNKS_SLEEPING = new ResourceLocation(Sprout.MODID, "textures/entity/sleeping_tree_trunks.png");

    @Override
    public ResourceLocation getModelResource(ElephantEntity object) {
        return new ResourceLocation(Sprout.MODID, "geo/elephant.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ElephantEntity object) {
        var closedEyes = object.isInSittingPose() || object.isWatering();
        return switch (object.getName().getString().toLowerCase().replace(" ", "_")) {
            case "lumpy" -> closedEyes ? LUMPY_SLEEPING : LUMPY;
            case "tree_trunks" -> closedEyes ? TREE_TRUNKS_SLEEPING : TREE_TRUNKS;
            default -> closedEyes ? NORMAL_SLEEPING : NORMAL;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(ElephantEntity animatable) {
        return new ResourceLocation(Sprout.MODID, "animations/elephant.animation.json");
    }
}
