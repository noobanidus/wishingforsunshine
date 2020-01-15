package noobanidus.mods.wishingforsunshine.init;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.block.SoundType;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;
import noobanidus.mods.wishingforsunshine.blocks.WellBlock;

import static noobanidus.mods.wishingforsunshine.WishingForSunshine.REGISTRATE;

@Mod.EventBusSubscriber(modid = WishingForSunshine.MODID)
public class ModBlocks {
  public static final RegistryEntry<WellBlock> WELL = REGISTRATE.block("well", WellBlock::new)
      .blockstate((ctx, p) -> p.horizontalBlock(ctx.getEntry(), p.getExistingFile(new ResourceLocation(WishingForSunshine.MODID, "block/well"))))
      .properties(o -> o.hardnessAndResistance(3f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE))
      .simpleItem()
      .recipe((ctx, p) -> ShapedRecipeBuilder.shapedRecipe(ctx.getEntry(), 1)
          .patternLine(" P ")
          .patternLine("PBP")
          .patternLine("SSS")
          .key('P', ItemTags.PLANKS)
          .key('B', Items.WATER_BUCKET)
          .key('S', ItemTags.STONE_BRICKS)
          .addCriterion("has_water", p.hasItem(Items.WATER_BUCKET))
          .build(p))
      .register();

  public static void load() {

  }
}
