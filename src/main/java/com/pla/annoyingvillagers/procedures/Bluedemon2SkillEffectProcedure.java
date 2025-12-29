package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class Bluedemon2SkillEffectProcedure {

    public static void execute(LevelAccessor world, final double x, final double y, final double z, final Entity entity) {
        if (!(entity instanceof Mob mob)) return;

        LivingEntity target = mob.getTarget();
        if (target != null && !target.level().isClientSide()) {
            target.addEffect(new MobEffectInstance(
                    AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 1, 0, false, false));
        }
        new DelayedTask(2) {
            @Override
            public void run() {
                applyEffect(world, mob);
            }
        };
        new DelayedTask(4) {
            @Override
            public void run() {
                applyEffect(world, mob);
            }
        };
        new DelayedTask(5) {
            @Override
            public void run() {
                LivingEntity t = mob.getTarget();
                if (t != null && !t.level().isClientSide()) {
                    t.addEffect(new MobEffectInstance(
                            AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 20, 0, false, false));
                }

                if (world instanceof Level level && !level.isClientSide()) {
                    level.explode(null, x, y, z, 2.0F, Level.ExplosionInteraction.NONE);
                    level.explode(null, x, y, z, 2.0F, Level.ExplosionInteraction.BLOCK);
                }
            }
        };
    }

    private static void applyEffect(LevelAccessor world, Mob mob) {
        LivingEntity target = mob.getTarget();
        if (target != null && !target.level().isClientSide()) {
            target.addEffect(new MobEffectInstance(
                    AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 1, 0, false, false));
        }
    }
}
