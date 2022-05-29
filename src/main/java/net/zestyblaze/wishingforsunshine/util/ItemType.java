package net.zestyblaze.wishingforsunshine.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zestyblaze.wishingforsunshine.WishingForSunshine;

import java.util.Locale;

public enum ItemType {
    RAIN,
    THUNDER,
    SUNSHINE,
    SUNRISE,
    SUNSET,
    NOON,
    MIDNIGHT;

    public final TagKey<Item> tag;

    public static ItemType get(ItemStack stack) {
        for(ItemType type : values()) {
            if(stack.is(type.tag)) {
                return type;
            }
        }
        return null;
    }

    private static TagKey<Item> create(String id) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(WishingForSunshine.MODID, id));
    }

    ItemType() {
        tag = create(name().toLowerCase(Locale.ROOT));
    }
}
