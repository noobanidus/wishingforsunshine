package noobanidus.mods.wishingforsunshine;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;
import noobanidus.mods.wishingforsunshine.world.ComponentWell;
import noobanidus.mods.wishingforsunshine.world.VillageWellHandler;
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

    VillagerRegistry.instance().registerVillageCreationHandler(new VillageWellHandler());
    MapGenStructureIO.registerStructureComponent(ComponentWell.class, "wishingforsunshine:village_well");
  }
}
