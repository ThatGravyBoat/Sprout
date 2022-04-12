package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.List;

public class ElephantEntityModel extends AnimatedGeoModel<ElephantEntity> {
    @Override
    public Identifier getModelLocation(ElephantEntity object) {
        return new Identifier(Sprout.MODID, "geo/elephant.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ElephantEntity object) {
        String name = switch (object.getName().getString().toLowerCase()) {
            case "lumpy" -> "lumpy";
            case "tree trunks" -> "tree_trunks";
            default -> "elephant";
        };
        return object.isInSittingPose() ? new Identifier(Sprout.MODID, "textures/entity/sleeping_" + name + ".png") : new Identifier(Sprout.MODID, "textures/entity/" + name + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(ElephantEntity animatable) {
        return new Identifier(Sprout.MODID, "animations/elephant.animation.json");
    }
}
