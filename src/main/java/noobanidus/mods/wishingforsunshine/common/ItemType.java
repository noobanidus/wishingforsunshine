package noobanidus.mods.wishingforsunshine.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.tag.TagRegistry;

public enum ItemType {
  RAIN,
  THUNDER,
  SUNSHINE,
  SUNRISE,
  SUNSET,
  NOON,
  MIDNIGHT;

  public static ItemType get(ItemStack stack)
  {
    for (ItemType type : values()) {
      if (type.tag.contains(stack.getItem())) {
        return type;
      }
    }
    return null;
  }

  private static Tag<Item> create(String id) {
    return TagRegistry.item(new Identifier("wishingforsunshine", id));
  }

  private final Tag<Item> tag;

  ItemType()
  {
    tag = create(name().toLowerCase());
  }
}
