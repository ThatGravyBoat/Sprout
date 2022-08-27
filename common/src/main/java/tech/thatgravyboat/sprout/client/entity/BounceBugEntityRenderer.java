package tech.thatgravyboat.sprout.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class BounceBugEntityRenderer extends MobEntityRenderer<BounceBugEntity> {
    private final ItemRenderer itemRenderer;

    public BounceBugEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BounceBugModel());
        this.itemRenderer = ctx.getItemRenderer();
        this.addLayer(new BounceBugGlowLayer(this));
    }

    @Override
    public void render(@NotNull BounceBugEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        ItemStack shroom = entity.getItemInHand(InteractionHand.MAIN_HAND);
        if(!shroom.isEmpty() && entity.isInSittingPose()) {
            stack.pushPose();
            stack.mulPose(Vector3f.YN.rotationDegrees(entity.yBodyRot));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));
            stack.translate(-0.05, 0.1, 0.35);
            itemRenderer.renderStatic(shroom, ItemTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, stack, bufferIn, entity.getId());
            stack.popPose();
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
