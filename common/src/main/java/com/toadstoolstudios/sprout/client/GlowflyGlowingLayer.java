package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import static com.toadstoolstudios.sprout.Sprout.MODID;

public class GlowflyGlowingLayer extends GeoLayerRenderer<GlowflyEntity> {
    public GlowflyGlowingLayer(IGeoRenderer<GlowflyEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, GlowflyEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entitylivingbaseIn.isInAir()) {
            GeoModelProvider<GlowflyEntity> provider = this.getEntityModel();
            GeoModel model = provider.getModel(provider.getModelLocation(entitylivingbaseIn));
            VertexConsumer buffer = bufferIn.getBuffer(RenderLayer.getEyes(new Identifier(MODID, "textures/entity/glowfly_glow.png")));
            getRenderer().render(model, entitylivingbaseIn, partialTicks, null, matrixStackIn, null, buffer, 15728640, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
        }
    }
}
