package com.pla.annoyingvillagers.procedures;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class OffEmoteMusicProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            Player player;

            if (!entity.getPersistentData().getBoolean("emote")) {
                entity.getPersistentData().putBoolean("emote", true);
                if (entity instanceof Player) {
                    player = (Player) entity;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u5df2\u5173\u95ed\u8868\u60c5\u97f3\u4e50"), true);
                    }
                }
            } else if (entity.getPersistentData().getBoolean("emote")) {
                entity.getPersistentData().putBoolean("emote", false);
                if (entity instanceof Player) {
                    player = (Player) entity;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u5df2\u5f00\u542f\u8868\u60c5\u97f3\u4e50"), true);
                    }
                }
            }

        }
    }
}
