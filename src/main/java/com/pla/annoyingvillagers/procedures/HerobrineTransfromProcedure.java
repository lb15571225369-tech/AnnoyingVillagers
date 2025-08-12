package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;

import com.pla.annoyingvillagers.entity.Herobrine3Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HerobrineTransfromProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;

//            if (entity instanceof LivingEntity) {
//                livingentity = (LivingEntity)entity;
//                if (!livingentity.level().isClientSide()) {
//                    livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.HEROBRINEFUSHENBUFF.get(), 5000, 0));
//                }
//            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.HARM, 100, 6, false, false));
                }
            }

            Entity entity1;

            if (!levelaccessor.getEntitiesOfClass(Herobrine3Entity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrine3entity) -> {
                return true;
            }).isEmpty()) {
                entity1 = levelaccessor.getEntitiesOfClass(Herobrine3Entity.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                herobrine3entity -> true)
                        .stream()
                        .min(Comparator.comparingDouble(entity2 -> entity2.distanceToSqr(d0, d1, d2)))
                        .orElse(null);
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    livingentity.setHealth(40.0F);
                }
            }

//            if (!levelaccessor.getEntitiesOfClass(HbGaoJiFenShenEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (hbgaojifenshenentity) -> {
//                return true;
//            }).isEmpty()) {
//                entity1 = (Entity)levelaccessor.getEntitiesOfClass(HerobrinefenshenEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrinefenshenentity) -> {
//                    return true;
//                }).stream().sorted(((<undefinedtype>)(new Object() {
//                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
//                        return Comparator.comparingDouble((entity2) -> {
//                            return entity2.distanceToSqr(d3, d4, d5);
//                        });
//                    }
//                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
//                if (entity1 instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity1;
//                    livingentity.setHealth(40.0F);
//                }
//            }
//
//            if (!levelaccessor.getEntitiesOfClass(HerobrinefenshenEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrinefenshenentity) -> {
//                return true;
//            }).isEmpty()) {
//                entity1 = (Entity)levelaccessor.getEntitiesOfClass(HerobrinefenshenEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrinefenshenentity) -> {
//                    return true;
//                }).stream().sorted(((<undefinedtype>)(new Object() {
//                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
//                        return Comparator.comparingDouble((entity2) -> {
//                            return entity2.distanceToSqr(d3, d4, d5);
//                        });
//                    }
//                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
//                if (entity1 instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity1;
//                    livingentity.setHealth(40.0F);
//                }
//            }
//
//            if (!levelaccessor.getEntitiesOfClass(Herobrine2Entity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrine2entity) -> {
//                return true;
//            }).isEmpty()) {
//                entity1 = (Entity)levelaccessor.getEntitiesOfClass(Herobrine2Entity.class, AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D), (herobrine2entity) -> {
//                    return true;
//                }).stream().sorted(((<undefinedtype>)(new Object() {
//                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
//                        return Comparator.comparingDouble((entity2) -> {
//                            return entity2.distanceToSqr(d3, d4, d5);
//                        });
//                    }
//                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
//                if (entity1 instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity1;
//                    livingentity.setHealth(40.0F);
//                }
//            }

        }
    }
}
