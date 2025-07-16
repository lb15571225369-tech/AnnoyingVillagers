package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;

public class ECEffectOnStartProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("kick_x", true);
        }
    }
}
