package noobanidus.mods.wishingforsunshine.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;
import noobanidus.mods.wishingforsunshine.blocks.BlockWell;
import noobanidus.mods.wishingforsunshine.tiles.TileWishingWell;

@Mod.EventBusSubscriber(modid= WishingForSunshine.MODID)
public class ModBlocks {
  public static ResourceLocation WELL = new ResourceLocation(WishingForSunshine.MODID, "well");
  public static final BlockWell well = new BlockWell();
  public static final ItemBlock wellItemBlock = new ItemBlock(well);

  static {
    well.setRegistryName(WELL);
    wellItemBlock.setRegistryName(WELL);
    well.setCreativeTab(WishingForSunshine.TAB);
    wellItemBlock.setCreativeTab(WishingForSunshine.TAB);
  }

  @SubscribeEvent
  public static void onBlockRegistry (RegistryEvent.Register<Block> event) {
    event.getRegistry().register(well);

    GameRegistry.registerTileEntity(TileWishingWell.class, WELL);
  }

  @SubscribeEvent
  public static void onItemRegistry (RegistryEvent.Register<Item> event) {
    event.getRegistry().register(wellItemBlock);
  }

  @SubscribeEvent
  public static void onRegisterModels (ModelRegistryEvent event) {
    ModelLoader.setCustomModelResourceLocation(wellItemBlock, 0, new ModelResourceLocation(WELL, "inventory"));
  }
}
