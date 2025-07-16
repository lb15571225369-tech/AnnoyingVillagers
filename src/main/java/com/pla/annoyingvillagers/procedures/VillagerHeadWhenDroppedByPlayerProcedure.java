package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;

public class VillagerHeadWhenDroppedByPlayerProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team leave @s[team=villagers]");
            }

        }
    }
}
