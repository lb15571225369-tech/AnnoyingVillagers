//package com.pla.annoyingvillagers.procedures;
//
//import com.mojang.brigadier.arguments.StringArgumentType;
//import com.mojang.brigadier.context.CommandContext;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Calendar;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.arguments.EntityArgument;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.fml.loading.FMLPaths;
//
//public class TellSendProcedure {
//
//    public static void execute(final CommandContext<CommandSourceStack> commandcontext, Entity entity) {
//        if (entity != null) {
//            new File("");
//            File file = new File(FMLPaths.GAMEDIR.get().toString(), File.separator + "chat_log.txt");
//            FileWriter filewriter;
//            BufferedWriter bufferedwriter;
//            int i;
//
//            if (file.exists()) {
//                try {
//                    filewriter = new FileWriter(file, true);
//                    bufferedwriter = new BufferedWriter(filewriter);
//                    i = Calendar.getInstance().get(1);
//                    bufferedwriter.write("[" + i + "\u5e74" + Calendar.getInstance().get(2) + "\u6708" + Calendar.getInstance().get(5) + "\u65e5 " + Calendar.getInstance().get(11) + ":" + Calendar.getInstance().get(12) + "]<" + entity.getDisplayName().getString() + "> /tell " + ((<undefinedtype>)(new Object() {
//                        public Entity getEntity() {
//                            try {
//                                return EntityArgument.getEntity(commandcontext, "player");
//                            } catch (CommandSyntaxException commandsyntaxexception) {
//                                commandsyntaxexception.printStackTrace();
//                                return null;
//                            }
//                        }
//                    })).getEntity().getDisplayName().getString() + " " + StringArgumentType.getString(commandcontext, "message"));
//                    bufferedwriter.newLine();
//                    bufferedwriter.close();
//                    filewriter.close();
//                } catch (IOException ioexception) {
//                    ioexception.printStackTrace();
//                }
//            } else {
//                try {
//                    file.getParentFile().mkdirs();
//                    file.createNewFile();
//                } catch (IOException ioexception1) {
//                    ioexception1.printStackTrace();
//                }
//
//                try {
//                    filewriter = new FileWriter(file, true);
//                    bufferedwriter = new BufferedWriter(filewriter);
//                    i = Calendar.getInstance().get(1);
//                    bufferedwriter.write("[" + i + "\u5e74" + Calendar.getInstance().get(2) + "\u6708" + Calendar.getInstance().get(5) + "\u65e5 " + Calendar.getInstance().get(11) + ":" + Calendar.getInstance().get(12) + "]<" + entity.getDisplayName().getString() + "> /tell " + ((<undefinedtype>)(new Object() {
//                        public Entity getEntity() {
//                            try {
//                                return EntityArgument.getEntity(commandcontext, "player");
//                            } catch (CommandSyntaxException commandsyntaxexception) {
//                                commandsyntaxexception.printStackTrace();
//                                return null;
//                            }
//                        }
//                    })).getEntity().getDisplayName().getString() + " " + StringArgumentType.getString(commandcontext, "message"));
//                    bufferedwriter.newLine();
//                    bufferedwriter.close();
//                    filewriter.close();
//                } catch (IOException ioexception2) {
//                    ioexception2.printStackTrace();
//                }
//            }
//
//            Entity entity1 = ((<undefinedtype>)(new Object() {
//                public Entity getEntity() {
//                    try {
//                        return EntityArgument.getEntity(commandcontext, "player");
//                    } catch (CommandSyntaxException commandsyntaxexception) {
//                        commandsyntaxexception.printStackTrace();
//                        return null;
//                    }
//                }
//            })).getEntity();
//
//            if (entity1 instanceof Player) {
//                Player player = (Player)entity1;
//
//                if (!player.level.isClientSide()) {
//                    String s = entity.getDisplayName().getString();
//
//                    player.displayClientMessage(new TextComponent(s + "\u544a\u8bc9\u4f60\uff1a" + StringArgumentType.getString(commandcontext, "message")), false);
//                }
//            }
//
//        }
//    }
//}
