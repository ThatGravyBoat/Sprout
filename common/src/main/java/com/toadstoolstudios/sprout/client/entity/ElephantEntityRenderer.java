package com.toadstoolstudios.sprout.client.entity;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ElephantEntityRenderer extends MobEntityRenderer<ElephantEntity> {
    public ElephantEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ElephantEntityModel());
    }

    @Override
    public RenderLayer getRenderType(ElephantEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
