package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;

public class BlueDemonStagingCombatConditionProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : entity.isAlive();
    }
}
