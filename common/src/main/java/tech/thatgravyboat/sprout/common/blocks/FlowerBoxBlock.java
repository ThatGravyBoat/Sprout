package tech.thatgravyboat.sprout.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.sprout.common.flowers.FlowerBreed;
import tech.thatgravyboat.sprout.common.flowers.FlowerBreedTree;
import tech.thatgravyboat.sprout.mixin.FlowerPotBlockAccessor;

@SuppressWarnings("deprecation")
public class FlowerBoxBlock extends BaseEntityBlock implements BonemealableBlock {

    public static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16, 8, 16);

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public FlowerBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Level level = context.getLevel();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos nextPos = context.getClickedPos().relative(direction);
            BlockState nextState = level.getBlockState(nextPos);
            if (!nextState.is(this)) continue;
            Direction.Axis axis = getAxis(nextState);
            if (direction.getAxis() != axis && axis != null) continue;
            BlockState state = this.defaultBlockState();
            switch (direction) {
                case SOUTH -> state = state.setValue(SOUTH, true);
                case NORTH -> state = state.setValue(NORTH, true);
                case EAST -> state = state.setValue(EAST, true);
                case WEST -> state = state.setValue(WEST, true);
            }
            BlockState oppositeState = level.getBlockState(context.getClickedPos().relative(direction.getOpposite()));
            if (oppositeState.is(this) && (getAxis(oppositeState) == null || getAxis(oppositeState) == direction.getAxis())) {
                switch (direction.getOpposite()) {
                    case SOUTH -> state = state.setValue(SOUTH, true);
                    case NORTH -> state = state.setValue(NORTH, true);
                    case EAST -> state = state.setValue(EAST, true);
                    case WEST -> state = state.setValue(WEST, true);
                }
            }
            return state;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos pos2, boolean bl) {
        super.neighborChanged(state, level, pos, block, pos2, bl);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!pos2.equals(pos.relative(direction))) continue;
            Direction.Axis axis = getAxis(state);
            if (direction.getAxis() != axis && axis != null) return;
            boolean connected = level.getBlockState(pos2).getBlock() instanceof FlowerBoxBlock;
            switch (direction) {
                case NORTH -> level.setBlock(pos, state.setValue(NORTH, connected), Block.UPDATE_CLIENTS);
                case SOUTH -> level.setBlock(pos, state.setValue(SOUTH, connected), Block.UPDATE_CLIENTS);
                case EAST -> level.setBlock(pos, state.setValue(EAST, connected), Block.UPDATE_CLIENTS);
                case WEST -> level.setBlock(pos, state.setValue(WEST, connected), Block.UPDATE_CLIENTS);
            }
            return;
        }
    }

    public static Direction.Axis getAxis(BlockState state) {
        if (state.getValue(NORTH) || state.getValue(SOUTH)) return Direction.Axis.Z;
        if (state.getValue(EAST) || state.getValue(WEST)) return Direction.Axis.X;
        return null;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        ItemStack item = player.getItemInHand(hand);

        if (level.getBlockEntity(pos) instanceof FlowerBoxBlockEntity flowerBox) {
            if (flowerBox.getFlower() == null && item.getItem() instanceof BlockItem block && FlowerPotBlockAccessor.getFlowerPotMap().containsKey(block.getBlock())) {
                flowerBox.setFlower(block.getBlock());
                if (!player.getAbilities().instabuild) item.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                if (flowerBox.getFlower() != null) {
                    ItemStack stack = new ItemStack(flowerBox.getFlower());
                    flowerBox.setFlower(null);

                    if (item.isEmpty()) player.setItemInHand(hand, stack);
                    else if (!player.addItem(stack)) player.drop(stack, false);

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return super.use(state, level, pos, player, hand, result);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        // Only tick the middle block.
        return (state.getValue(NORTH) && state.getValue(SOUTH)) || (state.getValue(EAST) && state.getValue(WEST));
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        performBonemeal(level, random, pos, state);
    }

    private static void setRandomFlower(BlockPos pos, ServerLevel level, Direction left, Direction right) {
        Block flower1 = FlowerBoxBlockEntity.getFlower(level.getBlockEntity(pos.relative(left)));
        Block flower2 = FlowerBoxBlockEntity.getFlower(level.getBlockEntity(pos.relative(right)));
        if (level.getBlockEntity(pos) instanceof FlowerBoxBlockEntity flowerBox && flower1 != null && flower2 != null) {
            FlowerBreed breed = FlowerBreed.of(flower1, flower2);
            if (FlowerBreedTree.BREEDS.containsKey(breed)) {
                flowerBox.setFlower(FlowerBreedTree.BREEDS.get(breed).next());
            }
        }
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean bl) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FlowerBoxBlockEntity flowerBox && flowerBox.getFlower() != null) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(flowerBox.getFlower()));
            }
            super.onRemove(state, level, pos, newState, bl);
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FlowerBoxBlockEntity(pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean bl) {
        return level.getBlockEntity(pos) instanceof FlowerBoxBlockEntity flowerBox && flowerBox.getFlower() == null;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (level.getBlockEntity(pos) instanceof FlowerBoxBlockEntity flowerBox && flowerBox.getFlower() == null) {
            return random.nextInt(5) == 0;
        }
        return false;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (FlowerBoxBlockEntity.getFlower(level.getBlockEntity(pos)) != null) return;
        Direction.Axis axis = getAxis(state);
        if (axis == null) return;
        switch (axis) {
            case X -> setRandomFlower(pos, level, Direction.WEST, Direction.EAST);
            case Z -> setRandomFlower(pos, level, Direction.SOUTH, Direction.NORTH);
        }
    }
}
