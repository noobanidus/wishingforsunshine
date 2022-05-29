package net.zestyblaze.wishingforsunshine.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.zestyblaze.wishingforsunshine.WishingForSunshine;

public class WFSItemInit {
    public static Item WELL_ITEM;

    public static void registerItems() {
        WELL_ITEM = Registry.register(Registry.ITEM, new ResourceLocation(WishingForSunshine.MODID, "well"), new BlockItem(WFSBlockInit.WELL_BLOCK, new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
    }
}
