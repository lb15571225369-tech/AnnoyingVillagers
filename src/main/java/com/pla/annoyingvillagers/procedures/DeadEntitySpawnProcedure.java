package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fml.ModList;

public class DeadEntitySpawnProcedure {
    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            new DelayedTask(4) {
                @Override
                public void run() {
                    Entity entity1 = entity;

                    if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                        try {
                            entity1.getServer().getCommands().getDispatcher().execute(
                                    "kill @s",
                                    entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }
                }
            };
        }
    }
}
