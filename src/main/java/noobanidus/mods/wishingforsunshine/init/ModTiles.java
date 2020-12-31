package noobanidus.mods.wishingforsunshine.init;


import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraft.tileentity.TileEntityType;
import noobanidus.mods.wishingforsunshine.tiles.WishingWellTile;

import static noobanidus.mods.wishingforsunshine.WishingForSunshine.REGISTRATE;

public class ModTiles {
  public static final TileEntityEntry<WishingWellTile> WISHING_WELL = REGISTRATE.tileEntity("well", WishingWellTile::new).validBlock(ModBlocks.WELL).register();

  public static void load() {

  }
}
