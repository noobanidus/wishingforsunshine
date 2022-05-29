package net.zestyblaze.wishingforsunshine.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.zestyblaze.wishingforsunshine.registry.WFSBlockInit;
import net.zestyblaze.wishingforsunshine.registry.WFSItemInit;

@Environment(EnvType.CLIENT)
public class WFSClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : -1, WFSBlockInit.WELL_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 4159204, WFSItemInit.WELL_ITEM);
    }
}
