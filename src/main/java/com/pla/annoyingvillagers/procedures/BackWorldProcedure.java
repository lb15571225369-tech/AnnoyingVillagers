//package com.pla.annoyingvillagers.procedures;
//
//import java.util.Iterator;
//import javax.annotation.Nullable;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
//import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
//import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
//import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
//
//@EventBusSubscriber
//public class BackWorldProcedure {
//
//    @SubscribeEvent
//    public static void onEntityJoin(EntityJoinWorldEvent entityjoinworldevent) {
//        execute(entityjoinworldevent, entityjoinworldevent.getWorld(), entityjoinworldevent.getEntity());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity) {
//        execute((Event) null, levelaccessor, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
//        if (entity != null) {
//            if (entity.level.dimension() != Level.OVERWORLD && levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.BAN_OTHER_WORLD) && entity instanceof ServerPlayer) {
//                ServerPlayer serverplayer = (ServerPlayer) entity;
//
//                if (!serverplayer.level.isClientSide()) {
//                    ResourceKey<Level> resourcekey = Level.OVERWORLD;
//
//                    if (serverplayer.level.dimension() == resourcekey) {
//                        return;
//                    }
//
//                    ServerLevel serverlevel = serverplayer.server.getLevel(resourcekey);
//
//                    if (serverlevel != null) {
//                        serverplayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
//                        serverplayer.teleportTo(serverlevel, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), serverplayer.getYRot(), serverplayer.getXRot());
//                        serverplayer.connection.send(new ClientboundPlayerAbilitiesPacket(serverplayer.getAbilities()));
//                        Iterator iterator = serverplayer.getActiveEffects().iterator();
//
//                        while (iterator.hasNext()) {
//                            MobEffectInstance mobeffectinstance = (MobEffectInstance) iterator.next();
//
//                            serverplayer.connection.send(new ClientboundUpdateMobEffectPacket(serverplayer.getId(), mobeffectinstance));
//                        }
//
//                        serverplayer.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
//                    }
//                }
//            }
//
//        }
//    }
//}
