package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class VillagerHelmetWearingItemOnInventoryTick {

    public static void execute(Entity entity, ItemStack itemStack, ItemStack replaceItem) {
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                    return itemStack.getItem() == itemstack1.getItem();
                }, 1, player.inventoryMenu.getCraftSlots());

                if (replaceItem != null && !replaceItem.isEmpty() && Math.random() < 0.3) {
                    ItemStack damaged = replaceItem.copy();
                    if (damaged.isDamageableItem()) {
                        int maxDamage = damaged.getMaxDamage();
                        int min = maxDamage / 2;
                        int randomDamage = min + (int)(Math.random() * (maxDamage - min));
                        damaged.setDamageValue(randomDamage);
                    }
                    player.getInventory().add(damaged);
                }
            }
        }
    }
}
