package tech.thatgravyboat.sprout.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.blocks.*;
import tech.thatgravyboat.sprout.common.entities.BounceBugEntity;
import tech.thatgravyboat.sprout.common.entities.ButterFly;
import tech.thatgravyboat.sprout.common.utils.PlatformRegistryHelper;

import java.util.function.Supplier;

import static tech.thatgravyboat.sprout.common.utils.PlatformRegistryHelper.createBlockEntityType;

public class SproutBlocks {

    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(Registry.BLOCK, Sprout.MODID);
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITIES = ResourcefulRegistries.create(Registry.BLOCK_ENTITY_TYPE, Sprout.MODID);

    public static final Supplier<Block> PEANUT_PLANT_BLOCK = BLOCKS.register("peanut_plant", () -> new PeanutCrop(BlockBehaviour.Properties.copy(Blocks.CARROTS)));
    public static final Supplier<Block> CATTIAL = BLOCKS.register("cattail", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.FERN)));
    public static final Supplier<Block> DUNE_GRASS = BLOCKS.register("dune_grass", () -> new DuneGrass(BlockBehaviour.Properties.copy(Blocks.GRASS)){});
    public static final Supplier<Block> SPROUTS = BLOCKS.register("sprouts", () -> new BasicGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)){});
    public static final Supplier<Block> TALL_DEAD_BUSH = BLOCKS.register("tall_dead_bush", () -> new TallDeadBushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)));
    public static final Supplier<Block> WATER_LENTIL = BLOCKS.register("water_lentil", () -> new SpreadingWaterlilyBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noCollission()){});
    public static final Supplier<Block> BOUNCE_BUG_BOTTLE = BLOCKS.register("bounce_bug_jar", () -> new BottledEntityBlock(
            BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(1).lightLevel(value -> 3).isSuffocating((state, world, pos) -> false),
            (pos, state) -> createBounceBugJarBlockEntity().create(pos, state)
    ));
    public static final Supplier<Block> BUTTER_FLY_BOTTLE = BLOCKS.register("butterfly_jar", () -> new BottledEntityBlock(
            BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(1).lightLevel(value -> 3).isSuffocating((state, world, pos) -> false),
            (pos, state) -> createButterflyJarBlockEntity().create(pos, state)
    ));
    public static final Supplier<Block> BROWN_SHELF_FUNGI = BLOCKS.register("brown_shelf_fungi", () -> new ShelfFungiBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).instabreak().noCollission().noOcclusion()));
    public static final Supplier<Block> RED_SHELF_FUNGI = BLOCKS.register("red_shelf_fungi", () -> new ShelfFungiBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).instabreak().noCollission().noOcclusion()));
    public static final Supplier<Block> WARPED_SHELF_FUNGI = BLOCKS.register("warped_shelf_fungi", () -> new ShelfFungiBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FUNGUS).instabreak().noCollission().noOcclusion()));
    public static final Supplier<Block> CRIMSON_SHELF_FUNGI = BLOCKS.register("crimson_shelf_fungi", () -> new ShelfFungiBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FUNGUS).instabreak().noCollission().noOcclusion()));
    public static final Supplier<Block> FLOWER_BOX = BLOCKS.register("flower_box", () -> new FlowerBoxBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final Supplier<Block> PEANUT_BLOCK = BLOCKS.register("peanut_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PUMPKIN)));

    public static Supplier<BlockEntityType<BottledEntityBlockEntity>> BOUNCE_BUG_JAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("bounce_bug_block_entity", () -> createBlockEntityType(createBounceBugJarBlockEntity(), BOUNCE_BUG_BOTTLE.get()));
    public static Supplier<BlockEntityType<BottledEntityBlockEntity>> BUTTER_FLY_JAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("butter_fly_block_entity", () -> createBlockEntityType(createButterflyJarBlockEntity(), BUTTER_FLY_BOTTLE.get()));
    public static Supplier<BlockEntityType<FlowerBoxBlockEntity>> FLOWER_BOX_ENTITY = BLOCK_ENTITIES.register("flower_box", () -> createBlockEntityType(FlowerBoxBlockEntity::new, FLOWER_BOX.get()));

    public static void registerBlocks() {
        SproutFlowers.registerBlocks();

        BLOCKS.init();
        BLOCK_ENTITIES.init();
    }

    private static PlatformRegistryHelper.BlockEntityFactory<BottledEntityBlockEntity> createBounceBugJarBlockEntity() {
        return (pos, state) -> new BottledEntityBlockEntity(BOUNCE_BUG_JAR_BLOCK_ENTITY.get(), pos, state, level -> new BounceBugEntity(SproutEntities.BOUNCE_BUG_ENTITY.get(), level));
    }

    private static PlatformRegistryHelper.BlockEntityFactory<BottledEntityBlockEntity> createButterflyJarBlockEntity() {
        return (pos, state) -> new BottledEntityBlockEntity(BUTTER_FLY_JAR_BLOCK_ENTITY.get(), pos, state, level -> new ButterFly(SproutEntities.BUTTERFLY.get(), level));
    }
}
