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
        if (entity != null) {
            if (((<undefinedtype>)(new Object() {
                public Entity getEntity() {
                    try {
                        return EntityArgument.getEntity(commandcontext, "name");
                    } catch (CommandSyntaxException commandsyntaxexception) {
                        commandsyntaxexception.printStackTrace();
                        return null;
                    }
                }
            })).getEntity().getPersistentData().getString(entity.getUUID().toString() + "_f_fire").equals(entity.getUUID().toString())) {
                ((<undefinedtype>)(new Object() {
                    public Entity getEntity() {
                        try {
                            return EntityArgument.getEntity(commandcontext, "name");
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return null;
                        }
                    }
                })).getEntity().getPersistentData().putString(entity.getUUID().toString() + "_f_fire", "none");
                Player player;

                if (entity instanceof Player) {
                    player = (Player)entity;
                    if (!player.level.isClientSide()) {
                        Object object = new Object() {
                            public Entity getEntity() {
                                try {
                                    return EntityArgument.getEntity(commandcontext, "name");
                                } catch (CommandSyntaxException commandsyntaxexception) {
                                    commandsyntaxexception.printStackTrace();
                                    return null;
                                }
                            }
                        };

                        player.displayClientMessage(new TextComponent("\u4f60\u5df2\u5f00\u542f\u5bf9" + ((<undefinedtype>)object).getEntity().getDisplayName().getString() + "\u7684\u4f24\u5bb3"), false);
                    }
                }

                Entity entity1 = ((<undefinedtype>)(new Object() {
                    public Entity getEntity() {
                        try {
                            return EntityArgument.getEntity(commandcontext, "name");
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return null;
                        }
                    }
                })).getEntity();

                if (entity1 instanceof Player) {
                    player = (Player)entity1;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent(entity.getDisplayName().getString() + "\u5df2\u5bf9\u4f60\u5f00\u542f\u4e86\u4f24\u5bb3"), false);
                    }
                }
            }

        }
    }
}
