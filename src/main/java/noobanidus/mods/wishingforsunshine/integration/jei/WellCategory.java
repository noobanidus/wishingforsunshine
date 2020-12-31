package noobanidus.mods.wishingforsunshine.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;
import noobanidus.mods.wishingforsunshine.config.ConfigManager;
import noobanidus.mods.wishingforsunshine.config.ItemType;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WellCategory implements IRecipeCategory<WellCategory.WellRecipe> {
  public static ResourceLocation UID = new ResourceLocation(WishingForSunshine.MODID, "well_category");
  public static ResourceLocation BACKGROUND = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/wishing_well_jei.png");
  public static ResourceLocation MIDDAY = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/midday.png");
  public static ResourceLocation MIDNIGHT = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/midnight.png");
  public static ResourceLocation RAIN = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/rain.png");
  public static ResourceLocation SUNRISE = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/sunrise.png");
  public static ResourceLocation SUNSET = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/sunset.png");
  public static ResourceLocation SUNSHINE = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/sunshine.png");
  public static ResourceLocation THUNDER = new ResourceLocation(WishingForSunshine.MODID, "textures/gui/thunder.png");

  public static final int WIDTH = 72, HEIGHT = 28;

  public final IDrawable background, icon;
  public final IDrawable midday, midnight, rain, sunrise, sunset, sunshine, thunder;

  public static List<WellRecipe> RECIPE_LIST = Stream.of(ItemType.values()).map(WellRecipe::new).collect(Collectors.toList());

  public WellCategory(IGuiHelper guiHelper) {
    this.background = guiHelper.drawableBuilder(BACKGROUND, 0, 0, WIDTH, HEIGHT).addPadding(5, 0, 0, 0).build();
    this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.WELL.get()));

    this.midday = guiHelper.drawableBuilder(MIDDAY, 0, 0, 24, 24).setTextureSize(24, 24).build();
    this.midnight = guiHelper.drawableBuilder(MIDNIGHT, 0, 0, 24, 24).setTextureSize(24, 24).build();
    this.rain = guiHelper.drawableBuilder(RAIN, 0, 0, 24, 24).setTextureSize(24, 24).build();
    this.sunrise = guiHelper.drawableBuilder(SUNRISE, 0, 0, 24, 24).setTextureSize(24, 24).build();
    this.sunset = guiHelper.drawableBuilder(SUNSET, 0, 0, 24, 24).setTextureSize(24, 24).build();
    this.sunshine = guiHelper.drawableBuilder(SUNSHINE, 0, 0, 24, 24).setTextureSize(24, 24).build();
    this.thunder = guiHelper.drawableBuilder(THUNDER, 0, 0, 24, 24).setTextureSize(24, 24).build();
  }

  @Override
  public ResourceLocation getUid() {
    return UID;
  }

  @Override
  public Class<? extends WellRecipe> getRecipeClass() {
    return WellRecipe.class;
  }

  @Override
  public String getTitle() {
    return I18n.format("wishingforsunshine.jei.well");
  }

  @Override
  public IDrawable getBackground() {
    return background;
  }

  @Override
  public IDrawable getIcon() {
    return icon;
  }

  @Override
  public void draw(WellRecipe recipe, MatrixStack stack, double mouseX, double mouseY) {
    IDrawable toDraw = null;

    switch (recipe.getType()) {
      case MIDDAY:
        toDraw = this.midday;
        break;
      case MIDNIGHT:
        toDraw = this.midnight;
        break;
      case RAIN:
        toDraw = this.rain;
        break;
      case SUNRISE:
        toDraw = this.sunrise;
        break;
      case SUNSET:
        toDraw = this.sunset;
        break;
      case SUNSHINE:
        toDraw = this.sunshine;
        break;
      case THUNDER:
        toDraw = this.thunder;
        break;
    }

    if (toDraw == null) {
      return;
    }

    toDraw.draw(stack, 42, 6);
    Minecraft mc = Minecraft.getInstance();
    FontRenderer fr = mc.fontRenderer;
    int stringWidth = fr.getStringWidth(recipe.getName());
    fr.drawString(stack, recipe.getName(), (float) (this.background.getWidth() - stringWidth) / 2, 0.0F, -8355712);
  }

  @Override
  public void setIngredients(WellRecipe wellRecipe, IIngredients iIngredients) {
    iIngredients.setInput(VanillaTypes.ITEM, wellRecipe.getInput());
  }

  @Override
  public void setRecipe(IRecipeLayout iRecipeLayout, WellRecipe wellRecipe, IIngredients iIngredients) {
    IGuiItemStackGroup group = iRecipeLayout.getItemStacks();

    group.init(0, true, 3, 10);
    group.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
  }

  public static class WellRecipe {
    private ItemType type;
    private String name;

    public WellRecipe(ItemType type) {
      this.type = type;
      this.name = I18n.format("wishingforsunshine.type." + type.name().toLowerCase());
    }

    public ItemType getType() {
      return type;
    }

    public ItemStack getInput() {
      return ConfigManager.getItemForType(this.type);
    }

    public String getName() {
      return name;
    }
  }
}

