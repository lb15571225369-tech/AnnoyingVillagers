package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.item.ItemStack;

public class WoopieTheSwordItemOnEntityHitProcedure {

    public static void execute(ItemStack itemstack) {
        itemstack.getOrCreateTag().putDouble("woopie_dash", itemstack.getOrCreateTag().getDouble("woopie_dash") + 1.0D);
    }
}

