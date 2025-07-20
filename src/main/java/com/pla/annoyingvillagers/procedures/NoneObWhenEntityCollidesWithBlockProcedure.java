package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class NoneObWhenEntityCollidesWithBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().getDispatcher().execute(
                        "execute as @s at @s anchored eyes run setblock ^ ^ ^ minecraft:air",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            }
            new DelayedTask(20) {
                @Override
                public void run() throws CommandSyntaxException {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:darkob",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    }
                }
            };
            new DelayedTask(2) {
                @Override
                public void run() throws CommandSyntaxException {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^ annoyingvillagers:darkob",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    }
                }
            };
            new DelayedTask(2) {
                @Override
                public void run() throws CommandSyntaxException {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^-1 annoyingvillagers:darkob",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    }
                }
            };

            new DelayedTask(2) {
                @Override
                public void run() throws CommandSyntaxException {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^-2 annoyingvillagers:darkob",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    }
                }
            };

            new DelayedTask(2) {
                @Override
                public void run() throws CommandSyntaxException {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^-3 annoyingvillagers:darkob",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                        );
                    }
                }
            };
        }
    }
}
