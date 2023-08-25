package tech.thatgravyboat.sprout.common.utils;

import com.google.common.collect.ImmutableSet;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlatformRegistryHelper {

    public static <E extends Entity> EntityType<E> createEntityType(EntityType.EntityFactory<E> factory, MobCategory category, EntityDimensions dimensions) {
        return new EntityType<>(
                factory,
                category,
                true,
                true,
                false,
                category == MobCategory.CREATURE || category == MobCategory.MISC,
                ImmutableSet.of(),
                dimensions,
                5,
                3
        );
    }

    @ExpectPlatform
    public static <T extends Mob> SpawnEggItem createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityFactory<T> factory, Block... block) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> {
        @NotNull T create(BlockPos pos, BlockState state);
    }
}
