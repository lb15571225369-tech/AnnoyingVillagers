//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.client.Minecraft;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.GameType;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
//
//@EventBusSubscriber
//public class JoinWorldProcedure {
//
//    @SubscribeEvent
//    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent entitytraveltodimensionevent) {
//        execute(entitytraveltodimensionevent, entitytraveltodimensionevent.getEntity().level, entitytraveltodimensionevent.getEntity());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity) {
//        execute((Event) null, levelaccessor, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
//        if (entity != null) {
//            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.BAN_OTHER_WORLD) && !((<undefinedtype>)(new Object() {
//                public boolean checkGamemode(Entity entity1) {
//                    if (entity1 instanceof ServerPlayer) {
//                        ServerPlayer serverplayer = (ServerPlayer)entity1;
//
//                        return serverplayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
//                    } else if (entity1.level.isClientSide() && entity1 instanceof Player) {
//                        Player player = (Player)entity1;
//
//                        return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
//                    } else {
//                        return false;
//                    }
//                }
//            })).checkGamemode(entity) && event != null && event.isCancelable()) {
//                event.setCanceled(true);
//            }
//
//        }
//    }
//}
