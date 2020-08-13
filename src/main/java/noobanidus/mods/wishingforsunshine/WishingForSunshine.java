package noobanidus.mods.wishingforsunshine;

import com.google.common.base.Suppliers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import noobanidus.mods.wishingforsunshine.common.WellBlock;
import noobanidus.mods.wishingforsunshine.common.WellBlockEntity;

public class WishingForSunshine implements ModInitializer {
  public static final String MODID = "wishingforsunshine";

  public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "items"), Suppliers.memoize(() -> new ItemStack(WishingForSunshine.WELL_BLOCK)));
  public static final WellBlock WELL_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MODID, "well"), new WellBlock(AbstractBlock.Settings.of(Material.STONE).strength(3f).sounds(BlockSoundGroup.STONE)));
  public static final BlockEntityType<WellBlockEntity> WELL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "well"), BlockEntityType.Builder.create(WellBlockEntity::new, WELL_BLOCK).build(null));
  public static final Item WELL_ITEM = Registry.register(Registry.ITEM, new Identifier(MODID, "well"), new BlockItem(WELL_BLOCK, new Item.Settings().group(ITEM_GROUP)));

  @Override
  public void onInitialize() {}
}
