package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.damagesource.DamageSource;

public class HerobrineCombatConditionProcedure {

    public static boolean execute(DamageSource damagesource) {
        return damagesource == DamageSource.ANVIL;
    }
}
