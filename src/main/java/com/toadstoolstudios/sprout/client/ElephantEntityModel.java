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
        return new Identifier(Sprout.MODID, "textures/entity/elephant.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ElephantEntity animatable) {
        return new Identifier(Sprout.MODID, "animations/elephant.animation.json");
    }

    @Override
    public void setLivingAnimations(ElephantEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if(customPredicate == null) return;
        //noinspection unchecked
        List<EntityModelData> entityDataStuffandThings = customPredicate.getExtraDataOfType(EntityModelData.class);
        var head = this.getAnimationProcessor().getBone("head");
        var adjustedPitch = entity.isInSittingPose() ? -0.4799655F : 0F;
        head.setRotationX(entityDataStuffandThings.get(0).headPitch * ((float)Math.PI / 180F) + adjustedPitch);
        head.setRotationY(entityDataStuffandThings.get(0).netHeadYaw * ((float)Math.PI / 180F));
    }
}
