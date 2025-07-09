//package com.pla.annoyingvillagers.procedures;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import javax.annotation.Nullable;
//import net.minecraft.client.Minecraft;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.loading.FMLPaths;
//import net.minecraftforge.server.ServerLifecycleHooks;
//import org.apache.logging.log4j.Logger;
//import com.pla.annoyingvillagers.AnnoyingVillagersMod;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
//
//@EventBusSubscriber
//public class RespawnTpProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerRespawned(PlayerRespawnEvent playerrespawnevent) {
//        execute(playerrespawnevent, playerrespawnevent.getPlayer().level, playerrespawnevent.getPlayer());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity) {
//        execute((Event) null, levelaccessor, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
//        if (entity != null) {
//            new File("");
//            new JsonObject();
//            String s = FMLPaths.GAMEDIR.get().toString() + "/" + (levelaccessor.isClientSide() ? Minecraft.getInstance().getSingleplayerServer().getWorldData().getLevelName() : ServerLifecycleHooks.getCurrentServer().getWorldData().getLevelName()) + "/player_spawn/";
//            String s1 = File.separator;
//            File file = new File(s, s1 + entity.getUUID().toString() + "_spawn.json");
//
//            if (file.exists()) {
//                try {
//                    BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
//                    StringBuilder stringbuilder = new StringBuilder();
//
//                    String s2;
//
//                    while ((s2 = bufferedreader.readLine()) != null) {
//                        stringbuilder.append(s2);
//                    }
//
//                    bufferedreader.close();
//                    JsonObject jsonobject = (JsonObject) (new Gson()).fromJson(stringbuilder.toString(), JsonObject.class);
//                    ServerPlayer serverplayer;
//                    Logger logger;
//                    String s3;
//
//                    if (!levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.RADOM_SPAWN)) {
//                        entity.teleportTo(jsonobject.get("x").getAsDouble(), jsonobject.get("y").getAsDouble(), jsonobject.get("z").getAsDouble());
//                        if (entity instanceof ServerPlayer) {
//                            serverplayer = (ServerPlayer) entity;
//                            serverplayer.connection.teleport(jsonobject.get("x").getAsDouble(), jsonobject.get("y").getAsDouble(), jsonobject.get("z").getAsDouble(), entity.getYRot(), entity.getXRot());
//                        }
//
//                        logger = AnnoyingVillagersMod.LOGGER;
//                        s3 = entity.getDisplayName().getString();
//                        logger.info(s3 + "\u91cd\u751f\u5230\u4e86x:" + jsonobject.get("x").getAsDouble() + ", y:" + jsonobject.get("y").getAsDouble() + ", z:" + jsonobject.get("z").getAsDouble());
//                    } else {
//                        Player player;
//
//                        if (jsonobject.get("used_bed").getAsString().equals("true")) {
//                            entity.teleportTo(jsonobject.get("x").getAsDouble(), jsonobject.get("y").getAsDouble(), jsonobject.get("z").getAsDouble());
//                            if (entity instanceof ServerPlayer) {
//                                serverplayer = (ServerPlayer) entity;
//                                serverplayer.connection.teleport(jsonobject.get("x").getAsDouble(), jsonobject.get("y").getAsDouble(), jsonobject.get("z").getAsDouble(), entity.getYRot(), entity.getXRot());
//                            }
//
//                            logger = AnnoyingVillagersMod.LOGGER;
//                            s3 = entity.getDisplayName().getString();
//                            logger.info(s3 + "\u91cd\u751f\u5230\u4e86x:" + jsonobject.get("x").getAsDouble() + ", y:" + jsonobject.get("y").getAsDouble() + ", z:" + jsonobject.get("z").getAsDouble());
//                            jsonobject.addProperty("used_bed", "false");
//                            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//                            try {
//                                FileWriter filewriter = new FileWriter(file);
//
//                                filewriter.write(gson.toJson((JsonElement) jsonobject));
//                                filewriter.close();
//                            } catch (IOException ioexception) {
//                                ioexception.printStackTrace();
//                            }
//
//                            if (entity instanceof Player) {
//                                player = (Player) entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent("\u518d\u6b21\u70b9\u5e8a\u6765\u4fdd\u5b58\u91cd\u751f\u70b9"), false);
//                                }
//                            }
//
//                            if (entity instanceof Player) {
//                                player = (Player) entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent("\u5df2\u4f20\u9001\u81f3\u91cd\u751f\u70b9\uff0c\u8bf7\u8bb0\u5f97\u518d\u6b21\u70b9\u5e8a\u6765\u4fdd\u5b58"), true);
//                                }
//                            }
//                        } else {
//                            if (entity instanceof Player) {
//                                player = (Player) entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent("\u4f60\u672a\u4fdd\u5b58\u91cd\u751f\u70b9\uff0c\u5df2\u88ab\u968f\u673a\u4f20\u9001"), true);
//                                }
//                            }
//
//                            if (entity instanceof Player) {
//                                player = (Player) entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent("\u4f60\u672a\u4fdd\u5b58\u91cd\u751f\u70b9\uff0c\u5df2\u88ab\u968f\u673a\u4f20\u9001"), false);
//                                }
//                            }
//                        }
//                    }
//                } catch (IOException ioexception1) {
//                    ioexception1.printStackTrace();
//                }
//            } else if (!levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.RADOM_SPAWN)) {
//                entity.teleportTo((double) levelaccessor.getLevelData().getXSpawn(), (double) levelaccessor.getLevelData().getYSpawn(), (double) levelaccessor.getLevelData().getZSpawn());
//                if (entity instanceof ServerPlayer) {
//                    ServerPlayer serverplayer1 = (ServerPlayer) entity;
//
//                    serverplayer1.connection.teleport((double) levelaccessor.getLevelData().getXSpawn(), (double) levelaccessor.getLevelData().getYSpawn(), (double) levelaccessor.getLevelData().getZSpawn(), entity.getYRot(), entity.getXRot());
//                }
//            } else {
//                Player player1;
//
//                if (entity instanceof Player) {
//                    player1 = (Player) entity;
//                    if (!player1.level.isClientSide()) {
//                        player1.displayClientMessage(new TextComponent("\u4f60\u672a\u4fdd\u5b58\u91cd\u751f\u70b9\uff0c\u5df2\u88ab\u968f\u673a\u4f20\u9001"), true);
//                    }
//                }
//
//                if (entity instanceof Player) {
//                    player1 = (Player) entity;
//                    if (!player1.level.isClientSide()) {
//                        player1.displayClientMessage(new TextComponent("\u4f60\u672a\u4fdd\u5b58\u91cd\u751f\u70b9\uff0c\u5df2\u88ab\u968f\u673a\u4f20\u9001"), false);
//                    }
//                }
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "attribute @s combatroll:distance base set 0");
//            }
//
//        }
//    }
//}
