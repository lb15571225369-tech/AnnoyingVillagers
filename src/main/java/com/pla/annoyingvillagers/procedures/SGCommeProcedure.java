package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;

public class SGCommeProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("s_g", true);
        }
    }
}
