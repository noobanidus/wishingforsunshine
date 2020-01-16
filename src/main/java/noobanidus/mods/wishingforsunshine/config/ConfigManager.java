package noobanidus.mods.wishingforsunshine.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.wishingforsunshine.util.ItemUtil;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigManager {
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

  public static ForgeConfigSpec COMMON_CONFIG;
  private static Map<ItemType, ForgeConfigSpec.ConfigValue<String>> MAP = new HashMap<>();

  private static Map<ItemType, String> DEFAULTS = new HashMap<>();
  private static Map<ItemType, ItemStack> CACHE = new HashMap<>();

  static {
    DEFAULTS.put(ItemType.RAIN, Objects.requireNonNull(Items.BLUE_ORCHID.getRegistryName()).toString());
    DEFAULTS.put(ItemType.SUNSHINE, Objects.requireNonNull(Items.SUNFLOWER.getRegistryName()).toString());
    DEFAULTS.put(ItemType.SUNSET, Objects.requireNonNull(Items.IRON_INGOT.getRegistryName()).toString());
    DEFAULTS.put(ItemType.SUNRISE, Objects.requireNonNull(Items.GOLD_INGOT.getRegistryName()).toString());

    for (ItemType type : ItemType.values()) {
      String def = DEFAULTS.getOrDefault(type, "");
      MAP.put(type, COMMON_BUILDER.comment("Item to trigger the " + type.name().toLowerCase() + " effect (registry name, leave blank for no default)").define(type.name().toLowerCase(), def));
    }

    COMMON_CONFIG = COMMON_BUILDER.build();
  }

  public static void loadConfig(ForgeConfigSpec spec, Path path) {
    CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
    configData.load();
    spec.setConfig(configData);
  }

  public static ItemStack getItemForType(ItemType type) {
    ItemStack cachedItem = CACHE.get(type);
    if (cachedItem == null) {
      String def = MAP.get(type).get();
      cachedItem = getItem(def);
      CACHE.put(type, cachedItem);
    }
    return cachedItem;
  }

  @Nullable
  public static ItemType getTypeForItem(ItemStack item) {
    for (ItemType type : ItemType.values()) {
      ItemStack forType = getItemForType(type);
      if (ItemUtil.equalWithoutSize(forType, item)) {
        return type;
      }
    }

    return null;
  }

  @Nullable
  private static ItemStack getItem(String name) {
    ResourceLocation rl = new ResourceLocation(name);
    Item item = ForgeRegistries.ITEMS.getValue(rl);
    if (item == null) {
      return ItemStack.EMPTY;
    }

    return new ItemStack(item);
  }
}

