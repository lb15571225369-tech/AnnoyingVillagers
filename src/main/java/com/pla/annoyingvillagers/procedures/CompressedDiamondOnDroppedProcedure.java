package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;


public class CompressedDiamondOnDroppedProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            entity.hurt(DamageSource.GENERIC, 7.0F);
        }
    }
}
