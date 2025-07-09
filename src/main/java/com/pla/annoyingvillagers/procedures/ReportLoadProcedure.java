//package com.pla.annoyingvillagers.procedures;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.mojang.brigadier.arguments.StringArgumentType;
//import com.mojang.brigadier.context.CommandContext;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.fml.loading.FMLPaths;
//
//public class ReportLoadProcedure {
//
//    public static void execute(CommandContext<CommandSourceStack> commandcontext, Entity entity) {
//        if (entity != null) {
//            new File("");
//            new JsonObject();
//            new JsonObject();
//            new JsonObject();
//            new JsonObject();
//            String s = FMLPaths.GAMEDIR.get().toString() + "/player_report/" + StringArgumentType.getString(commandcontext, "name") + "/" + entity.getDisplayName().getString() + " report";
//            String s1 = File.separator;
//            File file = new File(s, s1 + StringArgumentType.getString(commandcontext, "name") + "_report.json");
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
//                    Player player;
//
//                    if (jsonobject.get("ban").getAsDouble() == 0.0D) {
//                        if (entity instanceof Player) {
//                            player = (Player) entity;
//                            if (!player.level.isClientSide()) {
//                                player.displayClientMessage(new TextComponent("\u6b64\u73a9\u5bb6\u6b63\u5728\u88ab\u8c03\u67e5\u4e2d"), false);
//                            }
//                        }
//                    } else if (jsonobject.get("ban").getAsDouble() == 1.0D) {
//                        if (entity instanceof Player) {
//                            player = (Player) entity;
//                            if (!player.level.isClientSide()) {
//                                player.displayClientMessage(new TextComponent("\u6b64\u73a9\u5bb6\u5df2\u88ab\u6c38\u4e45\u5c01\u7981"), false);
//                            }
//                        }
//                    } else if (jsonobject.get("ban").getAsDouble() == -1.0D) {
//                        if (entity instanceof Player) {
//                            player = (Player) entity;
//                            if (!player.level.isClientSide()) {
//                                player.displayClientMessage(new TextComponent("\u6b64\u73a9\u5bb6\u6ca1\u6709\u8fdd\u89c4\u884c\u4e3a"), false);
//                            }
//                        }
//                    } else if (jsonobject.get("ban").getAsDouble() >= 2.0D && entity instanceof Player) {
//                        player = (Player) entity;
//                        if (!player.level.isClientSide()) {
//                            player.displayClientMessage(new TextComponent("\u6b64\u73a9\u5bb6\u5df2\u88ab\u5c01\u7981" + jsonobject.get("ban").getAsDouble() + "\u5929"), false);
//                        }
//                    }
//                } catch (IOException ioexception) {
//                    ioexception.printStackTrace();
//                }
//            } else if (entity instanceof Player) {
//                Player player1 = (Player) entity;
//
//                if (!player1.level.isClientSide()) {
//                    player1.displayClientMessage(new TextComponent("\u6b64\u73a9\u5bb6\u672a\u88ab\u4e3e\u62a5"), false);
//                }
//            }
//
//        }
//    }
//}
