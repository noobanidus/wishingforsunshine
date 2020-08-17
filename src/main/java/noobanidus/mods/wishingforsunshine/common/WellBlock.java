package noobanidus.mods.wishingforsunshine.common;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class WellBlock extends Block implements BlockEntityProvider {
  private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

  private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(Block.createCuboidShape(13, 0, 3,16, 6, 13), Block.createCuboidShape(0, 0, 0,16, 6, 3), Block.createCuboidShape(0, 0, 13,16, 6, 16), Block.createCuboidShape(0, 0, 3,3, 6, 13), Block.createCuboidShape(3, 0, 3,13, 1, 13), Block.createCuboidShape(3, 1, 3,13, 2, 13), Block.createCuboidShape(1, 18.6, 6.5,15, 19.6, 9.5), Block.createCuboidShape(1, 17.6, 5.5,15, 18.6, 7.5), Block.createCuboidShape(1, 17.6, 8.5,15, 18.6, 10.5), Block.createCuboidShape(1, 16.6, 4.5,15, 17.6, 6.5), Block.createCuboidShape(1, 16.6, 9.5,15, 17.6, 11.5), Block.createCuboidShape(1, 15.6, 3.5,15, 16.6, 5.5), Block.createCuboidShape(1, 15.6, 10.5,15, 16.6, 12.5), Block.createCuboidShape(1, 14.6, 2.5,15, 15.6, 4.5), Block.createCuboidShape(1, 14.6, 11.5,15, 15.6, 13.5), Block.createCuboidShape(1, 13.6, 1.5,15, 14.6, 3.5), Block.createCuboidShape(1, 13.6, 12.5,15, 14.6, 14.5), Block.createCuboidShape(13, 6, 6.5,14, 17, 9.5), Block.createCuboidShape(2, 6, 6.5,3, 17, 9.5), Block.createCuboidShape(3, 11, 7.5,13, 12, 8.5), Block.createCuboidShape(3, 16, 6.5,13, 17, 9.5));

  private static final VoxelShape SHAPE_WEST = VoxelShapes.union(Block.createCuboidShape(3, 0, 13,13, 6, 16), Block.createCuboidShape(13, 0, 0,16, 6, 16), Block.createCuboidShape(0, 0, 0,3, 6, 16), Block.createCuboidShape(3, 0, 0,13, 6, 3), Block.createCuboidShape(3, 0, 3,13, 1, 13), Block.createCuboidShape(3, 1, 3,13, 2, 13), Block.createCuboidShape(6.5, 18.6, 1,9.5, 19.6, 15), Block.createCuboidShape(8.5, 17.6, 1,10.5, 18.6, 15), Block.createCuboidShape(5.5, 17.6, 1,7.5, 18.6, 15), Block.createCuboidShape(9.5, 16.6, 1,11.5, 17.6, 15), Block.createCuboidShape(4.5, 16.6, 1,6.5, 17.6, 15), Block.createCuboidShape(10.5, 15.6, 1,12.5, 16.6, 15), Block.createCuboidShape(3.5, 15.6, 1,5.5, 16.6, 15), Block.createCuboidShape(11.5, 14.6, 1,13.5, 15.6, 15), Block.createCuboidShape(2.5, 14.6, 1,4.5, 15.6, 15), Block.createCuboidShape(12.5, 13.6, 1,14.5, 14.6, 15), Block.createCuboidShape(1.5, 13.6, 1,3.5, 14.6, 15), Block.createCuboidShape(6.5, 6, 13,9.5, 17, 14), Block.createCuboidShape(6.5, 6, 2,9.5, 17, 3), Block.createCuboidShape(7.5, 11, 3,8.5, 12, 13), Block.createCuboidShape(6.5, 16, 3,9.5, 17, 13));

  private static final VoxelShape SHAPE_COLLIDE = VoxelShapes.union(Block.createCuboidShape(3, 0, 13,13, 6, 16), Block.createCuboidShape(13, 0, 0,16, 6, 16), Block.createCuboidShape(0, 0, 0,3, 6, 16), Block.createCuboidShape(3, 0, 0,13, 6, 3), Block.createCuboidShape(3, 0, 3,13, 1, 13), Block.createCuboidShape(3, 1, 3,13, 2, 13));

  public WellBlock(Block.Settings settings) {
    super(settings);
    setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
  }

  @Override
  public BlockEntity createBlockEntity(BlockView world) {
    return new WellBlockEntity();
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    if (!world.isClient()) {
      BlockEntity be = world.getBlockEntity(pos);
      if (be instanceof WellBlockEntity && entity instanceof ItemEntity) {
        ItemEntity itemEntity = (ItemEntity) entity;
        PlayerEntity player = world.getPlayerByUuid(itemEntity.getThrower());
        ((WellBlockEntity) be).onEntityCollision(player, itemEntity);
      }
    }
    super.onEntityCollision(state, world, pos, entity);
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    BlockEntity be = world.getBlockEntity(pos);
    if (be instanceof WellBlockEntity) {
      ((WellBlockEntity) be).onUse(player, hand, player.getStackInHand(hand));
    }
    return super.onUse(state, world, pos, player, hand, hit);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return getDefaultState().with(FACING, ctx.getPlayerFacing());
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.with(FACING, mirror.apply(state.get(FACING)));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    Direction facing = state.get(FACING);
    return facing.getAxis() == Direction.Axis.Z ? SHAPE_NORTH : SHAPE_WEST;
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE_COLLIDE;
  }
}
