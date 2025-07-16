package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;

public class EcPlayerDuringEffectEveryTickProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoyingvillagers:red_spark ~ ~1 ~ 0.3 0.7 0.3 0.01 3");
            }

        }
    }
}
