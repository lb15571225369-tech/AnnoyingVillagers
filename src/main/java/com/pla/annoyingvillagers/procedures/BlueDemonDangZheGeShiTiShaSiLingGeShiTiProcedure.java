package com.pla.annoyingvillagers.procedures;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class BlueDemonDangZheGeShiTiShaSiLingGeShiTiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            Level level;

            if (Math.random() <= 0.2D) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u4e0d\u8981\u81ea\u5927"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsaydontbe")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsaydontbe")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            } else if (Math.random() <= 0.3D) {
                if (entity instanceof Player) {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u5e7d\u9ed8\uff0c\u8650\u73a9\u5bb6\u771f\u6709\u610f\u601d"), ChatType.SYSTEM, Util.NIL_UUID);
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level) levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsayplayer")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsayplayer")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }
            } else if (Math.random() <= 0.2D) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u5c0f\u770b\u522b\u4eba\u53ea\u4f1a\u7a81\u51fa\u4f60\u6709\u591a\u4e48\u65e0\u77e5"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemon_say_you_no_know")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemon_say_you_no_know")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            } else if (Math.random() <= 0.2D) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u4f60\u6025\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsayyc")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsayyc")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

        }
    }
}
