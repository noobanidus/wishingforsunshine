package noobanidus.mods.wishingforsunshine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import noobanidus.mods.wishingforsunshine.config.EnumItemType;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;
import noobanidus.mods.wishingforsunshine.tiles.TileWishingWell;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockWell extends Block {
  private static final PropertyDirection FACING = BlockFurnace.FACING;

  public BlockWell() {
    super(Material.ROCK);
    this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
    this.setTranslationKey("well");
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return new AxisAlignedBB(0.05, 0.05, 0.05, 0.95, 1.2, 0.95);
  }

  private void alertServer(String source, EnumItemType result) {
    if (result != null) {
      MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
      server.getPlayerList().sendMessage(new TextComponentTranslation("wishingforsunshine.message." + result.toString().toLowerCase(), source).setStyle(new Style().setColor(TextFormatting.AQUA)));
    }
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!worldIn.isRemote) {
      TileEntity te = worldIn.getTileEntity(pos);
      if (te instanceof TileWishingWell && entityIn instanceof EntityItem) {
        EntityItem item = (EntityItem) entityIn;
        String playerName = item.getThrower();
        EnumItemType result = ((TileWishingWell) te).itemCollide((EntityItem) entityIn);
        alertServer(playerName, result);
      }
    }
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    TileEntity te = worldIn.getTileEntity(pos);
    if (te instanceof TileWishingWell) {
      EnumItemType result = ((TileWishingWell) te).itemActivated(playerIn, hand, playerIn.getHeldItem(hand));
      if (result != null) {
        if (!worldIn.isRemote) {
          alertServer(playerIn.getName(), result);
        }
        return true;
      }
    }
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TileWishingWell();
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ModBlocks.wellItemBlock;
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    this.setDefaultFacing(worldIn, pos, state);
  }

  private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      IBlockState iblockstate = worldIn.getBlockState(pos.north());
      IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
      IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
      IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
      EnumFacing enumfacing = state.getValue(FACING);

      if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
        enumfacing = EnumFacing.SOUTH;
      } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
        enumfacing = EnumFacing.NORTH;
      } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
        enumfacing = EnumFacing.EAST;
      } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
        enumfacing = EnumFacing.WEST;
      }

      worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
    }
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
  }

  @Override
  public int damageDropped(IBlockState state) {
    return 0;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.byIndex(meta);

    if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH;
    }

    return this.getDefaultState().withProperty(FACING, enumfacing);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getIndex();
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
  }

  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING);
  }
}
