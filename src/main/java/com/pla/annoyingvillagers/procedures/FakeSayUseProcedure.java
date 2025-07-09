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

    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> context) {
        if (world.isClientSide() || world.getServer() == null) return;

        PlayerList playerList = world.getServer().getPlayerList();

        String playerName;
        String messageText;

        try {
            Entity targetEntity = EntityArgument.getEntity(context, "player");
            playerName = targetEntity.getDisplayName().getString();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            playerName = "???";
        }

        try {
            messageText = MessageArgument.getMessage(context, "message").getString();
        } catch (CommandSyntaxException e) {
            messageText = "";
        }

        playerList.broadcastMessage(new TextComponent("<" + playerName + "> " + messageText), ChatType.SYSTEM, Util.NIL_UUID);
    }
}
