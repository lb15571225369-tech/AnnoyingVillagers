package com.pla.annoyingvillagers.events;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;

@EventBusSubscriber
public class BreakArmorHitProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity(), livinghurtevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            Enchantment enchantment = (Enchantment) AnnoyingVillagersModEnchantments.BREAK_ARMOR.get();
            ItemStack itemstack;

            if (entity1 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity1;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0) {
                LivingEntity livingentity1;
                ItemStack itemstack1;

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1;
                Enchantment enchantment1 = (Enchantment) AnnoyingVillagersModEnchantments.BREAK_ARMOR.get();
                ItemStack itemstack3;

                if (entity1 instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity1;
                    itemstack3 = livingentity1.getMainHandItem();
                } else {
                    itemstack3 = ItemStack.EMPTY;
                }

                if (itemstack2.hurt(EnchantmentHelper.getItemEnchantmentLevel(enchantment1, itemstack3) * 10, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                enchantment1 = (Enchantment) AnnoyingVillagersModEnchantments.BREAK_ARMOR.get();
                if (entity1 instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity1;
                    itemstack3 = livingentity1.getMainHandItem();
                } else {
                    itemstack3 = ItemStack.EMPTY;
                }

                if (itemstack2.hurt(EnchantmentHelper.getItemEnchantmentLevel(enchantment1, itemstack3) * 10, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                enchantment1 = (Enchantment) AnnoyingVillagersModEnchantments.BREAK_ARMOR.get();
                if (entity1 instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity1;
                    itemstack3 = livingentity1.getMainHandItem();
                } else {
                    itemstack3 = ItemStack.EMPTY;
                }

                if (itemstack2.hurt(EnchantmentHelper.getItemEnchantmentLevel(enchantment1, itemstack3) * 10, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack1 = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack2 = itemstack1;
                enchantment1 = (Enchantment) AnnoyingVillagersModEnchantments.BREAK_ARMOR.get();
                if (entity1 instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity1;
                    itemstack3 = livingentity1.getMainHandItem();
                } else {
                    itemstack3 = ItemStack.EMPTY;
                }

                if (itemstack2.hurt(EnchantmentHelper.getItemEnchantmentLevel(enchantment1, itemstack3) * 10, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack2.shrink(1);
                    itemstack2.setDamageValue(0);
                }
            }

        }
    }
}
