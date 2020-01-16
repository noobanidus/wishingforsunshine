package noobanidus.mods.wishingforsunshine;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import noobanidus.mods.wishingforsunshine.config.ConfigManager;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;
import noobanidus.mods.wishingforsunshine.init.ModLang;
import noobanidus.mods.wishingforsunshine.init.ModTiles;
import noobanidus.mods.wishingforsunshine.setup.ClientSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("wishingforsunshine")
@SuppressWarnings("WeakerAccess")
public class WishingForSunshine {
  public static final String MODID = "wishingforsunshine";
  public static final String MODNAME = "Wishing for Sunshine";
  public static final String VERSION = "GRADLE:VERSION";

  public static final ItemGroup GROUP = new ItemGroup(MODID) {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModBlocks.WELL.get());
    }
  };

  public static Logger LOG = LogManager.getLogger();

  public static Registrate REGISTRATE;

  public WishingForSunshine() {
    ConfigManager.loadConfig(ConfigManager.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-common.toml"));

    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    REGISTRATE = Registrate.create(MODID);
    REGISTRATE.itemGroup(NonNullSupplier.of(() -> GROUP));
    ModBlocks.load();
    ModLang.load();
    ModTiles.load();

    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
      modBus.addListener(ClientSetup::init);
    });
  }
}
