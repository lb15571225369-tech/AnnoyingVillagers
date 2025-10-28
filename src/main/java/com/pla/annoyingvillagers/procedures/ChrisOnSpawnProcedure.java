package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class ChrisOnSpawnProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                entity.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§e" + entity.getDisplayName().getString() + " has joined the game§r"), false);
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join ce",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
