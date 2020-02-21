package noobanidus.mods.wishingforsunshine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import noobanidus.libs.noobutil.util.VoxelUtil;
import noobanidus.mods.wishingforsunshine.config.ItemType;
import noobanidus.mods.wishingforsunshine.tiles.WishingWellTile;

import javax.annotation.Nullable;
import java.util.UUID;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class WellBlock extends Block {
  private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  private static final VoxelShape SHAPE_NORTH = VoxelUtil.multiOr(Block.makeCuboidShape(13, 0, 3,16, 6, 13), Block.makeCuboidShape(0, 0, 0,16, 6, 3), Block.makeCuboidShape(0, 0, 13,16, 6, 16), Block.makeCuboidShape(0, 0, 3,3, 6, 13), Block.makeCuboidShape(3, 0, 3,13, 1, 13), Block.makeCuboidShape(3, 1, 3,13, 2, 13), Block.makeCuboidShape(1, 18.6, 6.5,15, 19.6, 9.5), Block.makeCuboidShape(1, 17.6, 5.5,15, 18.6, 7.5), Block.makeCuboidShape(1, 17.6, 8.5,15, 18.6, 10.5), Block.makeCuboidShape(1, 16.6, 4.5,15, 17.6, 6.5), Block.makeCuboidShape(1, 16.6, 9.5,15, 17.6, 11.5), Block.makeCuboidShape(1, 15.6, 3.5,15, 16.6, 5.5), Block.makeCuboidShape(1, 15.6, 10.5,15, 16.6, 12.5), Block.makeCuboidShape(1, 14.6, 2.5,15, 15.6, 4.5), Block.makeCuboidShape(1, 14.6, 11.5,15, 15.6, 13.5), Block.makeCuboidShape(1, 13.6, 1.5,15, 14.6, 3.5), Block.makeCuboidShape(1, 13.6, 12.5,15, 14.6, 14.5), Block.makeCuboidShape(13, 6, 6.5,14, 17, 9.5), Block.makeCuboidShape(2, 6, 6.5,3, 17, 9.5), Block.makeCuboidShape(3, 11, 7.5,13, 12, 8.5), Block.makeCuboidShape(3, 16, 6.5,13, 17, 9.5));

  private static final VoxelShape SHAPE_WEST = VoxelUtil.multiOr(Block.makeCuboidShape(3, 0, 13,13, 6, 16), Block.makeCuboidShape(13, 0, 0,16, 6, 16), Block.makeCuboidShape(0, 0, 0,3, 6, 16), Block.makeCuboidShape(3, 0, 0,13, 6, 3), Block.makeCuboidShape(3, 0, 3,13, 1, 13), Block.makeCuboidShape(3, 1, 3,13, 2, 13), Block.makeCuboidShape(6.5, 18.6, 1,9.5, 19.6, 15), Block.makeCuboidShape(8.5, 17.6, 1,10.5, 18.6, 15), Block.makeCuboidShape(5.5, 17.6, 1,7.5, 18.6, 15), Block.makeCuboidShape(9.5, 16.6, 1,11.5, 17.6, 15), Block.makeCuboidShape(4.5, 16.6, 1,6.5, 17.6, 15), Block.makeCuboidShape(10.5, 15.6, 1,12.5, 16.6, 15), Block.makeCuboidShape(3.5, 15.6, 1,5.5, 16.6, 15), Block.makeCuboidShape(11.5, 14.6, 1,13.5, 15.6, 15), Block.makeCuboidShape(2.5, 14.6, 1,4.5, 15.6, 15), Block.makeCuboidShape(12.5, 13.6, 1,14.5, 14.6, 15), Block.makeCuboidShape(1.5, 13.6, 1,3.5, 14.6, 15), Block.makeCuboidShape(6.5, 6, 13,9.5, 17, 14), Block.makeCuboidShape(6.5, 6, 2,9.5, 17, 3), Block.makeCuboidShape(7.5, 11, 3,8.5, 12, 13), Block.makeCuboidShape(6.5, 16, 3,9.5, 17, 13));

  private static final VoxelShape SHAPE_COLLIDE = VoxelUtil.multiOr(Block.makeCuboidShape(3, 0, 13,13, 6, 16), Block.makeCuboidShape(13, 0, 0,16, 6, 16), Block.makeCuboidShape(0, 0, 0,3, 6, 16), Block.makeCuboidShape(3, 0, 0,13, 6, 3), Block.makeCuboidShape(3, 0, 3,13, 1, 13), Block.makeCuboidShape(3, 1, 3,13, 2, 13));

  public WellBlock(Block.Properties properties) {
    super(properties);
    this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
  }

  private void alertServer(UUID source, ItemType result) {
    if (result != null) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      ServerPlayerEntity player = server.getPlayerList().getPlayerByUUID(source);
      if (player != null) {
        server.getPlayerList().sendMessage(new TranslationTextComponent("wishingforsunshine.message." + result.toString().toLowerCase(), player.getDisplayName()).setStyle(new Style().setColor(TextFormatting.AQUA)));
      }
    }
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    if (!world.isRemote) {
      TileEntity te = world.getTileEntity(pos);
      if (te instanceof WishingWellTile && entity instanceof ItemEntity) {
        ItemEntity item = (ItemEntity) entity;
        UUID playerID = item.getThrowerId();
        ItemType result = ((WishingWellTile) te).itemCollide(item);
        if (playerID != null) {
          alertServer(playerID, result);
        }
      }
    }
  }

  @Override
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
    TileEntity te = world.getTileEntity(pos);
    if (te instanceof WishingWellTile) {
      ItemType result = ((WishingWellTile) te).itemActivated(player, hand, player.getHeldItem(hand));
      if (result != null) {
        if (!world.isRemote()) {
          alertServer(player.getUniqueID(), result);
        }
        return true;
      }
    }

    return super.onBlockActivated(state, world, pos, player, hand, trace);
  }


  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new WishingWellTile();
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return state.rotate(mirror.toRotation(state.get(FACING)));
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public BlockRenderType getRenderType(BlockState p_149645_1_) {
    return BlockRenderType.MODEL;
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    Direction facing = state.get(FACING);
    if (facing == Direction.NORTH || facing == Direction.SOUTH) {
      return SHAPE_NORTH;
    } else {
      return SHAPE_WEST;
    }
  }

  @Override
  public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
    return SHAPE_COLLIDE;
  }
}
