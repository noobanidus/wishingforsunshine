package net.zestyblaze.wishingforsunshine.entity.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ServerLevelData;
import net.zestyblaze.wishingforsunshine.WishingForSunshine;
import net.zestyblaze.wishingforsunshine.registry.WFSBlockEntityInit;
import net.zestyblaze.wishingforsunshine.util.ItemType;

public class WellBlockEntity extends BlockEntity {
    public WellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(WFSBlockEntityInit.WELL_BLOCK_ENTITY, blockPos, blockState);
    }

    public void onEntityCollision(Player player, ItemEntity itemEntity) {
        if(level != null && !level.isClientSide()) {
            ItemType result = handleItem(itemEntity.getItem());
            if(result != null) {
                itemEntity.getItem().shrink(1);
                if(itemEntity.getItem().isEmpty()) {
                    itemEntity.discard();
                }
                if(player != null) {
                    player.sendMessage(new TranslatableComponent(WishingForSunshine.MODID + ".message." + result.name().toLowerCase(), player.getDisplayName()).setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), null);
                }
            }
        }
    }

    public void onUse(Player player, InteractionHand hand, ItemStack stack) {
        if(level != null && !level.isClientSide) {
            ItemType result = handleItem(stack);
            if(result != null) {
                stack.shrink(1);
                player.setItemInHand(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);
                player.sendMessage(new TranslatableComponent(WishingForSunshine.MODID + ".message." + result.name().toLowerCase(), player.getDisplayName()).setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), null);
            }
        }
    }

    private ItemType handleItem(ItemStack stack) {
        if (!stack.isEmpty() && level != null) {
            if (level.getLevelData() instanceof ServerLevelData properties) {
                ItemType resultType = ItemType.get(stack);
                if(resultType != null) {
                    switch (resultType) {
                        case RAIN -> {
                            if (!properties.isRaining()) {
                                properties.setRaining(true);
                                properties.setRainTime(level.getRandom().nextInt(168000) + 12000);
                                return resultType;
                            }
                            return null;
                        }
                        case THUNDER -> {
                            if (!properties.isThundering()) {
                                properties.setRaining(true);
                                properties.setThundering(true);
                                properties.setRainTime(level.getRandom().nextInt(168000) + 12000);
                                properties.setThunderTime(level.getRandom().nextInt(168000) + 12000);
                                return resultType;
                            }
                            return null;
                        }
                        case SUNSHINE -> {
                            if(properties.isRaining() || properties.isThundering()) {
                                properties.setRaining(false);
                                properties.setThundering(false);
                                properties.setRainTime(level.getRandom().nextInt(168000) + 12000);
                                properties.setThunderTime(level.getRandom().nextInt(168000) + 12000);
                                return resultType;
                            }
                            return null;
                        }
                        case SUNRISE -> {
                            properties.setDayTime(0);
                            return resultType;
                        }
                        case SUNSET -> {
                            properties.setDayTime(12400);
                            return resultType;
                        }
                        case NOON -> {
                            properties.setDayTime(5800);
                            return resultType;
                        }
                        case MIDNIGHT -> {
                            properties.setDayTime(17800);
                            return resultType;
                        }
                    }
                }
            }
        }
        return null;
    }
}
