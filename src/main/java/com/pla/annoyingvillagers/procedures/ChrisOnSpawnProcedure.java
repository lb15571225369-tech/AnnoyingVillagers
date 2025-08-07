package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;

public class ChrisOnSpawnProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("av_npc", true);
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "tellraw @a {\"text\":\"Chris has joined the game\",\"color\":\"yellow\"}",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
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
