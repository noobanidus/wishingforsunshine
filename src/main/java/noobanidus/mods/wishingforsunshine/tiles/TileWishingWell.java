package noobanidus.mods.wishingforsunshine.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.storage.WorldInfo;
import noobanidus.mods.wishingforsunshine.config.EnumItemType;
import noobanidus.mods.wishingforsunshine.config.WishingConfig;
import noobanidus.mods.wishingforsunshine.util.ItemUtil;

public class TileWishingWell extends TileEntity {
  public TileWishingWell() {
  }

  public EnumItemType itemCollide(EntityItem itemEntity) {
    EnumItemType result = handleItem(itemEntity.getItem());
    if (result != null && !world.isRemote) {
      itemEntity.getItem().shrink(1);
      if (itemEntity.getItem().isEmpty()) {
        itemEntity.setDead();
      }
    }
    return result;
  }

  public EnumItemType itemActivated(EntityPlayer player, EnumHand hand, ItemStack stack) {
    EnumItemType result = handleItem(stack);
    if (result != null && !world.isRemote) {
      stack.shrink(1);
      player.setHeldItem(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);
    }
    return result;
  }

  private EnumItemType handleItem(ItemStack stack) {
    if (stack.isEmpty()) {
      return null;
    }

    EnumItemType type = getTypeFromItem(stack);
    if (type == null) {
      return null;
    }

    WorldInfo info = world.getWorldInfo();

    switch (type) {
      case RAIN:
        if (world.isRaining()) {
          return null;
        }

        if (world.isRemote) {
          return null;
        }

        info.setRaining(true);
        info.setRainTime(world.rand.nextInt(168000) + 12000);
        return type;
      case THUNDER:
        if (world.isThundering()) {
          return null;
        }

        if (world.isRemote) {
          return type;
        }

        info.setRaining(true);
        info.setThundering(true);
        info.setRainTime(world.rand.nextInt(168000) + 12000);
        info.setThunderTime(world.rand.nextInt(168000) + 12000);
        return type;
      case SUNSHINE:
        if (!world.isThundering() && !world.isRaining()) {
          return null;
        }

        if (world.isRemote) {
          return type;
        }

        info.setThundering(false);
        info.setRaining(false);
        info.setRainTime(world.rand.nextInt(168000) + 12000);
        info.setThunderTime(world.rand.nextInt(168000) + 12000);
        return type;
      case MORNING:
        if (!world.isRemote) {
          world.setWorldTime((world.getWorldTime() / 24000) * 24000);
        }

        return type;
      case SUNSET:
        if (!world.isRemote) {
          world.setWorldTime((world.getWorldTime() / 24000) * 24000 + 13800);
        }

        return type;
      case MIDDAY:
        if (!world.isRemote) {
          world.setWorldTime((world.getWorldTime() / 24000) * 24000 + 5800);
        }

        return type;
      case MIDNIGHT:
        if (!world.isRemote) {
          world.setWorldTime((world.getWorldTime() / 24000) * 24000 + 17800);
        }

        return type;
      default:
        return null;
    }
  }

  private EnumItemType getTypeFromItem (ItemStack stack) {
    for (EnumItemType type : EnumItemType.values()) {
      ItemStack typeStack = WishingConfig.getItemForType(type);
      if (ItemUtil.equalWithoutSize(stack, typeStack)) {
        return type;
      }
    }

    return null;
  }
}
