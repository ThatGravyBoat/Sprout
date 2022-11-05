package tech.thatgravyboat.sprout.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BounceBugGlowLayer extends GeoLayerRenderer<BounceBugEntity> {

    private static final ResourceLocation EYES = new ResourceLocation(Sprout.MODID, "textures/entity/bug_eyes.png");

    public BounceBugGlowLayer(IGeoRenderer<BounceBugEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, BounceBugEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        GeoModel model = this.getEntityModel().getModel(this.getEntityModel().getModelResource(entity));
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(EYES));
        getRenderer().render(model, entity, partialTicks,
                null, stack, null, consumer,
                packedLightIn, OverlayTexture.pack(OverlayTexture.u(0), entity.hurtTime > 0 || entity.deathTime > 0),
                1f, 1f, 1f, 1f);
    }
}
