//package com.pla.annoyingvillagers.procedures;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.mojang.brigadier.arguments.StringArgumentType;
//import com.mojang.brigadier.context.CommandContext;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.arguments.MessageArgument;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.fml.loading.FMLPaths;
//
//public class ReportUseProcedure {
//
//    public static void execute(final CommandContext<CommandSourceStack> commandcontext, Entity entity) {
//        if (entity != null) {
//            new File("");
//            JsonObject jsonobject = new JsonObject();
//            JsonObject jsonobject1 = new JsonObject();
//            JsonObject jsonobject2 = new JsonObject();
//
//            new JsonObject();
//            String s = FMLPaths.GAMEDIR.get().toString() + "/player_report/" + StringArgumentType.getString(commandcontext, "name") + "/" + entity.getDisplayName().getString() + " report";
//            String s1 = File.separator;
//            File file = new File(s, s1 + StringArgumentType.getString(commandcontext, "name") + "_report.json");
//
//            try {
//                file.getParentFile().mkdirs();
//                file.createNewFile();
//            } catch (IOException ioexception) {
//                ioexception.printStackTrace();
//            }
//
//            if (!file.exists()) {
//                try {
//                    file.getParentFile().mkdirs();
//                    file.createNewFile();
//                } catch (IOException ioexception1) {
//                    ioexception1.printStackTrace();
//                }
//            }
//
//            jsonobject2.addProperty("reporter", entity.getDisplayName().getString());
//            jsonobject2.addProperty("name", StringArgumentType.getString(commandcontext, "name"));
//            jsonobject2.addProperty("ban", (int)0);
//            jsonobject2.addProperty("reason", ((<undefinedtype>)(new Object() {
//                public String getMessage() {
//                    try {
//                        return MessageArgument.getMessage(commandcontext, "reason").getString();
//                    } catch (CommandSyntaxException commandsyntaxexception) {
//                        return "";
//                    }
//                }
//            })).getMessage());
//            Player player;
//
//            if (entity instanceof Player) {
//                player = (Player)entity;
//                if (!player.level.isClientSide()) {
//                    s1 = StringArgumentType.getString(commandcontext, "name");
//                    player.displayClientMessage(new TextComponent("\u4f60\u4e3e\u62a5\u4e86" + s1 + "\uff0c\u539f\u56e0\u4e3a\uff1a" + ((<undefinedtype>)(new Object() {
//                        public String getMessage() {
//                            try {
//                                return MessageArgument.getMessage(commandcontext, "reason").getString();
//                            } catch (CommandSyntaxException commandsyntaxexception) {
//                                return "";
//                            }
//                        }
//                    })).getMessage()), false);
//                }
//            }
//
//            if (entity instanceof Player) {
//                player = (Player)entity;
//                if (!player.level.isClientSide()) {
//                    player.displayClientMessage(new TextComponent("\u5982\u9700\u8fdb\u4e00\u6b65\u4e3e\u62a5\uff0c\u53ef\u53d1\u9001\u90ae\u4ef6\u81f3pugiliststeve666@gmail.com\uff0c\u5e76\u9644\u5e26\u56fe\u7247\u8bc1\u636e"), false);
//                }
//            }
//
//            jsonobject1.add("reason", jsonobject2);
//            jsonobject1.add("player", jsonobject);
//            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//            try {
//                FileWriter filewriter = new FileWriter(file);
//
//                filewriter.write(gson.toJson((JsonElement)jsonobject2));
//                filewriter.close();
//            } catch (IOException ioexception2) {
//                ioexception2.printStackTrace();
//            }
//
//        }
//    }
//}
