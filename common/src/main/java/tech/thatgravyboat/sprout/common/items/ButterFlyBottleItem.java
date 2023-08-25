package tech.thatgravyboat.sprout.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.common.entities.ButterFly;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;
import tech.thatgravyboat.sprout.common.registry.SproutEntities;

public class ButterFlyBottleItem extends BlockItem {
    public ButterFlyBottleItem(Properties settings) {
        super(SproutBlocks.BUTTER_FLY_BOTTLE.get(), settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();
        if(player != null) {
            ItemStack stackInHand = player.getItemInHand(context.getHand());
            CompoundTag nbt = stackInHand.getTag();
            if(!player.isShiftKeyDown()) {
                Level world = context.getLevel();
                if (!world.isClientSide()) {
                    ButterFly butterFly = new ButterFly(SproutEntities.BUTTERFLY.get(), world);
                    if (nbt != null) butterFly.load(nbt.getCompound("entity"));
                    stackInHand.shrink(1);
                    player.getInventory().placeItemBackInInventory(Items.GLASS_BOTTLE.getDefaultInstance());
                    BlockPos blockPos = context.getClickedPos().relative(context.getClickedFace());
                    butterFly.setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5);
                    butterFly.setDeltaMovement(0, 0.1, 0);
                    butterFly.setFromBottle(true);
                    world.addFreshEntity(butterFly);
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }
        return super.useOn(context);
    }
}
