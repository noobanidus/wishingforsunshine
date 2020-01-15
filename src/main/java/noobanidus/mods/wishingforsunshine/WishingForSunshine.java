package noobanidus.mods.wishingforsunshine;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = WishingForSunshine.MODID, name = WishingForSunshine.MODNAME, version = WishingForSunshine.VERSION)
@SuppressWarnings("WeakerAccess")
public class WishingForSunshine {
  public static final String MODID = "wishingforsunshine";
  public static final String MODNAME = "Wishing for Sunshine";
  public static final String VERSION = "GRADLE:VERSION";

  public static final CreativeTabs TAB = new CreativeTabs(MODID) {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModBlocks.wellItemBlock);
    }
  };

  public static Logger LOG;

  @SuppressWarnings("unused")
  @Mod.Instance(WishingForSunshine.MODID)
  public static WishingForSunshine instance;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    LOG = event.getModLog();
  }
}
