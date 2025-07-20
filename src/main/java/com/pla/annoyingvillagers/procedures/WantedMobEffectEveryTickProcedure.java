package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;

public class WantedMobEffectEveryTickProcedure {

    public static void execute(Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "team leave @s",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }

        }
    }
}

