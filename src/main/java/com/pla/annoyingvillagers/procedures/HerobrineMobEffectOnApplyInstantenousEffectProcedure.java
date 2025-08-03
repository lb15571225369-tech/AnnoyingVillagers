package com.pla.annoyingvillagers.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class HerobrineMobEffectOnApplyInstantenousEffectProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            ServerPlayer serverplayer;

            if (entity instanceof ServerPlayer) {
                serverplayer = (ServerPlayer) entity;
                serverplayer.setGameMode(GameType.ADVENTURE);
            }

            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.causeFoodExhaustion(0.1F);
            }

            if (Math.random() <= 0.01D) {
                entity.hurt(entity.level().damageSources().generic(), 3.5F);
            }

        }
    }
}
