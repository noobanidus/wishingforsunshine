package noobanidus.mods.wishingforsunshine.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid= WishingForSunshine.MODID)
@Config(modid = WishingForSunshine.MODID)
public class WishingConfig {
  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(WishingForSunshine.MODID)) {
      ConfigManager.sync(WishingForSunshine.MODID, Config.Type.INSTANCE);
    }
  }

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to START rain (leave blank to disable this feature)")
  public static String RAIN_START = "minecraft:red_flower:1";

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to STOP rain and thunderstorms (leave blank to disable this feature)")
  public static String RAIN_THUNDER_STOP = "minecraft:double_plant:0";

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to START thunderstorms (leave blank to disable this feature)")
  public static String THUNDER_START = "minecraft:gold_block";

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to CHANGE TIME to morning (leave blank to disable this feature)")
  public static String MORNING = "minecraft:gold_ingot";

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to CHANGE TIME to midday (leave blank to disable this feature)")
  public static String MIDDAY = "";

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to CHANGE TIME to sunset (leave blank to disable this feature)")
  public static String SUNSET = "";

  @Config.Comment("String that describes the ItemStack (modname:itemid:meta) used to CHANGE TIME to midnight (leave blank to disable this feature)")
  public static String MIDNIGHT = "minecraft:iron_ingot";

  public static ItemStack getItemForType(EnumItemType type) {
    switch (type) {
      case RAIN:
        return getItemStack(RAIN_START);
      case THUNDER:
        return getItemStack(THUNDER_START);
      case SUNSHINE:
        return getItemStack(RAIN_THUNDER_STOP);
      case MORNING:
        return getItemStack(MORNING);
      case MIDDAY:
        return getItemStack(MIDDAY);
      case SUNSET:
        return getItemStack(SUNSET);
      case MIDNIGHT:
        return getItemStack(MIDNIGHT);
      default:
        return ItemStack.EMPTY;
    }
  }

  @Config.Ignore
  private static Map<String, ItemStack> cache = new HashMap<>();

  private static ItemStack getItemStack(String input) {
    ItemStack potential = cache.get(input);
    if (potential != null) {
      return potential.copy();
    }

    String[] split = input.split(":");
    Item item = null;
    int meta;
    if (split.length == 1) {
      return ItemStack.EMPTY;
    }
    if (split.length >= 2) {
      item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0], split[1]));
    }
    if (split.length == 3) {
      meta = Integer.parseInt(split[2]);
    } else {
      meta = 0;
    }
    if (item == null) {
      return ItemStack.EMPTY;
    }
    potential = new ItemStack(item, 1, meta);
    cache.put(input, potential);
    return potential.copy();
  }
}
