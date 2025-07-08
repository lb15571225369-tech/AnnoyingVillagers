package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class EnchantBedItemDangWuPinZaiShouShangMeiKeFaShengProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() == AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()) {
                LivingEntity livingentity1;

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    ItemStack itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModBlocks.ENCHANT_BED.get());

                    itemstack1.setCount(1);
                    livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity1 instanceof Player) {
                        Player player = (Player) livingentity1;

                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.POWER_ARROWS, 1);
            }

        }
    }
}
