package tech.thatgravyboat.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.blocks.*;

import java.util.function.Supplier;

public class SproutBlocks {

    public static final Supplier<Block> PEANUT_PLANT_BLOCK = registerBlock("peanut_plant", () -> new PeanutCrop(BlockBehaviour.Properties.copy(Blocks.CARROTS)));
    public static final Supplier<Block> CATTIAL = registerBlock("cattail", () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.FERN)));
    public static final Supplier<Block> DUNE_GRASS = registerBlock("dune_grass", () -> new DuneGrass(BlockBehaviour.Properties.copy(Blocks.GRASS)){});
    public static final Supplier<Block> SPROUTS = registerBlock("sprouts", () -> new BasicGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)){});
    public static final Supplier<Block> TALL_DEAD_BUSH = registerBlock("tall_dead_bush", () -> new TallDeadBushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)));
    public static final Supplier<Block> WATER_LENTIL = registerBlock("water_lentil", () -> new WaterlilyBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noCollission()){});
    public static final Supplier<Block> BOUNCE_BUG_BOTTLE = registerBlock("bounce_bug_jar", () -> new BounceBugBottle(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(1).lightLevel(value -> 3).isSuffocating((state, world, pos) -> false)));
    public static final Supplier<Block> BROWN_SHELF_FUNGI = registerBlock("brown_shelf_fungi", () -> new ShelfFungiBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).instabreak().noCollission().noOcclusion()));
    public static final Supplier<Block> RED_SHELF_FUNGI = registerBlock("red_shelf_fungi", () -> new ShelfFungiBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instabreak().noCollission().noOcclusion()));

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
        @NotNull T create(BlockPos pos, BlockState state);
    }
}
