package tech.thatgravyboat.sprout.common.blocks;

import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.sprout.common.registry.SproutBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShelfFungiBlock extends BushBlock {

    private static final VoxelShape EAST_SHAPE = Block.box(1, 1, 0, 8, 15, 15);
    private static final VoxelShape WEST_SHAPE = Block.box(8, 1, 1, 16, 15, 15);
    private static final VoxelShape SOUTH_SHAPE = Block.box(1, 1, 0, 15, 15, 8);
    private static final VoxelShape NORTH_SHAPE = Block.box(1, 1, 8, 15, 15, 16);
    public static DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ShelfFungiBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        Level worldView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();

        for (Direction direction : ctx.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction2 = direction.getOpposite();
                blockState = blockState.setValue(FACING, direction2);
                if (blockState.canSurvive(worldView, blockPos)) {
                    return blockState;
                }
            }
        }

        return null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).isSolidRender(level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public ItemStack getCloneItemStack(@NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.asBlock() == SproutBlocks.RED_SHELF_FUNGI.get()) {
            return new ItemStack(Items.RED_MUSHROOM);
        }
        if (this.asBlock() == SproutBlocks.BROWN_SHELF_FUNGI.get()) {
            return new ItemStack(Items.BROWN_MUSHROOM);
        }
        return ItemStack.EMPTY;
    }
}
