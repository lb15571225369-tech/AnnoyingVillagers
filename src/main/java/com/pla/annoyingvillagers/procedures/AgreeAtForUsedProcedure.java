package com.pla.annoyingvillagers.procedures;

import com.google.gson.JsonObject;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.io.IOException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fml.loading.FMLPaths;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;

public class AgreeAtForUsedProcedure {

    public static void execute(LevelAccessor levelaccessor, final CommandContext<CommandSourceStack> commandcontext, Entity entity) {
        if (entity != null) {
            new File("");
            new JsonObject();

            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.MAKE_A_TEAM)) {
                String s = FMLPaths.GAMEDIR.get().toString() + "/player_at/";
                String s1 = File.separator;
                File file = new File(s, s1 + ((<undefinedtype>)(new Object() {
                    public Entity getEntity() {
                        try {
                            return EntityArgument.getEntity(commandcontext, "player");
                        } catch (CommandSyntaxException commandsyntaxexception) {
                            commandsyntaxexception.printStackTrace();
                            return null;
                        }
                    }
                })).getEntity().getUUID().toString() + "_team.json");
                Player player;

                if (!file.exists()) {
                    if (entity.getPersistentData().getString("at_for_player").equals(((<undefinedtype>)(new Object() {
                        public Entity getEntity() {
                            try {
                                return EntityArgument.getEntity(commandcontext, "player");
                            } catch (CommandSyntaxException commandsyntaxexception) {
                                commandsyntaxexception.printStackTrace();
                                return null;
                            }
                        }
                    })).getEntity().getUUID().toString())) {
                        try {
                            file.getParentFile().mkdirs();
                            file.createNewFile();
                        } catch (IOException ioexception) {
                            ioexception.printStackTrace();
                        }

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

                        entity1.teleportTo(entity.getX(), entity.getY(), entity.getZ());
                        if (entity1 instanceof ServerPlayer) {
                            ServerPlayer serverplayer = (ServerPlayer)entity1;

                            serverplayer.connection.teleport(entity.getX(), entity.getY(), entity.getZ(), entity1.getYRot(), entity1.getXRot());
                        }

                        Entity entity2 = ((<undefinedtype>)(new Object() {
                            public Entity getEntity() {
                                try {
                                    return EntityArgument.getEntity(commandcontext, "player");
                                } catch (CommandSyntaxException commandsyntaxexception) {
                                    commandsyntaxexception.printStackTrace();
                                    return null;
                                }
                            }
                        })).getEntity();

                        if (entity2 instanceof Player) {
                            player = (Player)entity2;
                            if (!player.level.isClientSide()) {
                                player.displayClientMessage(new TextComponent("\u00a7e" + entity.getDisplayName().getString() + "\u540c\u610f\u4e86\u4f60\u7684\u8bf7\u6c42"), false);
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

                                player.displayClientMessage(new TextComponent("\u00a7e\u4f60\u540c\u610f\u4e86" + ((<undefinedtype>)object).getEntity().getDisplayName().getString() + "\u7684\u8bf7\u6c42"), false);
                            }
                        }
                    } else if (entity instanceof Player) {
                        player = (Player)entity;
                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u6b64\u73a9\u5bb6\u5e76\u6ca1\u6709\u5411\u4f60\u53d1\u9001\u8fc7\u8bf7\u6c42"), false);
                        }
                    }
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
