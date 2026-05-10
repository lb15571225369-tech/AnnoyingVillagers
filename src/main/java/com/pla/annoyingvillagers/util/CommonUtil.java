package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class CommonUtil {
    public static boolean isAvDamageableEfnWeaponsMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AngrySteveEntity
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static boolean isAvRunawayJudgementCutEndMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AVNpc
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static void forceRotate(LivingEntity entity, LivingEntity lookAtEntity) {
        if (entity == null || lookAtEntity == null) {
            return;
        }

        forceRotate(entity, lookAtEntity.getEyePosition());
    }

    public static void forceRotate(LivingEntity entity, BlockPos lookAtPos) {
        if (entity == null || lookAtPos == null) {
            return;
        }

        forceRotate(entity, Vec3.atCenterOf(lookAtPos));
    }

    public static void forceRotate(LivingEntity entity, Vec3 lookAtPos) {
        if (entity == null || lookAtPos == null) {
            return;
        }

        Vec3 eyePos = entity.getEyePosition();

        double dx = lookAtPos.x - eyePos.x;
        double dy = lookAtPos.y - eyePos.y;
        double dz = lookAtPos.z - eyePos.z;

        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        if (horizontalDistance < 1.0E-7D) {
            return;
        }

        float yaw = (float) (Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
        float pitch = (float) -(Mth.atan2(dy, horizontalDistance) * Mth.RAD_TO_DEG);

        yaw = Mth.wrapDegrees(yaw);
        pitch = Mth.clamp(Mth.wrapDegrees(pitch), -90.0F, 90.0F);

        entity.setYRot(yaw);
        entity.setXRot(pitch);

        entity.yRotO = yaw;
        entity.xRotO = pitch;

        entity.setYHeadRot(yaw);
        entity.yHeadRotO = yaw;

        entity.yBodyRot = yaw;
        entity.yBodyRotO = yaw;
    }

    public static void pullEntityTowardCaster(LivingEntity target, LivingEntity caster) {
        pullEntityTowardCaster(target, caster, 0.22D, 0.04D, true);
    }

    public static void pullEntityTowardCaster(
            LivingEntity target,
            LivingEntity caster,
            double strength,
            double yBoost,
            boolean forceLookAtCaster
    ) {
        if (target == null || caster == null) {
            return;
        }

        if (!target.isAlive() || !caster.isAlive()) {
            return;
        }

        if (forceLookAtCaster) {
            forceRotate(target, caster);
        }

        pullEntityTowardPosition(target, caster.position(), strength, yBoost);
    }

    public static void pullEntityTowardPosition(
            Entity target,
            Vec3 targetPos,
            double strength,
            double yBoost
    ) {
        if (target == null || targetPos == null) {
            return;
        }

        Vec3 direction = targetPos.subtract(target.position());
        applyHorizontalDirectionalMotion(target, direction, strength, yBoost);
    }

    public static void pushEntityFromCaster(LivingEntity target, LivingEntity caster) {
        forceRotate(target, caster);
        pushEntityFromCaster(target, caster, 0.35D, 0.08D);
    }

    public static void pushEntityFromCaster(
            Entity target,
            Entity caster,
            double strength,
            double yBoost
    ) {
        if (target == null || caster == null) {
            return;
        }

        pushEntityFromPosition(target, caster.position(), strength, yBoost);
    }

    public static void pushEntityFromPosition(
            Entity target,
            Vec3 sourcePos,
            double strength,
            double yBoost
    ) {
        if (target == null || sourcePos == null) {
            return;
        }

        Vec3 direction = target.position().subtract(sourcePos);

        if (direction.horizontalDistanceSqr() < 1.0E-7D) {
            direction = target.getLookAngle();
        }

        applyHorizontalDirectionalMotion(target, direction, strength, yBoost);
    }

    private static void applyHorizontalDirectionalMotion(
            Entity entity,
            Vec3 direction,
            double strength,
            double yBoost
    ) {
        if (entity == null || direction == null) {
            return;
        }

        if (strength <= 0.0D) {
            return;
        }

        Vec3 horizontal = new Vec3(direction.x, 0.0D, direction.z);

        if (horizontal.lengthSqr() < 1.0E-7D) {
            return;
        }

        Vec3 motion = horizontal.normalize().scale(strength);

        entity.setDeltaMovement(
                entity.getDeltaMovement().add(
                        motion.x,
                        yBoost,
                        motion.z
                )
        );

        entity.hasImpulse = true;
        entity.hurtMarked = true;
    }
}
