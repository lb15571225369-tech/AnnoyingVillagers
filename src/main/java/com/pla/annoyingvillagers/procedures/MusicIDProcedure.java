//package com.pla.annoyingvillagers.procedures;
//
//import java.util.HashMap;
//import net.minecraft.client.gui.components.EditBox;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.Commands;
//import net.minecraft.world.entity.Entity;
//
//public class MusicIDProcedure {
//
//    public static void execute(Entity entity, HashMap hashmap) {
//        if (entity != null && hashmap != null) {
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                Commands commands = entity.getServer().getCommands();
//                CommandSourceStack commandsourcestack = entity.createCommandSourceStack().withSuppressedOutput().withPermission(4);
//                String s = hashmap.containsKey("text:music_id") ? ((EditBox) hashmap.get("text:music_id")).getValue() : "";
//
//                commands.performCommand(commandsourcestack, "music " + s);
//            }
//
//        }
//    }
//}
