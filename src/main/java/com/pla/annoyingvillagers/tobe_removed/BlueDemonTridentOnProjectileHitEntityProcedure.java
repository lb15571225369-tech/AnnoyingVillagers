package com.pla.annoyingvillagers.tobe_removed;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class BlueDemonTridentOnProjectileHitEntityProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            entity.setSecondsOnFire(8);
            ServerLevel serverlevel;

            if (levelaccessor instanceof ServerLevel) {
                serverlevel = (ServerLevel) levelaccessor;
                LightningBolt lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);

                lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) d0, (int) d1, (int) d2)));
                lightningbolt.setVisualOnly(true);
                serverlevel.addFreshEntity(lightningbolt);
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 1, false, false));
                }
            }

            if (levelaccessor instanceof ServerLevel) {
                serverlevel = (ServerLevel) levelaccessor;
                ThrownTrident throwntrident = new ThrownTrident(EntityType.TRIDENT, serverlevel);

                throwntrident.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                levelaccessor.addFreshEntity(throwntrident);
            }

        }
    }
}

