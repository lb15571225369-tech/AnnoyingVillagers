package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DeathReturnProcedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity().level, livingdeathevent.getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                ResourceKey resourcekey;

                if (levelaccessor instanceof Level) {
                    Level level = (Level) levelaccessor;

                    resourcekey = level.dimension();
                } else {
                    resourcekey = Level.OVERWORLD;
                }

                if (resourcekey != Level.OVERWORLD) {
                    entity.teleportTo((double) levelaccessor.getLevelData().getXSpawn(), (double) levelaccessor.getLevelData().getYSpawn(), (double) levelaccessor.getLevelData().getZSpawn());
                    if (entity instanceof ServerPlayer) {
                        ServerPlayer serverplayer = (ServerPlayer) entity;

                        serverplayer.connection.teleport((double) levelaccessor.getLevelData().getXSpawn(), (double) levelaccessor.getLevelData().getYSpawn(), (double) levelaccessor.getLevelData().getZSpawn(), entity.getYRot(), entity.getXRot());
                    }
                }
            }

        }
    }
}
