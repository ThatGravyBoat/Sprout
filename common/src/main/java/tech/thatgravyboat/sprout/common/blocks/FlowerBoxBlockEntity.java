package tech.thatgravyboat.sprout.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;

import java.util.Optional;

public class FlowerBoxBlockEntity extends BlockEntity {

    @Nullable
    private Block flower;

    public FlowerBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SproutBlocks.FLOWER_BOX_ENTITY.get(), blockPos, blockState);
    }



    public void setFlower(@Nullable Block flower) {
        this.flower = flower;

        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public @Nullable Block getFlower() {
        return flower;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
            flower = getOptionalString(tag, "Flower").flatMap(id -> Registry.BLOCK.getOptional(ResourceLocation.tryParse(id))).orElse(null);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        if (flower != null) {
            tag.putString("Flower", Registry.BLOCK.getKey(flower).toString());
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    public static Block getFlower(BlockEntity block) {
        return block instanceof FlowerBoxBlockEntity flowerBox ? flowerBox.getFlower() : null;
    }

    @SuppressWarnings("SameParameterValue")
    private static Optional<String> getOptionalString(CompoundTag tag, String key) {
        return tag.contains(key, Tag.TAG_STRING) ? Optional.of(tag.getString(key)) : Optional.empty();
    }
}
