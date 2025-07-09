//package com.pla.annoyingvillagers.procedures;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.block.Block;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
//
//public class NoSoundBombDangYouJianDianJiKongQiShiProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
//        if (entity != null) {
//            if (entity.isShiftKeyDown()) {
//                if (entity instanceof Player) {
//                    Player player = (Player)entity;
//
//                    if (!player.level.isClientSide()) {
//                        player.displayClientMessage(new TextComponent("\u8bf7\u8fdc\u79bb"), true);
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
//                                player1.displayClientMessage(new TextComponent("3"), true);
//                            }
//                        }
//
//                        this.world.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, 1.0D, 0.0D);
//                        ((<undefinedtype>)(new Object() {
//                            private int ticks = 0;
//                            private float waitTicks;
//                            private LevelAccessor world;
//
//                            public void start(LevelAccessor levelaccessor1, int i) {
//                                this.waitTicks = (float)i;
//                                MinecraftForge.EVENT_BUS.register(this);
//                                this.world = levelaccessor1;
//                            }
//
//                            @SubscribeEvent
//                            public void tick(ServerTickEvent servertickevent) {
//                                if (servertickevent.phase == Phase.END) {
//                                    ++this.ticks;
//                                    if ((float)this.ticks >= this.waitTicks) {
//                                        this.run();
//                                    }
//                                }
//
//                            }
//
//                            private void run() {
//                                if (entity instanceof Player) {
//                                    Player player2 = (Player)entity;
//
//                                    if (!player2.level.isClientSide()) {
//                                        player2.displayClientMessage(new TextComponent("2"), true);
//                                    }
//                                }
//
//                                this.world.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, 1.0D, 0.0D);
//                                ((<undefinedtype>)(new Object() {
//                                    private int ticks;
//                                    private float waitTicks;
//                                    private LevelAccessor world;
//
//                                    {
//                                        this.this$1 = 0;
//                                        this.ticks = 0;
//                                    }
//
//                                    public void start(LevelAccessor levelaccessor1, int i) {
//                                        this.waitTicks = (float)i;
//                                        MinecraftForge.EVENT_BUS.register(this);
//                                        this.world = levelaccessor1;
//                                    }
//
//                                    @SubscribeEvent
//                                    public void tick(ServerTickEvent servertickevent) {
//                                        if (servertickevent.phase == Phase.END) {
//                                            ++this.ticks;
//                                            if ((float)this.ticks >= this.waitTicks) {
//                                                this.run();
//                                            }
//                                        }
//
//                                    }
//
//                                    private void run() {
//                                        if (this.this$1.this$0.val$entity instanceof Player) {
//                                            Player player3 = (Player)this.this$1.this$0.val$entity;
//
//                                            if (!player3.level.isClientSide()) {
//                                                player3.displayClientMessage(new TextComponent("1"), true);
//                                            }
//                                        }
//
//                                        this.world.addParticle(ParticleTypes.FIREWORK, this.this$1.this$0.val$x, this.this$1.this$0.val$y, this.this$1.this$0.val$z, 0.0D, 1.0D, 0.0D);
//                                        ((<undefinedtype>)(new Object() {
//                                            private int ticks;
//                                            private float waitTicks;
//                                            private LevelAccessor world;
//
//                                            {
//                                                this.this$2 = 0;
//                                                this.ticks = 0;
//                                            }
//
//                                            public void start(LevelAccessor levelaccessor1, int i) {
//                                                this.waitTicks = (float)i;
//                                                MinecraftForge.EVENT_BUS.register(this);
//                                                this.world = levelaccessor1;
//                                            }
//
//                                            @SubscribeEvent
//                                            public void tick(ServerTickEvent servertickevent) {
//                                                if (servertickevent.phase == Phase.END) {
//                                                    ++this.ticks;
//                                                    if ((float)this.ticks >= this.waitTicks) {
//                                                        this.run();
//                                                    }
//                                                }
//
//                                            }
//
//                                            private void run() {
//                                                if (this.this$2.this$1.this$0.val$entity instanceof Player) {
//                                                    Player player4 = (Player)this.this$2.this$1.this$0.val$entity;
//
//                                                    if (player4.getInventory().contains(new ItemStack((ItemLike)AnnoyingVillagersModItems.BOMB_SPAWN_ITEM.get()))) {
//                                                        Player player5;
//
//                                                        if (this.this$2.this$1.this$0.val$entity instanceof Player) {
//                                                            player5 = (Player)this.this$2.this$1.this$0.val$entity;
//                                                            if (!player5.level.isClientSide()) {
//                                                                player5.displayClientMessage(new TextComponent("\u9677\u9631\u5df2\u5b89\u653e"), true);
//                                                            }
//                                                        }
//
//                                                        if (this.this$2.this$1.this$0.val$entity instanceof Player) {
//                                                            player5 = (Player)this.this$2.this$1.this$0.val$entity;
//                                                            if (!player5.level.isClientSide()) {
//                                                                player5.displayClientMessage(new TextComponent("\u9677\u9631\u5df2\u5b89\u653e"), false);
//                                                            }
//                                                        }
//
//                                                        this.world.setBlock(new BlockPos(this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z), ((Block)AnnoyingVillagersModBlocks.C_4SPAWN.get()).defaultBlockState(), 3);
//                                                        if (this.this$2.this$1.this$0.val$entity instanceof Player) {
//                                                            player5 = (Player)this.this$2.this$1.this$0.val$entity;
//                                                            ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.BOMB_SPAWN_ITEM.get());
//
//                                                            player5.getInventory().clearOrCountMatchingItems((itemstack1) -> {
//                                                                return itemstack2.getItem() == itemstack1.getItem();
//                                                            }, 1, player5.inventoryMenu.getCraftSlots());
//                                                        }
//                                                    }
//                                                }
//
//                                                MinecraftForge.EVENT_BUS.unregister(this);
//                                            }
//                                        })).start(this.world, 20);
//                                        MinecraftForge.EVENT_BUS.unregister(this);
//                                    }
//                                })).start(this.world, 20);
//                                MinecraftForge.EVENT_BUS.unregister(this);
//                            }
//                        })).start(this.world, 20);
//                        MinecraftForge.EVENT_BUS.unregister(this);
//                    }
//                })).start(levelaccessor, 20);
//            }
//
//        }
//    }
//}
