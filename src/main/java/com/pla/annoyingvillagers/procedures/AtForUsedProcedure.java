package com.pla.annoyingvillagers.procedures;

import com.google.gson.JsonObject;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fml.loading.FMLPaths;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;

public class AtForUsedProcedure {

    public static void execute(LevelAccessor levelaccessor, final CommandContext<CommandSourceStack> commandcontext, Entity entity) {
        if (entity != null) {
            new File("");
            new JsonObject();

            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.MAKE_A_TEAM)) {
                String s = FMLPaths.GAMEDIR.get().toString() + "/player_at/";
                String s1 = File.separator;
                File file = new File(s, s1 + entity.getUUID().toString() + "_team.json");
                Player player;

                if (!file.exists()) {
                    Entity entity1 = ((<undefinedtype>)(new Object() {
                        public Entity getEntity() {
                            try {
                                return EntityArgument.getEntity(commandcontext, "player");
                            } catch (CommandSyntaxException commandsyntaxexception) {
                                commandsyntaxexception.printStackTrace();
                                return null;
                            }
                        }
                    })).getEntity();

                    if (entity1 instanceof Player) {
                        player = (Player)entity1;
                        if (!player.level.isClientSide()) {
                            s1 = entity.getDisplayName().getString();
                            player.displayClientMessage(new TextComponent("\u00a7e" + s1 + "\u5411\u4f60\u53d1\u9001\u4e86\u7ec4\u961f\u4f20\u9001\u8bf7\u6c42\uff0c\u8f93\u5165\u6307\u4ee4/agree-at-for " + entity.getDisplayName().getString() + " \u6765\u540c\u610f"), false);
                        }
                    }

                    if (entity instanceof Player) {
                        player = (Player)entity;
                        if (!player.level.isClientSide()) {
                            Object object = new Object() {
                                public Entity getEntity() {
                                    try {
                                        return EntityArgument.getEntity(commandcontext, "player");
                                    } catch (CommandSyntaxException commandsyntaxexception) {
                                        commandsyntaxexception.printStackTrace();
                                        return null;
                                    }
                                }
                            };

                            player.displayClientMessage(new TextComponent("\u00a7e\u4f60\u5c06\u8bf7\u6c42\u53d1\u9001\u81f3\u4e86" + ((<undefinedtype>)object).getEntity().getDisplayName().getString()), false);
                        }
                    }

                    ((<undefinedtype>)(new Object() {
                        public Entity getEntity() {
                            try {
                                return EntityArgument.getEntity(commandcontext, "player");
                            } catch (CommandSyntaxException commandsyntaxexception) {
                                commandsyntaxexception.printStackTrace();
                                return null;
                            }
                        }
                    })).getEntity().getPersistentData().putString("at_for_player", entity.getUUID().toString());
                } else if (entity instanceof Player) {
                    player = (Player)entity;
                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u4f60\u65e0\u6cd5\u518d\u4f7f\u7528\u8be5\u6307\u4ee4"), false);
                    }
                }
            }

        }
    }
}
