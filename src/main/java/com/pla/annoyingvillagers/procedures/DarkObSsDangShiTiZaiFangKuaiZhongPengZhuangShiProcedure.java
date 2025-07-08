package com.pla.annoyingvillagers.procedures;

import java.util.Random;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class DarkObSsDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.getMobType() == MobType.UNDEAD) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "1");
                    }

                    return;
                }
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoying_villagersbychentu:spark ^ ^1.5 ^0.8 0 0 0 0.1 1");
            }

            LivingEntity livingentity1;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            ItemStack itemstack1 = itemstack;

            if (itemstack1.hurt(1, new Random(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/hit_short\" 0 1");
            }

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt(1, new Random(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity2 = (LivingEntity) entity;

                if (!livingentity2.level.isClientSide()) {
                    livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 8, false, false));
                }
            }

            entity.setDeltaMovement(new Vec3(0.0D, 0.0D, 0.0D));
            entity.hurt(DamageSource.MAGIC, 3.0F);
        }
    }
}
