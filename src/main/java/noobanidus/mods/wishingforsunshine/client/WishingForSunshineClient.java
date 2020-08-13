package noobanidus.mods.wishingforsunshine.client;

import net.minecraft.client.color.world.BiomeColors;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;

public class WishingForSunshineClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : -1, WishingForSunshine.WELL_BLOCK);
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
      return 4159204;
    }, WishingForSunshine.WELL_ITEM);
  }
}
