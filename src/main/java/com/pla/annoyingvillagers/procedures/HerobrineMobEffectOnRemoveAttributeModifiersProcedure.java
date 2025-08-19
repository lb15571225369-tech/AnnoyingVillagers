package com.pla.annoyingvillagers.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class HerobrineMobEffectOnRemoveAttributeModifiersProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;
                livingentity.hurt(entity.level().damageSources().magic(), 1.0F);
            }

            entity.hurt(entity.level().damageSources().inWall(), 1.0F);
        }
    }
}
