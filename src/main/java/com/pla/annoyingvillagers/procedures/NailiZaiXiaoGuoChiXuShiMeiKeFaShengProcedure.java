package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class NailiZaiXiaoGuoChiXuShiMeiKeFaShengProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.getFoodData().setFoodLevel(20);
            }

        }
    }
}
