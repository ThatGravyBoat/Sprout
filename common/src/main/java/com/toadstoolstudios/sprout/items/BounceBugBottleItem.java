package com.toadstoolstudios.sprout.items;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BounceBugBottleItem extends BlockItem {
    public BounceBugBottleItem(Settings settings) {
        super(SproutBlocks.BOUNCE_BUG_BOTTLE.get(), settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(main(context.getPlayer(), context.getWorld(), context.getHand(), context.getBlockPos(), context.getSide())) return ActionResult.SUCCESS;
        return super.useOnBlock(context);
    }

    public boolean main(PlayerEntity player, World world, Hand hand, BlockPos hitPos, Direction sideOfBlock) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if(!player.isSneaking() && stackInHand.getOrCreateNbt().contains("bug")) {
            BounceBugEntity bug = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world);
            bug.readNbt(stackInHand.getNbt().getCompound("bug"));
            stackInHand.decrement(1);
            player.getInventory().offerOrDrop(Items.GLASS_BOTTLE.getDefaultStack());
            BlockPos blockPos = hitPos.offset(sideOfBlock);
            bug.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            world.spawnEntity(bug);
            return true;
        }
        return false;
    }
}
