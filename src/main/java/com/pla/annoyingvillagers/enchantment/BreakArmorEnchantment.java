package com.pla.annoyingvillagers.enchantment;

import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class BreakArmorEnchantment extends Enchantment {

    public BreakArmorEnchantment(EquipmentSlot... aequipmentslot) {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, aequipmentslot);
    }

    public int getMaxLevel() {
        return 3;
    }

    protected boolean checkCompatibility(Enchantment enchantment) {
        return this != enchantment && !List.of(Enchantments.MENDING, Enchantments.POWER_ARROWS).contains(enchantment);
    }
}
