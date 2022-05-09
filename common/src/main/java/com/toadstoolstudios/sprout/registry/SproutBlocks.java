package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.blocks.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class SproutBlocks {

    public static final Supplier<Block> PEANUT_PLANT_BLOCK = registerBlock("peanut_plant", () -> new PeanutCrop(AbstractBlock.Settings.copy(Blocks.CARROTS)));
    public static final Supplier<Block> CATTIAL = registerBlock("cattail", () -> new TallPlantBlock(AbstractBlock.Settings.copy(Blocks.FERN)));
    public static final Supplier<Block> DUNE_GRASS = registerBlock("dune_grass", () -> new DuneGrass(AbstractBlock.Settings.copy(Blocks.GRASS)){});
    public static final Supplier<Block> SPROUTS = registerBlock("sprouts", () -> new PlantBlock(AbstractBlock.Settings.copy(Blocks.GRASS)){});
    public static final Supplier<Block> TALL_DEAD_BUSH = registerBlock("tall_dead_bush", () -> new TallDeadBushBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)));
    public static final Supplier<Block> WATER_LENTIL = registerBlock("water_lentil", () -> new LilyPadBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision()){});
    public static final Supplier<Block> BOUNCE_BUG_BOTTLE = registerBlock("bounce_bug_jar", () -> new BounceBugBottle(AbstractBlock.Settings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).strength(1).luminance(value -> 3).solidBlock((state, world, pos) -> false)));
    public static final Supplier<Block> BROWN_SHELF_FUNGI = registerBlock("brown_shelf_fungi", () -> new ShelfFungiBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK).breakInstantly().noCollision().nonOpaque()));
    public static final Supplier<Block> RED_SHELF_FUNGI = registerBlock("red_shelf_fungi", () -> new ShelfFungiBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK).breakInstantly().noCollision().nonOpaque()));

    public static Supplier<BlockEntityType<BounceBugBottleBlockEntity>> BOUNCE_BUG_JAR_BLOCK_ENTITY = registerBlockEntity("bounce_bug_block_entity", () -> createBlockEntityType(BounceBugBottleBlockEntity::new, BOUNCE_BUG_BOTTLE.get()));

    public static void registerBlocks() {
        //initialize class
    }

    @ExpectPlatform
    public static Supplier<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityFactory<T> factory, Block ... block) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }
}
