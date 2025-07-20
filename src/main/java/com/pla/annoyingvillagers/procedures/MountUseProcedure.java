package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;

public class MountUseProcedure {

    public static void execute(Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "player " + entity.getDisplayName().getString() + " mount anything",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

            }

        }
    }
}
