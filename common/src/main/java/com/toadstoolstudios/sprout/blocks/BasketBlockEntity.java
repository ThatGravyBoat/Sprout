package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BasketBlockEntity extends BlockEntity implements Inventory {

    DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public BasketBlockEntity(BlockPos pos, BlockState state) {
        super(SproutBlocks.BASKET_BLOCK_ENTITY.get(), pos, state);
    }


    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = items.get(slot);
        itemStack.decrement(amount);
        items.set(slot, itemStack);
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return items.remove(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        items = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, items);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
    }
}
