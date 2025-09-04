package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DiamondMagnetSwordOnTickProcedure {
    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List) levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(2.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Entity entity1 = (Entity) iterator.next();

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
                    }
                }
            }

        }
    }
}
