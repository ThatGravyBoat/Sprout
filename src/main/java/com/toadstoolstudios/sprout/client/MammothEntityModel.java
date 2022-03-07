package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.MammothEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MammothEntityModel extends AnimatedGeoModel<MammothEntity> {
    @Override
    public Identifier getModelLocation(MammothEntity object) {
        return new Identifier(Sprout.MODID, "geo/mammoth.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MammothEntity object) {
        return new Identifier(Sprout.MODID, "textures/entity/mammoth.png");
    }

    @Override
    public Identifier getAnimationFileLocation(MammothEntity animatable) {
        return new Identifier(Sprout.MODID, "animations/elephant.animation.json");
    }
}