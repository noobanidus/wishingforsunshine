package net.zestyblaze.wishingforsunshine.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.zestyblaze.wishingforsunshine.WishingForSunshine;
import net.zestyblaze.wishingforsunshine.entity.block.WellBlockEntity;

public class WFSBlockEntityInit {
    public static BlockEntityType<WellBlockEntity> WELL_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        WELL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(WishingForSunshine.MODID, "well_block_entity"), FabricBlockEntityTypeBuilder.create(WellBlockEntity::new, WFSBlockInit.WELL_BLOCK).build(null));
    }
}
