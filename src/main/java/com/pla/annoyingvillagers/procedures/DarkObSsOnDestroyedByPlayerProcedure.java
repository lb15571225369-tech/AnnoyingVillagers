package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DarkObSsOnDestroyedByPlayerProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                livingentity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }

        }
    }
}
