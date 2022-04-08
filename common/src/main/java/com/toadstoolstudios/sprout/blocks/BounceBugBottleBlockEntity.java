package com.toadstoolstudios.sprout.blocks;

import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BounceBugBottleBlockEntity extends BlockEntity {
    @Nullable
    private Entity renderEntity;

    private NbtCompound entity;

    public BounceBugBottleBlockEntity(BlockPos pos, BlockState state) {
        super(SproutBlocks.BOUNCE_BUG_JAR_BLOCK_ENTITY.get(), pos, state);
    }

    public void setEntity(NbtCompound entity) {
        this.entity = entity;
    }

    public Optional<NbtCompound> getEntity() {
        return Optional.ofNullable(this.entity);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("entity", 10)) entity = nbt.getCompound("entity");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if (entity != null) nbt.put("entity", entity);
    }

    public Entity getOrCreateEntity(World world) {
        if(renderEntity == null) {
            renderEntity = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world, true);
            if (entity != null) renderEntity.readNbt(entity);
            renderEntity.setPos(0, 0, 0);
            renderEntity.setBodyYaw(0);
            renderEntity.setPitch(0);
        }
        return renderEntity;
    }
}
