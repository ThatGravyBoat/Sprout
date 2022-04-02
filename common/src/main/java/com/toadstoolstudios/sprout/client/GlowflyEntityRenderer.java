package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GlowflyEntityRenderer extends GeoEntityRenderer<GlowflyEntity> {
    public GlowflyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GlowflyEntityModel());
        addLayer(new GlowflyGlowingLayer(this));
    }

    @Override
    public RenderLayer getRenderType(GlowflyEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
