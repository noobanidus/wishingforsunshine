package noobanidus.mods.wishingforsunshine.tiles;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.storage.WorldInfo;
import noobanidus.mods.wishingforsunshine.config.ConfigManager;
import noobanidus.mods.wishingforsunshine.config.ItemType;
import noobanidus.mods.wishingforsunshine.init.ModTiles;

public class WishingWellTile extends TileEntity {
  public WishingWellTile() {
    super(ModTiles.WISHING_WELL.get());
  }

  public ItemType itemCollide(ItemEntity itemEntity) {
    ItemType result = handleItem(itemEntity.getItem());
    if (result != null && world != null && !world.isRemote) {
      itemEntity.getItem().shrink(1);
      if (itemEntity.getItem().isEmpty()) {
        itemEntity.remove();
      }
    }
    return result;
  }

  public ItemType itemActivated(PlayerEntity player, Hand hand, ItemStack stack) {
    ItemType result = handleItem(stack);
    if (result != null && world != null && !world.isRemote) {
      stack.shrink(1);
      player.setHeldItem(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);
    }
    return result;
  }

  private ItemType handleItem(ItemStack stack) {
    if (stack.isEmpty() || world == null) {
      return null;
    }

    ItemType type = ConfigManager.getTypeForItem(stack);
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
      case SUNRISE:
        if (!world.isRemote) {
          world.setDayTime(0);
        }

        return type;
      case SUNSET:
        if (!world.isRemote) {
          world.setDayTime(12400);
        }

        return type;
      case MIDDAY:
        if (!world.isRemote) {
          world.setDayTime(5800);
        }

        return type;
      case MIDNIGHT:
        if (!world.isRemote) {
          world.setDayTime(17800);
        }

        return type;
      default:
        return null;
    }
  }
}
