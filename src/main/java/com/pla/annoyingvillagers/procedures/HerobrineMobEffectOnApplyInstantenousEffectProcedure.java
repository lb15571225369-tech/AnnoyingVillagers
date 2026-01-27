package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class HerobrineMobEffectOnApplyInstantenousEffectProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.causeFoodExhaustion(0.1F);
            }

            if (Math.random() <= 0.05D) {
                entity.hurt(entity.level().damageSources().generic(), 1.5F);
            }

            if (!entity.level().isClientSide()) {
                Random random = new Random();
                String particleCommand = String.format(
                        "execute at @s run particle annoyingvillagers:glowing_eyes ^%d ^%d ^%d 0 0 0 0.1 %d",
                        random.nextInt(-3, 3),
                        random.nextInt(-3, 3),
                        random.nextInt(-3, 3),
                        random.nextInt(50, 200)
                );
                try {
                    entity.getServer().getCommands().getDispatcher().execute(particleCommand,
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

        }
    }
}
