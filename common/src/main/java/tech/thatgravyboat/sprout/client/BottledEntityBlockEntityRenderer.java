package tech.thatgravyboat.sprout.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import tech.thatgravyboat.sprout.common.blocks.BottledEntityBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BottledEntityBlockEntityRenderer implements BlockEntityRenderer<BottledEntityBlockEntity> {
    @Override
    public void render(BottledEntityBlockEntity blockEntity, float tickDelta, PoseStack matrixStack, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        matrixStack.pushPose();
        matrixStack.translate(0.5, 0, 0.5);
        BlockState blockState = blockEntity.getBlockState();
        Direction direction = blockState.hasProperty(HorizontalDirectionalBlock.FACING) ? blockState.getValue(HorizontalDirectionalBlock.FACING) : Direction.SOUTH;
        matrixStack.mulPose(direction.getRotation());
        matrixStack.mulPose(Vector3f.XN.rotationDegrees(90));
        Entity entity = blockEntity.getOrCreateEntity(blockEntity.getLevel());
        entity.tickCount = Minecraft.getInstance().player == null ? 0 : Minecraft.getInstance().player.tickCount % 25000;
        matrixStack.scale(0.85F, 0.85F, 0.85F);
        Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0.0F, tickDelta, matrixStack, vertexConsumers, light);
        matrixStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull BottledEntityBlockEntity blockEntity) {
        return true;
    }
}
