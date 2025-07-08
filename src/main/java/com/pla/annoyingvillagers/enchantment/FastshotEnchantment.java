package com.pla.annoyingvillagers.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FastshotEnchantment extends Enchantment {

    public FastshotEnchantment(EquipmentSlot... aequipmentslot) {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, aequipmentslot);
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean isTreasureOnly() {
        return true;
    }
}
