package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.QueuedTaskScheduler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class NoneobDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^ minecraft:air");
            }
            new QueuedTaskScheduler()
                    .schedule(() -> {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                    "/execute as @s at @s anchored eyes run setblock ^ ^ ^1 modid = AnnoyingVillagers.MODID:darkob"
                            );
                        }
                    }, 20)
                    .schedule(() -> {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                    "/execute as @s at @s anchored eyes run setblock ^ ^ ^ modid = AnnoyingVillagers.MODID:darkob"
                            );
                        }
                    }, 2)
                    .schedule(() -> {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                    "/execute as @s at @s anchored eyes run setblock ^ ^ ^-1 modid = AnnoyingVillagers.MODID:darkob"
                            );
                        }
                    }, 2)
                    .schedule(() -> {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                    "/execute as @s at @s anchored eyes run setblock ^ ^ ^-2 modid = AnnoyingVillagers.MODID:darkob"
                            );
                        }
                    }, 2)
                    .schedule(() -> {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                    "/execute as @s at @s anchored eyes run setblock ^ ^ ^-3 modid = AnnoyingVillagers.MODID:darkob"
                            );
                        }
                    }, 2);
        }
    }
}
