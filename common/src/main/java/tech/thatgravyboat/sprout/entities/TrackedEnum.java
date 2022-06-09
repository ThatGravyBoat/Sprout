package tech.thatgravyboat.sprout.entities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

public record TrackedEnum<T extends Enum<T>>(Class<T> enumClass) implements EntityDataSerializer<T> {

    @Override
    public void write(FriendlyByteBuf buf, @NotNull T value) {
        buf.writeEnum(value);
    }

    @Override
    public T read(FriendlyByteBuf buf) {
        return buf.readEnum(enumClass);
    }

    @Override
    public T copy(@NotNull T value) {
        return value;
    }
}