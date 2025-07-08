package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;

public class HerobrinelowProcedure {

    public static boolean execute(Entity entity) {
        return entity == null ? false : !entity.isAlive();
    }
}
