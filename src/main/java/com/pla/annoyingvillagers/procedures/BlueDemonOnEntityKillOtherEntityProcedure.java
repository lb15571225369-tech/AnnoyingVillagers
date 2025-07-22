package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class BlueDemonOnEntityKillOtherEntityProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            Level level;

            if (Math.random() <= 0.2D) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Blue Demon> Don't be arrogant."), false);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsaydontbe")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsaydontbe")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            } else if (Math.random() <= 0.3D) {
                if (entity instanceof Player) {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Blue Demon> Amusing. Torturing players is really fun."), false);
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level) levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayplayer")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayplayer")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }
            } else if (Math.random() <= 0.2D) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Blue Demon> Underestimating others only shows how ignorant you are."), false);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemon_say_you_no_know")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemon_say_you_no_know")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            } else if (Math.random() <= 0.2D) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Blue Demon> You're getting nervous."), false);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayyc")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayyc")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

        }
    }
}
