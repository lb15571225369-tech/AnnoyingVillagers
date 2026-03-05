package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Random;

public class VillagerUtil {
    public static ItemStack generateMainWeaponItem() {
        float chance = new Random().nextFloat();
        ItemStack itemStack;
        if (chance <= 0.2F) {
            itemStack = new ItemStack(AnnoyingVillagersModItems.DIAMOND_DAGGER.get());
        } else if (chance <= 0.4F) {
            itemStack = new ItemStack(AnnoyingVillagersModItems.DIAMOND_FALCHION.get());
        } else if (chance <= 0.6F) {
            itemStack = new ItemStack(AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.get());
        } else {
            itemStack = new ItemStack(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
        }

        float enchantChance = new Random().nextFloat();
        if (enchantChance <= 0.2) {
            itemStack.enchant(Enchantments.FIRE_ASPECT, new Random().nextInt(1, 3));
        }
        if (enchantChance <= 0.4) {
            itemStack.enchant(Enchantments.SWEEPING_EDGE, new Random().nextInt(1, 3));
        }
        if (enchantChance <= 0.6) {
            itemStack.enchant(Enchantments.SMITE, new Random().nextInt(1, 3));
        }
        if (enchantChance <= 0.8) {
            itemStack.enchant(Enchantments.KNOCKBACK, new Random().nextInt(1, 3));
        }
        itemStack.enchant(Enchantments.SHARPNESS, new Random().nextInt(1, 3));
        return itemStack;
    }
}
