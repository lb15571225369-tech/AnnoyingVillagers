package com.pla.annoyingvillagers.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;

import java.util.Random;

public class CombatBehaviour {
    public static void throwEnderPearl(Entity entity, float xRot) {
        if (xRot != 0.0F) {
            entity.setYRot(0.0F);
            entity.setXRot(xRot);
            entity.setYBodyRot(entity.getYRot());
            entity.setYHeadRot(entity.getYRot());
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.yBodyRotO = livingEntity.getYRot();
            livingEntity.yHeadRotO = livingEntity.getYRot();
        }

        Level level = entity.level();
        if (!level.isClientSide()) {
            Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
            projectile.setOwner(entity);
            projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
            projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, new Random().nextBoolean() ? 1.0F : 2.0F, 0.0F);
            level.addFreshEntity(projectile);
        }
    }
}
