package noobanidus.mods.wishingforsunshine.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;
import noobanidus.mods.wishingforsunshine.config.EnumItemType;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WellCategory implements IRecipeCategory<WellWrapper> {
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
  public static IDrawable midday, midnight, rain, sunrise, sunset, sunshine, thunder;

  public static List<WellRecipe> RECIPE_LIST = Stream.of(EnumItemType.values()).map(WellRecipe::new).collect(Collectors.toList());

  public WellCategory(IGuiHelper guiHelper) {
    this.background = guiHelper.drawableBuilder(BACKGROUND, 0, 0, WIDTH, HEIGHT).addPadding(5, 0, 0, 0).build();
    this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.wellItemBlock));

    midday = guiHelper.drawableBuilder(MIDDAY, 0, 0, 24, 24).setTextureSize(24, 24).build();
    midnight = guiHelper.drawableBuilder(MIDNIGHT, 0, 0, 24, 24).setTextureSize(24, 24).build();
    rain = guiHelper.drawableBuilder(RAIN, 0, 0, 24, 24).setTextureSize(24, 24).build();
    sunrise = guiHelper.drawableBuilder(SUNRISE, 0, 0, 24, 24).setTextureSize(24, 24).build();
    sunset = guiHelper.drawableBuilder(SUNSET, 0, 0, 24, 24).setTextureSize(24, 24).build();
    sunshine = guiHelper.drawableBuilder(SUNSHINE, 0, 0, 24, 24).setTextureSize(24, 24).build();
    thunder = guiHelper.drawableBuilder(THUNDER, 0, 0, 24, 24).setTextureSize(24, 24).build();
  }

  @Override
  public String getModName() {
    return WishingForSunshine.MODNAME;
  }

  @Override
  public String getUid() {
    return UID.toString();
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
  public void drawExtras(Minecraft minecraft) {

  }

  @Override
  public void setRecipe(IRecipeLayout iRecipeLayout, WellWrapper wellRecipe, IIngredients iIngredients) {
    IGuiItemStackGroup group = iRecipeLayout.getItemStacks();

    group.init(0, true, 3, 10);
    group.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
  }

}

