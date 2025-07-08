package com.pla.annoyingvillagers.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ErrorEnchantment extends Enchantment {

    public ErrorEnchantment(EquipmentSlot... aequipmentslot) {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, aequipmentslot);
    }

    public int getMinLevel() {
        return 0;
    }

    public int getMaxLevel() {
        return 0;
    }

    public boolean isAllowedOnBooks() {
        return false;
    }

    public boolean isDiscoverable() {
        return false;
    }

    public boolean isTradeable() {
        return false;
    }
}
