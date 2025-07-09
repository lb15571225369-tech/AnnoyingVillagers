//package com.pla.annoyingvillagers.procedures;
//
//import java.util.HashMap;
//import net.minecraft.client.gui.components.EditBox;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.Commands;
//import net.minecraft.world.entity.Entity;
//import org.apache.logging.log4j.Logger;
//import com.pla.annoyingvillagers.AnnoyingVillagersMod;
//
//public class MusicSeacrhButtomProcedure {
//
//    public static void execute(Entity entity, HashMap hashmap) {
//        if (entity != null && hashmap != null) {
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                Commands commands = entity.getServer().getCommands();
//                CommandSourceStack commandsourcestack = entity.createCommandSourceStack().withSuppressedOutput().withPermission(4);
//                String s = hashmap.containsKey("text:music_seacrh") ? ((EditBox) hashmap.get("text:music_seacrh")).getValue() : "";
//
//                commands.performCommand(commandsourcestack, "music search " + s);
//            }
//
//            Logger logger = AnnoyingVillagersMod.LOGGER;
//            String s1 = entity.getDisplayName().getString();
//
//            logger.info(s1 + "\u641c\u7d22\u4e86" + (hashmap.containsKey("text:music_seacrh") ? ((EditBox) hashmap.get("text:music_seacrh")).getValue() : ""));
//        }
//    }
//}
