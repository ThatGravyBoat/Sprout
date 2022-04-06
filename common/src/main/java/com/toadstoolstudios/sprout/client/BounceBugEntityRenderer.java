package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BounceBugEntityRenderer extends GeoEntityRenderer<BounceBugEntity> {
    private final ItemRenderer shroomRenderer;

    public BounceBugEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BounceBugModel());
        this.shroomRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(BounceBugEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if(entity.isInSittingPose()) {
            stack.push();
            stack.translate(0, 0.35, 0);
            ItemStack shroom = entity.getBounceBugVariant().shroomChoice.getDefaultStack();
            stack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(entity.bodyYaw));
            stack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));
            shroomRenderer.renderItem(shroom, ModelTransformation.Mode.FIXED, packedLightIn, OverlayTexture.DEFAULT_UV, stack, bufferIn, entity.getId());
            stack.pop();
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public RenderLayer getRenderType(BounceBugEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
