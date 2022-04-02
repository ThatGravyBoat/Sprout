package com.toadstoolstudios.sprout.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WateringCanItem extends Item {
    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockPos blockPos = blockHitResult.getBlockPos();
        ItemStack itemStack = user.getStackInHand(hand);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof FluidDrainable && blockState.isOf(Blocks.WATER)) {
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                itemStack.getOrCreateNbt().putInt("Water", 100);
                return TypedActionResult.success(itemStack, world.isClient());
            }
            return TypedActionResult.pass(itemStack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return super.getUseAction(stack);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var block = context.getBlockPos();
        Box blockBox = new Box(block).expand(1, 1, 1);
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        if(context.getPlayer() != null) {
            BlockHitResult blockHitResult = raycast(world, context.getPlayer(), RaycastContext.FluidHandling.SOURCE_ONLY);
            if(blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockState blockState = world.getBlockState(blockHitResult.getBlockPos());
                if (blockState.getBlock() instanceof FluidDrainable && blockState.isOf(Blocks.WATER)) return super.useOnBlock(context);
            }
        }
        int water = getWater(stack);
        System.out.println(water);
        if (water > 0) {
            if (world instanceof ServerWorld serverWorld) {
                setWater(stack, water - 1);
                serverWorld.spawnParticles(ParticleTypes.SPLASH, block.getX() + 0.5F, block.getY() + 0.5F, block.getZ() + 0.5F, 10, 0.7F, 0.5F, 0.7F, 1.4F);
                BlockPos.stream(blockBox).filter(blockPos -> world.getBlockState(blockPos).getBlock() instanceof Fertilizable).forEach(blockPos -> {
                    if (world.getRandom().nextInt(25) == 0) {
                        BlockState crop = world.getBlockState(blockPos);
                        Fertilizable fertilizable = ((Fertilizable) crop.getBlock());
                        fertilizable.grow(serverWorld, world.random, blockPos, crop);
                    }
                });
                BlockPos.stream(blockBox).filter(blockPos -> world.getBlockState(blockPos).getBlock() instanceof FarmlandBlock).forEach(blockPos -> {
                    BlockState blockState = serverWorld.getBlockState(blockPos);
                    int moisture = blockState.get(Properties.MOISTURE);
                    if(world.getRandom().nextInt(15) == 0 && moisture < 7){
                        world.setBlockState(blockPos, blockState.with(Properties.MOISTURE, 7));
                    }
                });
                return ActionResult.FAIL;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return (int) (getWater(stack) / 100F * 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(0.4f, 0.4f, 1.0f);
    }

    public int getWater(ItemStack stack) {
        return stack.getNbt() != null ? stack.getNbt().getInt("Water") : 0;
    }

    public void setWater(ItemStack stack, int amount) {
        stack.getOrCreateNbt().putInt("Water", amount);
    }
}
