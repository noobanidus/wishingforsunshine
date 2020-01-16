package noobanidus.mods.wishingforsunshine.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
  private static final ResourceLocation UID = new ResourceLocation(WishingForSunshine.MODID, WishingForSunshine.MODID);

  @Nullable
  private WellCategory wellCategory;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    IJeiHelpers jeiHelpers = registration.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    wellCategory = new WellCategory(guiHelper);
    registration.addRecipeCategories(wellCategory);
  }

  @Override
  public void register(IModRegistry registration) {
    registration.handleRecipes(WellRecipe.class, WellWrapper::new, WellCategory.UID.toString());

    registration.addRecipes(WellCategory.RECIPE_LIST.stream().filter(o -> !o.getInput().isEmpty()).collect(Collectors.toList()), WellCategory.UID.toString());

    registration.addRecipeCatalyst(new ItemStack(ModBlocks.wellItemBlock), WellCategory.UID.toString());
  }
}

