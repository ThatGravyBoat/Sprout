package com.toadstoolstudios.sprout.entities;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GlowflyEntityRenderer extends GeoEntityRenderer<GlowflyEntity> {
    public GlowflyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GlowflyEntityModel());
    }
}
