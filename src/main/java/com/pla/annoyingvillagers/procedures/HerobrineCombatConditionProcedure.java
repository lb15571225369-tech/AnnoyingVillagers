package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;

public class HerobrineCombatConditionProcedure {

    public static boolean execute(DamageSource damagesource) {
        return damagesource.is(DamageTypes.FALLING_ANVIL);
    }
}
