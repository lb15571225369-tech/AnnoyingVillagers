//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
//
//@EventBusSubscriber
//public class ExecutePlayerLeaveGameProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerLoggedOut(PlayerLoggedOutEvent playerloggedoutevent) {
//        execute(playerloggedoutevent, playerloggedoutevent.getPlayer());
//    }
//
//    public static void execute(Entity entity) {
//        execute((Event) null, entity);
//    }
//
//    private static void execute(@Nullable Event event, Entity entity) {
//        if (entity != null) {
//            if (entity instanceof LivingEntity) {
//                LivingEntity livingentity = (LivingEntity) entity;
//
//                if (livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get())) {
//                    if (entity instanceof LivingEntity) {
//                        LivingEntity livingentity1 = (LivingEntity) entity;
//
//                        livingentity1.removeAllEffects();
//                    }
//
//                    entity.kill();
//                }
//            }
//
//        }
//    }
//}
