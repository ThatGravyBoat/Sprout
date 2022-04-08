package com.toadstoolstudios.sprout.items;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.BounceBugVariant;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BounceBugBottleItem extends BlockItem {
    public BounceBugBottleItem(Settings settings) {
        super(SproutBlocks.BOUNCE_BUG_BOTTLE.get(), settings);
    }

    public static float getTextureId(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (!stack.hasNbt() || nbt == null) return 0f;
        NbtCompound bug = nbt.getCompound("bug");
        return bug == null ? 0 : BounceBugVariant.getVarient(bug.getString("bugType")).ordinal();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var player = context.getPlayer();
        if(player != null) {
            ItemStack stackInHand = player.getStackInHand(context.getHand());
            NbtCompound nbt = stackInHand.getNbt();
            if(!player.isSneaking()) {
                World world = context.getWorld();
                if (!world.isClient) {
                    BounceBugEntity bug = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world);
                    if (nbt != null) bug.readNbt(nbt.getCompound("bug"));
                    stackInHand.decrement(1);
                    player.getInventory().offerOrDrop(Items.GLASS_BOTTLE.getDefaultStack());
                    BlockPos blockPos = context.getBlockPos().offset(context.getSide());
                    bug.setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5);
                    bug.setVelocity(0, 0.1, 0);
                    world.spawnEntity(bug);
                }
                return ActionResult.success(world.isClient());
            }
        }
        return super.useOnBlock(context);
    }
}
