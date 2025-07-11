package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.LevelAccessor;

public class KickSongKaiAnJianShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (entity.isShiftKeyDown() && entity.isSprinting()) {
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() instanceof SwordItem) {
                    if (entity.getPersistentData().getDouble("dash_auto") != 1.0D) {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"wom:biped/combat/torment_charged_attack_2\" 0 1");
                        }

                        entity.getPersistentData().putDouble("dash_auto", 1.0D);
                        new DelayedTask(50) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("dash_auto", 0.0D);
                            }
                        };
                    }
                } else {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity;

                        itemstack = livingentity1.getMainHandItem();
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    if (itemstack.getItem() instanceof AxeItem && entity.getPersistentData().getDouble("dash_auto") != 1.0D) {
                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"wom:biped/combat/torment_charged_attack_2\" 0 1");
                        }

                        entity.getPersistentData().putDouble("dash_auto", 1.0D);
                        new DelayedTask(50) {
                            @Override
                            public void run() {
                                entity.getPersistentData().putDouble("dash_auto", 0.0D);
                            }
                        };
                    }
                }
            }

        }
    }
}
