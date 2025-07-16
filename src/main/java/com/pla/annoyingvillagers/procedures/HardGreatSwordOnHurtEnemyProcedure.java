package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.item.ItemStack;

public class HardGreatSwordOnHurtEnemyProcedure {

    public static void execute(ItemStack itemstack) {
        itemstack.getOrCreateTag().putDouble("power", itemstack.getOrCreateTag().getDouble("power") + 1.0D);
    }
}
