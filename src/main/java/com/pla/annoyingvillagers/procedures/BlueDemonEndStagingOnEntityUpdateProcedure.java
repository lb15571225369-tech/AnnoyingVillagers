package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class BlueDemonEndStagingOnEntityUpdateProcedure {

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        new DelayedTask(405) {
            @Override
            public void run() {
                if (entity.isAlive()) {
                    String command = Math.random() <= 0.4D
                            ? "summon annoyingvillagers:blue_demon"
                            : "summon annoyingvillagers:blue_demon_2";

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                command
                        );
                    }

                    if (!entity.level.isClientSide()) {
                        entity.discard();
                    }
                }
            }
        };
    }
}
