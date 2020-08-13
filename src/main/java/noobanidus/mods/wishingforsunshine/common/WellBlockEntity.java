package noobanidus.mods.wishingforsunshine.common;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.level.ServerWorldProperties;

import noobanidus.mods.wishingforsunshine.WishingForSunshine;

public class WellBlockEntity extends BlockEntity {

  public WellBlockEntity() {
    super(WishingForSunshine.WELL_BLOCK_ENTITY);
  }

  public void onEntityCollision(PlayerEntity player, ItemEntity itemEntity) {
    if (world != null && !world.isClient()) {
      ItemType result = handleItem(itemEntity.getStack());
      if (result != null) {
        itemEntity.getStack().decrement(1);
        if (itemEntity.getStack().isEmpty()) {
          itemEntity.remove();
        }
        if (player != null) {
          player.sendMessage(new TranslatableText(WishingForSunshine.MODID + ".message." + result.name().toLowerCase(), player.getDisplayName()).setStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
        }
      }
    }
  }

  public void onUse(PlayerEntity player, Hand hand, ItemStack stack) {
    if (world != null && !world.isClient()) {
      ItemType result = handleItem(stack);
      if (result != null) {
        stack.decrement(1);
        player.setStackInHand(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);
        player.sendMessage(new TranslatableText(WishingForSunshine.MODID + ".message." + result.name().toLowerCase(), player.getDisplayName()).setStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
      }
    }
  }

  private ItemType handleItem(ItemStack stack) {
    if (!stack.isEmpty() && world != null) {
      if (world.getLevelProperties() instanceof ServerWorldProperties) {
        ServerWorldProperties properties = (ServerWorldProperties) world.getLevelProperties();
        ItemType resultType = ItemType.get(stack);
        if (resultType != null) {
          switch (resultType) {
            case RAIN:
              if (!properties.isRaining()) {
                properties.setRaining(true);
                properties.setRainTime(world.getRandom().nextInt(168000) + 12000);
                return resultType;
              }
              return null;
            case THUNDER:
              if (!properties.isThundering()) {
                properties.setRaining(true);
                properties.setThundering(true);
                properties.setRainTime(world.getRandom().nextInt(168000) + 12000);
                properties.setThunderTime(world.getRandom().nextInt(168000) + 12000);
                return resultType;
              }
              return null;
            case SUNSHINE:
              if (properties.isRaining() || properties.isThundering()) {
                properties.setRaining(false);
                properties.setThundering(false);
                properties.setRainTime(world.getRandom().nextInt(168000) + 12000);
                properties.setThunderTime(world.getRandom().nextInt(168000) + 12000);
                return resultType;
              }
              return null;
            case SUNRISE:
              properties.setTimeOfDay(0);
              return resultType;
            case SUNSET:
              properties.setTimeOfDay(12400);
              return resultType;
            case NOON:
              properties.setTimeOfDay(5800);
              return resultType;
            case MIDNIGHT:
              properties.setTimeOfDay(17800);
              return resultType;
          }
        }
      }
    }
    return null;
  }
}
