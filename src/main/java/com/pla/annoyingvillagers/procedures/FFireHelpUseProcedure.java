package com.pla.annoyingvillagers.procedures;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FFireHelpUseProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            Player player;

            if (entity instanceof Player) {
                player = (Player) entity;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("/f-fire \u76ee\u6807ID \u5173\u95ed\u5bf9\u6b64\u73a9\u5bb6\u7684\u4f24\u5bb3"), false);
                }
            }

            if (entity instanceof Player) {
                player = (Player) entity;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("/f-fire-off \u76ee\u6807ID \u5f00\u542f\u5bf9\u6b64\u73a9\u5bb6\u7684\u4f24\u5bb3"), false);
                }
            }

        }
    }
}
