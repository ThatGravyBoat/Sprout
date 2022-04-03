package com.toadstoolstudios.sprout.client;

import com.toadstoolstudios.sprout.blocks.BounceBugBottleBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class BounceBugJarBlockEntityRenderer implements BlockEntityRenderer<BounceBugBottleBlockEntity> {
    @Override
    public void render(BounceBugBottleBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrixStack.push();
        matrixStack.translate(0.5, 0, 0.5);
        World world = blockEntity.getWorld();
        BlockState blockState = world.getBlockState(blockEntity.getPos());
        Direction direction = blockState.contains(HorizontalFacingBlock.FACING) ? blockState.get(HorizontalFacingBlock.FACING) : Direction.SOUTH;
        matrixStack.multiply(direction.getRotationQuaternion());
        matrixStack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));
        Entity entity = blockEntity.getOrCreateEntity(world);
        float g = 0.53125f;
        matrixStack.scale(g, g, g);
        //if(MinecraftClient.getInstance().player != null) entity.age = MinecraftClient.getInstance().player.age;
        MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0.0F, tickDelta, matrixStack, vertexConsumers, light);
        matrixStack.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(BounceBugBottleBlockEntity blockEntity) {
        return true;
    }
}
