package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BounceBugEntityRenderer extends GeoEntityRenderer<BounceBugEntity> {

    public BounceBugEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BounceBugModel());
    }

    @Override
    public RenderLayer getRenderType(BounceBugEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
