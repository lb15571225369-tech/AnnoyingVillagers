package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class BlueDemonDangShiTiSiWangShiProcedure {

    public static void execute(LevelAccessor world, Entity entity, Entity entity1) {
        if (entity == null || entity1 == null) return;

        // Summon Blue Demon replacement
        if (!entity.level.isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performCommand(
                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                    "summon annoyingvillagers:blue_demon_r"
            );
        }

        if (!entity.level.isClientSide()) {
            entity.discard();
        }

        entity1.getPersistentData().putBoolean("b_d", true);

        new DelayedTask(120) {
            @Override
            public void run() {
                if (entity1.isAlive()) {
                    entity1.getPersistentData().putBoolean("b_d", false);
                }
            }
        };
    }
}
