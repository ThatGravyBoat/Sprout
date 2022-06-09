package tech.thatgravyboat.sprout.blocks;

import tech.thatgravyboat.sprout.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.registry.SproutBlocks;
import tech.thatgravyboat.sprout.registry.SproutEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BounceBugBottleBlockEntity extends BlockEntity {
    @Nullable
    private Entity renderEntity;

    private CompoundTag entity;

    public BounceBugBottleBlockEntity(BlockPos pos, BlockState state) {
        super(SproutBlocks.BOUNCE_BUG_JAR_BLOCK_ENTITY.get(), pos, state);
    }

    public void setEntity(CompoundTag entity) {
        this.entity = entity;
    }

    public Optional<CompoundTag> getEntity() {
        return Optional.ofNullable(this.entity);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("entity", 10)) entity = tag.getCompound("entity");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        if (entity != null) nbt.put("entity", entity);
    }

    public Entity getOrCreateEntity(Level world) {
        if(renderEntity == null) {
            renderEntity = new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), world, true);
            if (entity != null) renderEntity.load(entity);
            renderEntity.setPos(0, 0, 0);
            renderEntity.setYBodyRot(0);
            renderEntity.setXRot(0);
        }
        return renderEntity;
    }
}
