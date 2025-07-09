//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.world.entity.Entity;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//
//@EventBusSubscriber
//public class RespawnProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerRespawned(PlayerRespawnEvent playerrespawnevent) {
//        execute(playerrespawnevent, playerrespawnevent.getPlayer());
//    }
//
//    public static void execute(Entity entity) {
//        execute((Event) null, entity);
//    }
//
//    private static void execute(@Nullable Event event, Entity entity) {
//        if (entity != null) {
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "attribute @s epicfight:staminar base set 100");
//            }
//
//        }
//    }
//}
