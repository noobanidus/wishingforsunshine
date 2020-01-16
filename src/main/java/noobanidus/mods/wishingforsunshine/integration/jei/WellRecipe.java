package noobanidus.mods.wishingforsunshine.integration.jei;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import noobanidus.mods.wishingforsunshine.config.EnumItemType;
import noobanidus.mods.wishingforsunshine.config.WishingConfig;

public class WellRecipe {
  private EnumItemType type;
  private String name;

  public WellRecipe(EnumItemType type) {
    this.type = type;
    this.name = I18n.format("wishingforsunshine.type." + type.name().toLowerCase());
  }

  public EnumItemType getType() {
    return type;
  }

  public ItemStack getInput() {
    return WishingConfig.getItemForType(this.type);
  }

  public String getName() {
    return name;
  }
}
