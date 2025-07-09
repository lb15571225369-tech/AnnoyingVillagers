//package com.pla.annoyingvillagers.procedures;
//
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class ThownAimAnXiaAnJianShiProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
//        if (entity != null) {
//            if (!entity.getPersistentData().getBoolean("th_e")) {
//                if (!entity.getPersistentData().getBoolean("th_cooldown")) {
//                    entity.getPersistentData().putBoolean("th_e", true);
//                    entity.getPersistentData().putBoolean("th_cooldown", true);
//                    ((<undefinedtype>)(new Object() {
//                        private int ticks = 0;
//                        private float waitTicks;
//                        private LevelAccessor world;
//
//                        public void start(LevelAccessor levelaccessor1, int i) {
//                            this.waitTicks = (float)i;
//                            MinecraftForge.EVENT_BUS.register(this);
//                            this.world = levelaccessor1;
//                        }
//
//                        @SubscribeEvent
//                        public void tick(ServerTickEvent servertickevent) {
//                            if (servertickevent.phase == Phase.END) {
//                                ++this.ticks;
//                                if ((float)this.ticks >= this.waitTicks) {
//                                    this.run();
//                                }
//                            }
//
//                        }
//
//                        private void run() {
//                            entity.getPersistentData().putBoolean("th_cooldown", false);
//                            MinecraftForge.EVENT_BUS.unregister(this);
//                        }
//                    })).start(levelaccessor, 80);
//                } else if (entity instanceof Player) {
//                    Player player = (Player)entity;
//
//                    if (!player.level.isClientSide()) {
//                        player.displayClientMessage(new TextComponent("\u51b7\u5374\u4e2d"), true);
//                    }
//                }
//            }
//
//        }
//    }
//}
