package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FFireOffUseProcedure {

    public static void execute(final CommandContext<CommandSourceStack> commandcontext, Entity entity) {
        if (entity == null) return;

        Entity targetEntity;
        try {
            targetEntity = EntityArgument.getEntity(commandcontext, "name");
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return;
        }

        String key = entity.getUUID().toString() + "_f_fire";
        if (targetEntity.getPersistentData().getString(key).equals(entity.getUUID().toString())) {
            targetEntity.getPersistentData().putString(key, "none");

            if (entity instanceof Player player && !player.level.isClientSide()) {
                player.displayClientMessage(new TextComponent("你已开启对 " + targetEntity.getDisplayName().getString() + " 的伤害"), false);
            }

            if (targetEntity instanceof Player targetPlayer && !targetPlayer.level.isClientSide()) {
                targetPlayer.displayClientMessage(new TextComponent(entity.getDisplayName().getString() + " 已对你开启了伤害"), false);
            }
        }
    }
}
