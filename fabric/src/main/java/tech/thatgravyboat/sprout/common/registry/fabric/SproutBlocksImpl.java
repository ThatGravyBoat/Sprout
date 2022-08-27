package tech.thatgravyboat.sprout.common.registry.fabric;

import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutBlocksImpl {
    @NotNull
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        var register = Registry.register(Registry.BLOCK, new ResourceLocation(Sprout.MODID, name), blockSupplier.get());
        return () -> register;
    }

    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        var register = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Sprout.MODID, id), blockEntity.get());
        return () -> register;
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(SproutBlocks.BlockEntityFactory<E> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory::create, blocks).build();
    }
}
