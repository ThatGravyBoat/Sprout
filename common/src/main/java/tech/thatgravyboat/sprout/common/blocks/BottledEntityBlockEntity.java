package tech.thatgravyboat.sprout.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class BottledEntityBlockEntity extends BlockEntity {
    @Nullable
    private Entity renderEntity;

    private final Function<Level, Entity> factory;

    private CompoundTag entity;

    public BottledEntityBlockEntity(BlockEntityType<BottledEntityBlockEntity> type, BlockPos pos, BlockState state, Function<Level, Entity> factory) {
        super(type, pos, state);
        this.factory = factory;
    }

    public void setEntity(CompoundTag entity) {
        this.entity = entity;
    }

    public Optional<CompoundTag> getEntity() {
        return Optional.ofNullable(this.entity);
    }

    public Function<Level, Entity> getFactory() {
        return factory;
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("entity", Tag.TAG_COMPOUND)) {
            entity = tag.getCompound("entity");
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        if (entity != null) {
            tag.put("entity", entity);
        }
    }

    public Entity getOrCreateEntity(Level level) {
        if(renderEntity == null) {
            renderEntity = factory.apply(level);
            if (entity != null) renderEntity.load(entity);
            renderEntity.setPos(0, 0, 0);
            renderEntity.setYBodyRot(0);
            renderEntity.setXRot(0);
        }
        return renderEntity;
    }
}
