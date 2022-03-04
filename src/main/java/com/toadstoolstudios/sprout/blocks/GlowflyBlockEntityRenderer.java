package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3f;

public class GlowflyBlockEntityRenderer implements BlockEntityRenderer<GlowflyJarBlockEntity> {
    @Override
    public void render(GlowflyJarBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.0, 0.5);
        Entity entity = new GlowflyEntity(SproutEntities.GLOWFLY_ENTITY_TYPE, blockEntity.getWorld());
        float g = 0.53125f;
        float h = Math.max(entity.getWidth(), entity.getHeight());
        if ((double)h > 1.0) {
            g /= h;
        }
        matrixStack.translate(0.0, 0.4f, 0.0);
        matrixStack.translate(0.0, -0.2f, 0.0);
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-30.0f));
        matrixStack.scale(g, g, g);
        MinecraftClient.getInstance().getEntityRenderDispatcher().render(entity, 0.0, 0.0, 0.0, 0.0f, tickDelta, matrixStack, vertexConsumers, light);
        matrixStack.pop();
    }
}
