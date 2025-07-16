package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class NoneObWhenEntityCollidesWithBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^ minecraft:air");
            }
            new DelayedTask(20) {
                @Override
                public void run() {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                "/execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:darkob"
                        );
                    }
                }
            };
            new DelayedTask(2) {
                @Override
                public void run() {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                "/execute as @s at @s anchored eyes run setblock ^ ^ ^ annoyingvillagers:darkob"
                        );
                    }
                }
            };
            new DelayedTask(2) {
                @Override
                public void run() {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                "/execute as @s at @s anchored eyes run setblock ^ ^ ^-1 annoyingvillagers:darkob"
                        );
                    }
                }
            };

            new DelayedTask(2) {
                @Override
                public void run() {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                "/execute as @s at @s anchored eyes run setblock ^ ^ ^-2 annoyingvillagers:darkob"
                        );
                    }
                }
            };

            new DelayedTask(2) {
                @Override
                public void run() {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                "/execute as @s at @s anchored eyes run setblock ^ ^ ^-3 annoyingvillagers:darkob"
                        );
                    }
                }
            };
        }
    }
}
