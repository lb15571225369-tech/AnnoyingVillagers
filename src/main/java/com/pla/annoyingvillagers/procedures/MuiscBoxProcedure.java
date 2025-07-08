package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class MuiscBoxProcedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            Player player;

            if (entity1 instanceof Player) {
                player = (Player) entity1;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u00a7e\u4f60\u51fb\u6740\u4e86" + entity.getDisplayName().getString()), true);
                }
            }

            if (entity1 instanceof Player) {
                player = (Player) entity1;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u00a7a\u4f60\u51fb\u6740\u4e86" + entity.getDisplayName().getString()), false);
                }
            }

            if (entity instanceof Player) {
                player = (Player) entity;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u00a7c\u4f60\u5931\u8d25\u4e86\uff01"), false);
                }
            }

            if (entity instanceof Player) {
                player = (Player) entity;
                if (!player.level.isClientSide()) {
                    player.displayClientMessage(new TextComponent("\u00a7c\u4f60\u5931\u8d25\u4e86\uff01"), true);
                }
            }

        }
    }
}
