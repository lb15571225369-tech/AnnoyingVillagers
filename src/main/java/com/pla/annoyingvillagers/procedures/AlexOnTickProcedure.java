package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AlexOnTickProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;

            if (entity.isInWater() && entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                if (!livingentity.level().isClientSide()) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2, 5, false, false));
                }
            }

            if (entity.isPassenger()) {
                entity.stopRiding();
            }

            if (!levelaccessor.getEntitiesOfClass(PrimedTnt.class, AABB.ofSize(new Vec3(d0, d1, d2), 5.0D, 5.0D, 5.0D), (primedtnt) -> {
                return true;
            }).isEmpty()) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "/execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^1 ^ ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^1 ^-1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^1 ^1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^-1 ^ ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^-1 ^1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^-1 ^-1 ^1 minecraft:oak_planks",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            }
        }
    }
}
