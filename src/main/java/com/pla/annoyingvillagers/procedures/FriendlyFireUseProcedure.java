//package com.pla.annoyingvillagers.procedures;
//
//import com.mojang.brigadier.context.CommandContext;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import net.minecraft.commands.CommandSourceStack;
//import net.minecraft.commands.arguments.EntityArgument;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class FriendlyFireUseProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, final CommandContext<CommandSourceStack> commandcontext, final Entity entity) {
//        if (entity != null) {
//            Player player;
//            Object object;
//
//            if (((<undefinedtype>)(new Object() {
//                public Entity getEntity() {
//                    try {
//                        return EntityArgument.getEntity(commandcontext, "name");
//                    } catch (CommandSyntaxException commandsyntaxexception) {
//                        commandsyntaxexception.printStackTrace();
//                        return null;
//                    }
//                }
//            })).getEntity().getPersistentData().getString(entity.getUUID().toString() + "_f_fire").equals(entity.getUUID().toString())) {
//                if (entity instanceof Player) {
//                    player = (Player)entity;
//                    if (!player.level.isClientSide()) {
//                        object = new Object() {
//                            public Entity getEntity() {
//                                try {
//                                    return EntityArgument.getEntity(commandcontext, "name");
//                                } catch (CommandSyntaxException commandsyntaxexception) {
//                                    commandsyntaxexception.printStackTrace();
//                                    return null;
//                                }
//                            }
//                        };
//                        player.displayClientMessage(new TextComponent("\u4f60\u5df2\u7ecf\u5c06\u53cb\u4f24\u5173\u95ed\u5728" + ((<undefinedtype>)object).getEntity().getDisplayName().getString() + "\u4e0a\u4e86"), false);
//                    }
//                }
//            } else {
//                ((<undefinedtype>)(new Object() {
//                    public Entity getEntity() {
//                        try {
//                            return EntityArgument.getEntity(commandcontext, "name");
//                        } catch (CommandSyntaxException commandsyntaxexception) {
//                            commandsyntaxexception.printStackTrace();
//                            return null;
//                        }
//                    }
//                })).getEntity().getPersistentData().putString(entity.getUUID().toString() + "_f_fire", entity.getUUID().toString());
//                if (entity instanceof Player) {
//                    player = (Player)entity;
//                    if (!player.level.isClientSide()) {
//                        object = new Object() {
//                            public Entity getEntity() {
//                                try {
//                                    return EntityArgument.getEntity(commandcontext, "name");
//                                } catch (CommandSyntaxException commandsyntaxexception) {
//                                    commandsyntaxexception.printStackTrace();
//                                    return null;
//                                }
//                            }
//                        };
//                        player.displayClientMessage(new TextComponent("\u5df2\u5c06\u53cb\u4f24\u5728" + ((<undefinedtype>)object).getEntity().getDisplayName().getString() + "\u4e0a\u5173\u95ed\uff0c\u73b0\u5728\u4f60\u65e0\u6cd5\u4f24\u5bb3\u4ed6\uff0c\u00a7e\u8bf7\u8ba9\u5bf9\u65b9\u4f7f\u7528/f-fire \u4f60\u7684ID \u6765\u8ba9\u5bf9\u65b9\u4e5f\u65e0\u6cd5\u4f24\u5bb3\u5230\u4f60"), false);
//                    }
//                }
//
//                Entity entity1 = ((<undefinedtype>)(new Object() {
//                    public Entity getEntity() {
//                        try {
//                            return EntityArgument.getEntity(commandcontext, "name");
//                        } catch (CommandSyntaxException commandsyntaxexception) {
//                            commandsyntaxexception.printStackTrace();
//                            return null;
//                        }
//                    }
//                })).getEntity();
//
//                if (entity1 instanceof Player) {
//                    player = (Player)entity1;
//                    if (!player.level.isClientSide()) {
//                        String s = entity.getDisplayName().getString();
//
//                        player.displayClientMessage(new TextComponent(s + "\u00a7e\u73b0\u5728\u65e0\u6cd5\u4f24\u5bb3\u5230\u4f60\uff0c\u5982\u679c\u4f60\u4e5f\u4e0d\u60f3\u518d\u653b\u51fb\u5230" + entity.getDisplayName().getString() + "\uff0c\u8bf7\u4f7f\u7528/f-fire \u6b64\u73a9\u5bb6ID \u6765\u5173\u95ed\u5bf9" + entity.getDisplayName().getString() + "\u7684\u4f24\u5bb3"), false);
//                    }
//                }
//
//                ((<undefinedtype>)(new Object() {
//                    private int ticks = 0;
//                    private float waitTicks;
//                    private LevelAccessor world;
//
//                    public void start(LevelAccessor levelaccessor1, int i) {
//                        this.waitTicks = (float)i;
//                        MinecraftForge.EVENT_BUS.register(this);
//                        this.world = levelaccessor1;
//                    }
//
//                    @SubscribeEvent
//                    public void tick(ServerTickEvent servertickevent) {
//                        if (servertickevent.phase == Phase.END) {
//                            ++this.ticks;
//                            if ((float)this.ticks >= this.waitTicks) {
//                                this.run();
//                            }
//                        }
//
//                    }
//
//                    private void run() {
//                        if (entity instanceof Player) {
//                            Player player1 = (Player)entity;
//
//                            if (!player1.level.isClientSide()) {
//                                player1.displayClientMessage(new TextComponent("\u00a7e\u5982\u679c\u4f60\u9700\u8981\u4f24\u5bb3\u6b64\u73a9\u5bb6\u7684\u8bdd\uff0c\u8bf7\u4f7f\u7528\u6307\u4ee4/f-fire-off \u76ee\u6807ID"), false);
//                            }
//                        }
//
//                        MinecraftForge.EVENT_BUS.unregister(this);
//                    }
//                })).start(levelaccessor, 20);
//            }
//
//        }
//    }
//}
