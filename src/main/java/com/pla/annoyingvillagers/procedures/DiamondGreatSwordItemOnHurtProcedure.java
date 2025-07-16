package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.item.ItemStack;

public class DiamondGreatSwordItemOnHurtProcedure {

    public static void execute(ItemStack itemstack) {
        itemstack.getOrCreateTag().putDouble("sword_skill", itemstack.getOrCreateTag().getDouble("sword_skill") + 1.0D);
    }
}
