package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class BlueDemonOnEntityDeathProcedure {

    public static void execute(LevelAccessor world, Entity entity, Entity entity1) {
        if (entity == null || entity1 == null) return;

        // Summon Blue Demon replacement
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            try {
                entity.getServer().getCommands().getDispatcher().execute(
                        "summon annoyingvillagers:blue_demon_staging",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {
                
            }
            if (entity instanceof BlueDemonEntity blueDemon && blueDemon.getBbqUUID() != null) {
                if (world instanceof ServerLevel serverLevel) {
                    Entity bbq = serverLevel.getEntity(blueDemon.getBbqUUID());
                    if (bbq instanceof BbqEntity && bbq.isAlive()) {
                        bbq.discard();
                    }
                }
            }
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
