package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;

import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AngrySteveOnKillProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        Entity entity = levelaccessor.getEntitiesOfClass(
                        AngrySteveEntity.class,
                        AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                        angrySteveEntity -> true)
                .stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                .orElse(null);

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;

            if (!livingentity.level().isClientSide()) {
                livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2, false, false));
            }
        }

    }
}
