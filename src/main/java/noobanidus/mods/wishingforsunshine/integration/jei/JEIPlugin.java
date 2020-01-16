package noobanidus.mods.wishingforsunshine.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@JeiPlugin
public class JEIPlugin implements IModPlugin {
  private static final ResourceLocation UID = new ResourceLocation(WishingForSunshine.MODID, WishingForSunshine.MODID);

  @Nullable
  private WellCategory wellCategory;

  @Override
  public ResourceLocation getPluginUid() {
    return UID;
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    IJeiHelpers jeiHelpers = registration.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    wellCategory = new WellCategory(guiHelper);
    registration.addRecipeCategories(wellCategory);
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    registration.addRecipes(WellCategory.RECIPE_LIST.stream().filter(o -> !o.getInput().isEmpty()).collect(Collectors.toCollection(ArrayList::new)), WellCategory.UID);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    registration.addRecipeCatalyst(new ItemStack(ModBlocks.WELL.get()), WellCategory.UID);
  }
}

