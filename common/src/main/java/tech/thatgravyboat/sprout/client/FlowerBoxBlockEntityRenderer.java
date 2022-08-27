package tech.thatgravyboat.sprout.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.common.blocks.FlowerBoxBlockEntity;

public class FlowerBoxBlockEntityRenderer implements BlockEntityRenderer<FlowerBoxBlockEntity> {
    @Override
    public void render(FlowerBoxBlockEntity blockEntity, float tickDelta, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int light, int overlay) {
        Block flower = blockEntity.getFlower();
        if (flower == null) return;
        stack.pushPose();
        stack.translate(0, 0.35, 0);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(flower.defaultBlockState(), stack, source, light, OverlayTexture.NO_OVERLAY);
        stack.popPose();
    }
}
