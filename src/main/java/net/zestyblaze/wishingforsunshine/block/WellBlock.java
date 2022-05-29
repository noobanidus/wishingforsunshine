package net.zestyblaze.wishingforsunshine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zestyblaze.wishingforsunshine.entity.block.WellBlockEntity;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WellBlock extends Block implements EntityBlock {
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_NORTH = Shapes.or(Block.box(13, 0, 3,16, 6, 13), Block.box(0, 0, 0,16, 6, 3), Block.box(0, 0, 13,16, 6, 16), Block.box(0, 0, 3,3, 6, 13), Block.box(3, 0, 3,13, 1, 13), Block.box(3, 1, 3,13, 2, 13), Block.box(1, 18.6, 6.5,15, 19.6, 9.5), Block.box(1, 17.6, 5.5,15, 18.6, 7.5), Block.box(1, 17.6, 8.5,15, 18.6, 10.5), Block.box(1, 16.6, 4.5,15, 17.6, 6.5), Block.box(1, 16.6, 9.5,15, 17.6, 11.5), Block.box(1, 15.6, 3.5,15, 16.6, 5.5), Block.box(1, 15.6, 10.5,15, 16.6, 12.5), Block.box(1, 14.6, 2.5,15, 15.6, 4.5), Block.box(1, 14.6, 11.5,15, 15.6, 13.5), Block.box(1, 13.6, 1.5,15, 14.6, 3.5), Block.box(1, 13.6, 12.5,15, 14.6, 14.5), Block.box(13, 6, 6.5,14, 17, 9.5), Block.box(2, 6, 6.5,3, 17, 9.5), Block.box(3, 11, 7.5,13, 12, 8.5), Block.box(3, 16, 6.5,13, 17, 9.5));
    private static final VoxelShape SHAPE_WEST = Shapes.or(Block.box(3, 0, 13,13, 6, 16), Block.box(13, 0, 0,16, 6, 16), Block.box(0, 0, 0,3, 6, 16), Block.box(3, 0, 0,13, 6, 3), Block.box(3, 0, 3,13, 1, 13), Block.box(3, 1, 3,13, 2, 13), Block.box(6.5, 18.6, 1,9.5, 19.6, 15), Block.box(8.5, 17.6, 1,10.5, 18.6, 15), Block.box(5.5, 17.6, 1,7.5, 18.6, 15), Block.box(9.5, 16.6, 1,11.5, 17.6, 15), Block.box(4.5, 16.6, 1,6.5, 17.6, 15), Block.box(10.5, 15.6, 1,12.5, 16.6, 15), Block.box(3.5, 15.6, 1,5.5, 16.6, 15), Block.box(11.5, 14.6, 1,13.5, 15.6, 15), Block.box(2.5, 14.6, 1,4.5, 15.6, 15), Block.box(12.5, 13.6, 1,14.5, 14.6, 15), Block.box(1.5, 13.6, 1,3.5, 14.6, 15), Block.box(6.5, 6, 13,9.5, 17, 14), Block.box(6.5, 6, 2,9.5, 17, 3), Block.box(7.5, 11, 3,8.5, 12, 13), Block.box(6.5, 16, 3,9.5, 17, 13));
    private static final VoxelShape SHAPE_COLLIDE = Shapes.or(Block.box(3, 0, 13,13, 6, 16), Block.box(13, 0, 0,16, 6, 16), Block.box(0, 0, 0,3, 6, 16), Block.box(3, 0, 0,13, 6, 3), Block.box(3, 0, 3,13, 1, 13), Block.box(3, 1, 3,13, 2, 13));

    public WellBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WellBlockEntity(pos, state);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof WellBlockEntity && entity instanceof ItemEntity itemEntity) {
                if(itemEntity.getThrower() == null) return;

                Player player = level.getPlayerByUUID(itemEntity.getThrower());
                ((WellBlockEntity)be).onEntityCollision(player, itemEntity);
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof WellBlockEntity) {
            ((WellBlockEntity) be).onUse(player, hand, player.getItemInHand(hand));
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateDefinition().any().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return facing.getAxis() == Direction.Axis.Z ? SHAPE_NORTH : SHAPE_WEST;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_COLLIDE;
    }
}
