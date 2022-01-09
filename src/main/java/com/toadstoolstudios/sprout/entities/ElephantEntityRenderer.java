package com.toadstoolstudios.sprout.entities;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ElephantEntityRenderer extends GeoEntityRenderer<ElephantEntity> {
    public ElephantEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ElephantEntityModel());
    }

    @Override
    public void render(ElephantEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if(entity.isBaby()) stack.scale(0.5F, 0.5F, 0.5F);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
