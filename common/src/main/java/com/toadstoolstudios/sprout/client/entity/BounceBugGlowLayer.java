package com.toadstoolstudios.sprout.client.entity;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BounceBugGlowLayer extends GeoLayerRenderer<BounceBugEntity> {

    private static final Identifier EYES = new Identifier(Sprout.MODID, "textures/entity/bug_eyes.png");

    public BounceBugGlowLayer(IGeoRenderer<BounceBugEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn, BounceBugEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        GeoModel model = this.getEntityModel().getModel(this.getEntityModel().getModelLocation(entity));
        VertexConsumer consumer = buffer.getBuffer(RenderLayer.getEyes(EYES));
        getRenderer().render(model, entity, partialTicks,
                null, stack, null, consumer,
                packedLightIn, GeoEntityRenderer.getPackedOverlay(entity, 0),
                1f, 1f, 1f, 1f);
    }
}
