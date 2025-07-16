package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class EnchantBedItemWhenInInventoryEveryTickProcedure {

    public static void execute(ItemStack itemstack) {
        itemstack.enchant(Enchantments.POWER_ARROWS, 0);
    }
}
