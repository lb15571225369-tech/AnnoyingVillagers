package com.pla.annoyingvillagers.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class HerobrineEffectDangXiaoGuoJieShuShiProcedure {

    public static boolean execute(LevelAccessor levelaccessor, Entity entity) {
        if (entity == null) {
            return false;
        } else {
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;

                serverplayer.setGameMode(GameType.SURVIVAL);
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles remove @s titles:herobrine");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles refresh");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "titles refresh");
            }

            return Minecraft.getInstance().gameRenderer.currentEffect() == null;
        }
    }
}
