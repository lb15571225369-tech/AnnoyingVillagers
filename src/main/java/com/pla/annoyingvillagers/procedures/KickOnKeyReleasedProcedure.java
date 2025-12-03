package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.LevelAccessor;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class KickOnKeyReleasedProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
//        if (entity != null) {
//            if (entity.isShiftKeyDown() && entity.isSprinting()) {
//                ItemStack itemstack;
//
//                if (entity instanceof LivingEntity) {
//                    LivingEntity livingentity = (LivingEntity)entity;
//
//                    itemstack = livingentity.getMainHandItem();
//                } else {
//                    itemstack = ItemStack.EMPTY;
//                }
//
//                if (itemstack.getItem() instanceof SwordItem) {
//                    if (entity.getPersistentData().getDouble("dash_auto") != 1.0D) {
//                        if (!entity.level().isClientSide() && entity.getServer() != null) {
//                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//                            if (livingEntityPatch != null) {
//                                livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_2, 0.0F);
//                            }
//                        }
//
//                        entity.getPersistentData().putDouble("dash_auto", 1.0D);
//                        new DelayedTask(50) {
//                            @Override
//                            public void run() {
//                                entity.getPersistentData().putDouble("dash_auto", 0.0D);
//                            }
//                        };
//                    }
//                } else {
//                    if (entity instanceof LivingEntity) {
//                        LivingEntity livingentity1 = (LivingEntity)entity;
//
//                        itemstack = livingentity1.getMainHandItem();
//                    } else {
//                        itemstack = ItemStack.EMPTY;
//                    }
//
//                    if (itemstack.getItem() instanceof AxeItem && entity.getPersistentData().getDouble("dash_auto") != 1.0D) {
//                        if (!entity.level().isClientSide() && entity.getServer() != null) {
//                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//                            if (livingEntityPatch != null) {
//                                livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_2, 0.0F);
//                            }
//                        }
//
//                        entity.getPersistentData().putDouble("dash_auto", 1.0D);
//                        new DelayedTask(50) {
//                            @Override
//                            public void run() {
//                                entity.getPersistentData().putDouble("dash_auto", 0.0D);
//                            }
//                        };
//                    }
//                }
//            }

//        }
    }
}
