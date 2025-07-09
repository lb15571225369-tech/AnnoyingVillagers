package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.pla.annoyingvillagers.AnnoyingVillagers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class BlueDemonRDangShiTiGengXinKeShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            Level level;

            if (Math.random() <= 0.2D) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.0D, 0.15D), (float) Mth.nextDouble(new Random(), 0.7D, 1.05D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.0D, 0.15D), (float) Mth.nextDouble(new Random(), 0.7D, 1.05D), false);
                    }
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            if (!levelaccessor.getEntitiesOfClass(ThrownTrident.class, AABB.ofSize(new Vec3(d0, d1, d2), 40.0D, 40.0D, 40.0D), (throwntrident) -> {
                return true;
            }).isEmpty() && Math.random() <= 0.02D) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @e[type=minecraft:trident] run particle annoying_villagersbychentu:dianhu ^ ^ ^0.1 0.2 0.2 0.1 0 1");
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.0D, 0.1D), (float) Mth.nextDouble(new Random(), 0.7D, 1.05D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.0D, 0.1D), (float) Mth.nextDouble(new Random(), 0.7D, 1.05D), false);
                    }
                }
            }

            if (Math.random() <= 0.12D) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoying_villagersbychentu:dianhu ^ ^ ^ 0.3 1.2 0.3 0 1");
                }

                if (Math.random() <= 0.8D && levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.0D, 0.15D), (float) Mth.nextDouble(new Random(), 0.7D, 1.05D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":electify")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.0D, 0.15D), (float) Mth.nextDouble(new Random(), 0.7D, 1.05D), false);
                    }
                }
            }

            if (entity.isPassenger()) {
                entity.stopRiding();
            }

        }
    }
}
