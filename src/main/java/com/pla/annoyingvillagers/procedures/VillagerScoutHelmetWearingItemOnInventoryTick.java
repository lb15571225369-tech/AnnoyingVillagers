package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class VillagerScoutHelmetWearingItemOnInventoryTick {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET_WEARING.get());

                player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                    return itemstack.getItem() == itemstack1.getItem();
                }, 1, player.inventoryMenu.getCraftSlots());
            }

        }
    }
}
