package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoneobDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^ minecraft:air");
            }

            ((<undefinedtype>)(new Object() {
                private int ticks = 0;
                private float waitTicks;
                private LevelAccessor world;

                public void start(LevelAccessor levelaccessor1, int i) {
                    this.waitTicks = (float)i;
                    MinecraftForge.EVENT_BUS.register(this);
                    this.world = levelaccessor1;
                }

                @SubscribeEvent
                public void tick(ServerTickEvent servertickevent) {
                    if (servertickevent.phase == Phase.END) {
                        ++this.ticks;
                        if ((float)this.ticks >= this.waitTicks) {
                            this.run();
                        }
                    }

                }

                private void run() {
                    Entity entity1 = entity;

                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoying_villagers:darkob");
                    }

                    ((<undefinedtype>)(new Object() {
                        private int ticks = 0;
                        private float waitTicks;
                        private LevelAccessor world;

                        public void start(LevelAccessor levelaccessor1, int i) {
                            this.waitTicks = (float)i;
                            MinecraftForge.EVENT_BUS.register(this);
                            this.world = levelaccessor1;
                        }

                        @SubscribeEvent
                        public void tick(ServerTickEvent servertickevent) {
                            if (servertickevent.phase == Phase.END) {
                                ++this.ticks;
                                if ((float)this.ticks >= this.waitTicks) {
                                    this.run();
                                }
                            }

                        }

                        private void run() {
                            Entity entity2 = entity;

                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^ annoying_villagers:darkob");
                            }

                            ((<undefinedtype>)(new Object() {
                                private int ticks;
                                private float waitTicks;
                                private LevelAccessor world;

                                {
                                    this.this$1 = 0;
                                    this.ticks = 0;
                                }

                                public void start(LevelAccessor levelaccessor1, int i) {
                                    this.waitTicks = (float)i;
                                    MinecraftForge.EVENT_BUS.register(this);
                                    this.world = levelaccessor1;
                                }

                                @SubscribeEvent
                                public void tick(ServerTickEvent servertickevent) {
                                    if (servertickevent.phase == Phase.END) {
                                        ++this.ticks;
                                        if ((float)this.ticks >= this.waitTicks) {
                                            this.run();
                                        }
                                    }

                                }

                                private void run() {
                                    Entity entity3 = this.this$1.this$0.val$entity;

                                    if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                        entity3.getServer().getCommands().performCommand(entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^-1 annoying_villagers:darkob");
                                    }

                                    ((<undefinedtype>)(new Object() {
                                        private int ticks;
                                        private float waitTicks;
                                        private LevelAccessor world;

                                        {
                                            this.this$2 = 0;
                                            this.ticks = 0;
                                        }

                                        public void start(LevelAccessor levelaccessor1, int i) {
                                            this.waitTicks = (float)i;
                                            MinecraftForge.EVENT_BUS.register(this);
                                            this.world = levelaccessor1;
                                        }

                                        @SubscribeEvent
                                        public void tick(ServerTickEvent servertickevent) {
                                            if (servertickevent.phase == Phase.END) {
                                                ++this.ticks;
                                                if ((float)this.ticks >= this.waitTicks) {
                                                    this.run();
                                                }
                                            }

                                        }

                                        private void run() {
                                            Entity entity4 = this.this$2.this$1.this$0.val$entity;

                                            if (!entity4.level.isClientSide() && entity4.getServer() != null) {
                                                entity4.getServer().getCommands().performCommand(entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^-2 annoying_villagers:darkob");
                                            }

                                            ((<undefinedtype>)(new Object() {
                                                private int ticks;
                                                private float waitTicks;
                                                private LevelAccessor world;

                                                {
                                                    this.this$3 = 0;
                                                    this.ticks = 0;
                                                }

                                                public void start(LevelAccessor levelaccessor1, int i) {
                                                    this.waitTicks = (float)i;
                                                    MinecraftForge.EVENT_BUS.register(this);
                                                    this.world = levelaccessor1;
                                                }

                                                @SubscribeEvent
                                                public void tick(ServerTickEvent servertickevent) {
                                                    if (servertickevent.phase == Phase.END) {
                                                        ++this.ticks;
                                                        if ((float)this.ticks >= this.waitTicks) {
                                                            this.run();
                                                        }
                                                    }

                                                }

                                                private void run() {
                                                    Entity entity5 = this.this$3.this$2.this$1.this$0.val$entity;

                                                    if (!entity5.level.isClientSide() && entity5.getServer() != null) {
                                                        entity5.getServer().getCommands().performCommand(entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute as @s at @s anchored eyes run setblock ^ ^ ^-3 annoying_villagers:darkob");
                                                    }

                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(this.world, 2);
                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(this.world, 2);
                                    MinecraftForge.EVENT_BUS.unregister(this);
                                }
                            })).start(this.world, 2);
                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(this.world, 2);
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            })).start(levelaccessor, 20);
        }
    }
}
