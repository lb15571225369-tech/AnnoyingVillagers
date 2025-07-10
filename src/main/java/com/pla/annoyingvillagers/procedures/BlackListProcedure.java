//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.Util;
//import net.minecraft.network.chat.ChatType;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Explosion.BlockInteraction;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//
//@EventBusSubscriber
//public class BlackListProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerLoggedIn(PlayerLoggedInEvent playerloggedinevent) {
//        execute(playerloggedinevent, playerloggedinevent.getPlayer().level, playerloggedinevent.getPlayer().getX(), playerloggedinevent.getPlayer().getY(), playerloggedinevent.getPlayer().getZ(), playerloggedinevent.getPlayer());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        execute((Event) null, levelaccessor, d0, d1, d2, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
//        if (entity != null) {
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "attribute @s epicfight:staminar base set 100");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "attribute @s combatroll:distance base set 0");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight skill add @s passive1 annoyingvillagers:clash");
//            }
//
//            int i;
//
//            if (entity instanceof Player) {
//                Player player = (Player)entity;
//
//                i = player.experienceLevel;
//            } else {
//                i = 0;
//            }
//
//            if (i <= 3) {
//                if (!entity.level.isClientSide() && entity.getServer() != null) {
//                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight skill add @s guard epicfight:guard");
//                }
//
//                if (!entity.level.isClientSide() && entity.getServer() != null) {
//                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight skill add @s guard epicfight:parrying");
//                }
//
//                if (!entity.level.isClientSide() && entity.getServer() != null) {
//                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight skill add @s dodge epicfight:step");
//                }
//
//                if (!entity.level.isClientSide() && entity.getServer() != null) {
//                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight skill add @s identity epicfight:meteor_slam");
//                }
//
//                if (!entity.level.isClientSide() && entity.getServer() != null) {
//                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight skill add @s mover epicfight:phantom_ascent");
//                }
//            }
//
//            (new Object() {
//                private int ticks = 0;
//                private float waitTicks;
//                private LevelAccessor world;
//
//                public void start(LevelAccessor levelaccessor1, int j) {
//                    this.waitTicks = (float)j;
//                    MinecraftForge.EVENT_BUS.register(this);
//                    this.world = levelaccessor1;
//                }
//
//                @SubscribeEvent
//                public void tick(ServerTickEvent servertickevent) {
//                    if (servertickevent.phase == Phase.END) {
//                        ++this.ticks;
//                        if ((float)this.ticks >= this.waitTicks) {
//                            this.run();
//                        }
//                    }
//
//                }
//
//                private void run() {
//                    Entity entity1 = entity;
//
//                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "title @s title {\"text\":\"\u4f5c\u8005:Pugilist_Steve\",\"color\":\"green\"}");
//                    }
//
//                    entity1 = entity;
//                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute as @s run execute if entity @s run title @s actionbar {\"text\":\"\u4f5c\u8005:Pugilist_Steve \u5e73\u53f0:Bilibili\",\"italic\":true,\"color\":\"green\"}");
//                    }
//
//                    MinecraftForge.EVENT_BUS.unregister(this);
//                }
//            }).start(levelaccessor, 30);
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @s {\"text\":\"\u4f5c\u8005:@Pugilist_Steve\",\"color\":\"red\"}");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @s {\"text\":\"\u5e73\u53f0:\u54d4\u54e9\u54d4\u54e9 (B\u7ad9)\",\"color\":\"green\"}");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "tellraw @s {\"text\":\"\u5982\u8981\u53d1\u5e03\u5173\u4e8e\u6b64\u6574\u5408\u5305\u7684\u89c6\u9891\uff0c\u52a1\u5fc5\u6807\u6ce8\u539f\u4f5c\u8005\u53ca\u5e73\u53f0!\",\"color\":\"yellow\"},");
//            }
//
//            if (entity.getPersistentData().getBoolean("b_d_aim")) {
//                Level level;
//
//                if (levelaccessor instanceof Level) {
//                    level = (Level)levelaccessor;
//                    if (!level.isClientSide()) {
//                        level.explode((Entity)null, d0, d1, d2, 20.0F, BlockInteraction.NONE);
//                    }
//                }
//
//                if (levelaccessor instanceof Level) {
//                    level = (Level)levelaccessor;
//                    if (!level.isClientSide()) {
//                        level.explode((Entity)null, d0, d1, d2, 20.0F, BlockInteraction.DESTROY);
//                    }
//                }
//
//                entity.getPersistentData().putBoolean("b_d_aim", false);
//                (new Object() {
//                    private int ticks = 0;
//                    private float waitTicks;
//                    private LevelAccessor world;
//
//                    public void start(LevelAccessor levelaccessor1, int j) {
//                        this.waitTicks = (float)j;
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
//                        if (!this.world.isClientSide() && this.world.getServer() != null) {
//                            this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u54fc\uff0c\u60f3\u9003\uff1f"), ChatType.SYSTEM, Util.NIL_UUID);
//                        }
//
//                        MinecraftForge.EVENT_BUS.unregister(this);
//                    }
//                }).start(levelaccessor, 20);
//            }
//
//            entity.getPersistentData().putBoolean("kick_x", false);
//            entity.getPersistentData().putDouble("air_kick", 0.0D);
//            entity.getPersistentData().putDouble("kick", 0.0D);
//            entity.getPersistentData().putDouble("axe_a", 0.0D);
//            entity.getPersistentData().putDouble("sword_a", 0.0D);
//            entity.getPersistentData().putDouble("fist_a", 0.0D);
//            entity.getPersistentData().putDouble("dash_auto", 0.0D);
//        }
//    }
//}
