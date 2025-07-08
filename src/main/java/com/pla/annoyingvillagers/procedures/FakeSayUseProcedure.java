package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class FakeSayUseProcedure {

    public static void execute(LevelAccessor levelaccessor, final CommandContext<CommandSourceStack> commandcontext) {
        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
            PlayerList playerlist = levelaccessor.getServer().getPlayerList();
            String s = ((<undefinedtype>)(new Object() {
                public Entity getEntity() {
                    try {
                        return EntityArgument.getEntity(commandcontext, "player");
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        commandsyntaxexception.printStackTrace();
                        return null;
                    }
                }
            })).getEntity().getDisplayName().getString();

            playerlist.broadcastMessage(new TextComponent("<" + s + "> " + ((<undefinedtype>)(new Object() {
                public String getMessage() {
                    try {
                        return MessageArgument.getMessage(commandcontext, "message").getString();
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        return "";
                    }
                }
            })).getMessage()), ChatType.SYSTEM, Util.NIL_UUID);
        }

    }
}
