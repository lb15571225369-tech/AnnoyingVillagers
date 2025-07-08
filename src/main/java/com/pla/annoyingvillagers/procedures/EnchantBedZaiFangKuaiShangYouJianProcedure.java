package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class EnchantBedZaiFangKuaiShangYouJianProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            Player player;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get())) {
                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u4f60\u5df2\u7ecf\u4f7f\u7528\u4e86\u9644\u9b54\u5e8a\uff01"), true);
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
                if (entity.getPersistentData().getDouble("en_bed") >= 2.0D) {
                    levelaccessor.setBlock(new BlockPos(d0, d1, d2), Blocks.AIR.defaultBlockState(), 3);
                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u9644\u9b54\u5e8a\u5df2\u5931\u6548\uff0c\u4f60\u5c06\u65e0\u6cd5\u518d\u6b21\u4f7f\u7528"), true);
                        }
                    }
                } else {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity) entity;

                        if (!livingentity1.level.isClientSide()) {
                            livingentity1.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get(), 999999, 0, false, false));
                        }
                    }

                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u4f60\u4f7f\u7528\u4e86\u4e00\u6b21\u9644\u9b54\u5e8a\uff0c\u7ecf\u9a8c\u7b49\u7ea7-1"), true);
                        }
                    }

                    if (entity instanceof Player) {
                        player = (Player) entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u5df2\u91cd\u7f6e\u91cd\u751f\u70b9"), false);
                        }
                    }

                    if (entity instanceof Player) {
                        player = (Player) entity;
                        player.giveExperienceLevels(-1);
                    }

                    if (entity instanceof ServerPlayer) {
                        ServerPlayer serverplayer = (ServerPlayer) entity;

                        serverplayer.setRespawnPosition(serverplayer.level.dimension(), new BlockPos(d0, d1, d2), serverplayer.getYRot(), true, false);
                    }

                    entity.getPersistentData().putDouble("en_bed", entity.getPersistentData().getDouble("en_bed") + 1.0D);
                }
            } else if (entity instanceof Player) {
                player = (Player) entity;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u4f60\u7684\u7ecf\u9a8c\u7b49\u7ea7\u4e0d\u591f\uff0c\u5fc5\u987b\u5927\u4e8e2\u7ea7\u624d\u80fd\u4f7f\u7528\uff01"), true);
                }
            }

        }
    }
}
