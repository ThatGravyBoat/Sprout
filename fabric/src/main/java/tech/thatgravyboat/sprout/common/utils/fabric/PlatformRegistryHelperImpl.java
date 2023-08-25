package tech.thatgravyboat.sprout.common.utils.fabric;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import tech.thatgravyboat.sprout.common.utils.PlatformRegistryHelper;

import java.util.function.Supplier;

public class PlatformRegistryHelperImpl {
    public static <T extends Mob> SpawnEggItem createSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        return new SpawnEggItem(entity.get(), primaryColor, secondaryColor, properties);
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(PlatformRegistryHelper.BlockEntityFactory<T> factory, Block... block) {
        return FabricBlockEntityTypeBuilder.create(factory::create, block).build();
    }
}
