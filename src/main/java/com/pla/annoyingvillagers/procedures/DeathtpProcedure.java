package com.pla.annoyingvillagers.procedures;

import java.io.File;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

public class DeathtpProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            new File("");

            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:player") && entity instanceof Player) {
                Player player = (Player) entity;

                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u00a74\u4f60\u5931\u8d25\u4e86"), false);
                }
            }

        }
    }
}
