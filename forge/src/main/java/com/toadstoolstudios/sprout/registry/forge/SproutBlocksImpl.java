package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import com.toadstoolstudios.sprout.registry.SproutBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SproutBlocksImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Sprout.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Sprout.MODID);

    @NotNull
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        return BLOCK_ENTITIES.register(id, blockEntity);
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(SproutBlocks.BlockEntityFactory<E> factory, Block... blocks) {
        return BlockEntityType.Builder.create(factory::create, blocks).build(null);
    }
}
