package tech.thatgravyboat.sprout.common.items;

import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.common.entities.BounceBugVariant;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;
import tech.thatgravyboat.sprout.common.registry.SproutEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BounceBugBottleItem extends BlockItem {
    public BounceBugBottleItem(Properties settings) {
        super(SproutBlocks.BOUNCE_BUG_BOTTLE.get(), settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();
        if(player != null) {
            ItemStack stackInHand = player.getItemInHand(context.getHand());
            CompoundTag nbt = stackInHand.getTag();
            if(!player.isShiftKeyDown()) {
                Level world = context.getLevel();
                if (!world.isClientSide()) {
                    BounceBugEntity bug = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world);
                    if (nbt != null) bug.load(nbt.getCompound("bug"));
                    stackInHand.shrink(1);
                    player.getInventory().placeItemBackInInventory(Items.GLASS_BOTTLE.getDefaultInstance());
                    BlockPos blockPos = context.getClickedPos().relative(context.getClickedFace());
                    bug.setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5);
                    bug.setDeltaMovement(0, 0.1, 0);
                    bug.tame(player);
                    world.addFreshEntity(bug);
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }
        return super.useOn(context);
    }
}
