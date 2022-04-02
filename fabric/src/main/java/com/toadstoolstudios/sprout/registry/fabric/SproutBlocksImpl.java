package com.toadstoolstudios.sprout.registry.fabric;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutBlocksImpl {
    @NotNull
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        var register = Registry.register(Registry.BLOCK, new Identifier(Sprout.MODID, name), blockSupplier.get());
        return () -> register;
    }

    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        var register = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Sprout.MODID, id), blockEntity.get());
        return () -> register;
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(SproutBlocks.BlockEntityFactory<E> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory::create, blocks).build();
    }
}
