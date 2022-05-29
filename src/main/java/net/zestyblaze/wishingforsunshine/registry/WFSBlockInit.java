package net.zestyblaze.wishingforsunshine.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.zestyblaze.wishingforsunshine.WishingForSunshine;
import net.zestyblaze.wishingforsunshine.block.WellBlock;

public class WFSBlockInit {
    public static final Block WELL_BLOCK = new WellBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3f).sound(SoundType.STONE));

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new ResourceLocation(WishingForSunshine.MODID, "well"), WELL_BLOCK);
    }
}
