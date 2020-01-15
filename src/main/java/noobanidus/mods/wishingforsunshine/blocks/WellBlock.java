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
import noobanidus.mods.wishingforsunshine.config.ItemType;
import noobanidus.mods.wishingforsunshine.tiles.WishingWellTile;
import noobanidus.mods.wishingforsunshine.util.VoxelUtil;

import javax.annotation.Nullable;
import java.util.UUID;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class WellBlock extends Block {
  private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  private static final VoxelShape SHAPE = VoxelUtil.multiOr(Block.makeCuboidShape(0, 18.6, 6, 15, 19.6, 9), Block.makeCuboidShape(0, 17.6, 5, 15, 18.6, 7), Block.makeCuboidShape(0, 17.6, 8, 15, 18.6, 10), Block.makeCuboidShape(0, 16.6, 4, 15, 17.6, 6), Block.makeCuboidShape(0, 16.6, 9, 15, 17.6, 11), Block.makeCuboidShape(0, 15.6, 3, 15, 16.6, 5), Block.makeCuboidShape(0, 15.6, 10, 15, 16.6, 12), Block.makeCuboidShape(0, 14.6, 2, 15, 15.6, 4), Block.makeCuboidShape(0, 14.6, 11, 15, 15.6, 13), Block.makeCuboidShape(0, 13.6, 1, 15, 14.6, 3), Block.makeCuboidShape(0, 13.6, 12, 15, 14.6, 14), Block.makeCuboidShape(13, 0, 3, 16, 6, 13), Block.makeCuboidShape(0, 0, 0, 16, 6, 3), Block.makeCuboidShape(0, 0, 13, 16, 6, 16), Block.makeCuboidShape(0, 0, 3, 3, 6, 13), Block.makeCuboidShape(3, 0, 3, 13, 1, 13), Block.makeCuboidShape(3, 1, 3, 13, 2, 13), Block.makeCuboidShape(13, 6, 6, 14, 17, 9), Block.makeCuboidShape(2, 6, 6, 3, 17, 9), Block.makeCuboidShape(3, 11, 7, 13, 12, 8), Block.makeCuboidShape(3, 16, 6, 13, 17, 9));

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
        alertServer(player.getUniqueID(), result);
      }
      return true;
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
    return SHAPE;
  }
}
