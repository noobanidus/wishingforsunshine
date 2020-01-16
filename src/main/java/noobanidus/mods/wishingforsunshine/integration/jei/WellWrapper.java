package noobanidus.mods.wishingforsunshine.integration.jei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class WellWrapper implements IRecipeWrapper {
  public WellRecipe recipe;

  public WellWrapper(WellRecipe recipe) {
    this.recipe = recipe;
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInput(VanillaTypes.ITEM, this.recipe.getInput());
  }

  @Override
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    IDrawable toDraw = null;

    switch (recipe.getType()) {
      case MIDDAY:
        toDraw = WellCategory.midday;
        break;
      case MIDNIGHT:
        toDraw = WellCategory.midnight;
        break;
      case RAIN:
        toDraw = WellCategory.rain;
        break;
      case SUNRISE:
        toDraw = WellCategory.sunrise;
        break;
      case SUNSET:
        toDraw = WellCategory.sunset;
        break;
      case SUNSHINE:
        toDraw = WellCategory.sunshine;
        break;
      case THUNDER:
        toDraw = WellCategory.thunder;
        break;
    }

    if (toDraw == null) {
      return;
    }

    Minecraft mc = Minecraft.getMinecraft();
    toDraw.draw(mc, 42, 6);
    FontRenderer fr = mc.fontRenderer;
    int stringWidth = fr.getStringWidth(recipe.getName());
    fr.drawString(recipe.getName(), (72 - stringWidth) / 2, 0, -8355712);
  }
}
