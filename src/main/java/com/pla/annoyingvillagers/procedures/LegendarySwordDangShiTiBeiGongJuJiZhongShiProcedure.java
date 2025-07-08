package com.pla.annoyingvillagers.procedures;

import java.util.Random;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LegendarySwordDangShiTiBeiGongJuJiZhongShiProcedure {

    public static void execute(Entity entity, ItemStack itemstack) {
        if (entity != null) {
            itemstack.getOrCreateTag().putDouble("power", itemstack.getOrCreateTag().getDouble("power") + 1.0D);
            if (itemstack.getOrCreateTag().getDouble("power") >= 10.0D) {
                LivingEntity livingentity;
                ItemStack itemstack1;

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1;

                if (itemstack2.hurt(Mth.nextInt(new Random(), 30, 300), new Random(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                if (itemstack2.hurt(Mth.nextInt(new Random(), 30, 300), new Random(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                if (itemstack2.hurt(Mth.nextInt(new Random(), 30, 300), new Random(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                if (itemstack2.hurt(Mth.nextInt(new Random(), 30, 300), new Random(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }
            }

        }
    }
}
