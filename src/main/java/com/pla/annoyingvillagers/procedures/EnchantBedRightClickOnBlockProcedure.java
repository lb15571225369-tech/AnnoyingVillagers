package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class EnchantBedRightClickOnBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            Player player;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get())) {
                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level().isClientSide()) {
                            player.displayClientMessage(Component.literal("You have already used the Enchant Bed!"), true);
                            return;
                        }
                    }

                    return;
                }
            }

            int i;

            if (entity instanceof Player) {
                Player player1 = (Player) entity;

                i = player1.experienceLevel;
            } else {
                i = 0;
            }

            if (i >= 2) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    if (!livingentity1.level().isClientSide()) {
                        livingentity1.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get(),  Integer.MAX_VALUE, 0, false, false));
                    }
                }

                if (entity instanceof Player) {
                    player = (Player) entity;
                    if (!player.level().isClientSide()) {
                        player.displayClientMessage(Component.literal("You used the Enchant Bed once. Experience level -1."), true);
                    }
                }

                if (entity instanceof Player) {
                    player = (Player) entity;
                    if (!player.level().isClientSide()) {
                        player.displayClientMessage(Component.literal("Respawn point has been reset."), false);
                    }
                }

                if (entity instanceof Player) {
                    player = (Player) entity;
                    player.giveExperienceLevels(-1);
                }

                if (entity instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer) entity;

                    serverplayer.setRespawnPosition(serverplayer.level().dimension(), new BlockPos((int) d0, (int) d1, (int) d2), serverplayer.getYRot(), true, false);
                }
            } else if (entity instanceof Player) {
                player = (Player) entity;
                if (!player.level().isClientSide()) {
                    player.displayClientMessage(Component.literal("Your experience level is too low. You must be above level 2 to use this!"), true);
                }
            }

        }
    }
}
