//package com.pla.annoyingvillagers.procedures;
//
//import java.util.Comparator;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Explosion.BlockInteraction;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.registries.ForgeRegistries;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
//
//public class C4FangZhiFangKuaiShiProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
//        Level level;
//
//        if (levelaccessor instanceof Level) {
//            level = (Level)levelaccessor;
//            if (!level.isClientSide()) {
//                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 1.0F);
//            } else {
//                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
//            }
//        }
//
//        ((<undefinedtype>)(new Object() {
//            private int ticks = 0;
//            private float waitTicks;
//            private LevelAccessor world;
//
//            public void start(LevelAccessor levelaccessor1, int i) {
//                this.waitTicks = (float)i;
//                MinecraftForge.EVENT_BUS.register(this);
//                this.world = levelaccessor1;
//            }
//
//            @SubscribeEvent
//            public void tick(ServerTickEvent servertickevent) {
//                if (servertickevent.phase == Phase.END) {
//                    ++this.ticks;
//                    if ((float)this.ticks >= this.waitTicks) {
//                        this.run();
//                    }
//                }
//
//            }
//
//            private void run() {
//                LevelAccessor levelaccessor1 = this.world;
//                Level level1;
//
//                if (levelaccessor1 instanceof Level) {
//                    level1 = (Level)levelaccessor1;
//                    if (!level1.isClientSide()) {
//                        level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 2.0F);
//                    } else {
//                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 2.0F, false);
//                    }
//                }
//
//                levelaccessor1 = this.world;
//                if (levelaccessor1 instanceof Level) {
//                    level1 = (Level)levelaccessor1;
//                    if (!level1.isClientSide()) {
//                        level1.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 0.1F);
//                    } else {
//                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 0.1F, false);
//                    }
//                }
//
//                Entity entity = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player) -> {
//                    return true;
//                }).stream().sorted(((<undefinedtype>)(new Object() {
//                    Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
//                        return Comparator.comparingDouble((entity1) -> {
//                            return entity1.distanceToSqr(d3, d4, d5);
//                        });
//                    }
//                })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
//
//                if (entity instanceof Player) {
//                    Player player = (Player)entity;
//
//                    if (!player.level.isClientSide()) {
//                        player.displayClientMessage(new TextComponent("C4\u5373\u5c06\u5f00\u59cb\u7206\u70b8"), true);
//                    }
//                }
//
//                ((<undefinedtype>)(new Object() {
//                    private int ticks = 0;
//                    private float waitTicks;
//                    private LevelAccessor world;
//
//                    public void start(LevelAccessor levelaccessor2, int i) {
//                        this.waitTicks = (float)i;
//                        MinecraftForge.EVENT_BUS.register(this);
//                        this.world = levelaccessor2;
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
//                        Entity entity1 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(d0, d1, d2), 32.0D, 32.0D, 32.0D), (player1) -> {
//                            return true;
//                        }).stream().sorted(((<undefinedtype>)(new Object() {
//                            Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
//                                return Comparator.comparingDouble((entity2) -> {
//                                    return entity2.distanceToSqr(d3, d4, d5);
//                                });
//                            }
//                        })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null);
//
//                        if (entity1 instanceof Player) {
//                            Player player1 = (Player)entity1;
//
//                            if (!player1.level.isClientSide()) {
//                                player1.displayClientMessage(new TextComponent("C4\u5373\u5c06\u5f00\u59cb\u7206\u70b8"), true);
//                            }
//                        }
//
//                        LevelAccessor levelaccessor2 = this.world;
//
//                        if (levelaccessor2 instanceof Level) {
//                            Level level2 = (Level)levelaccessor2;
//
//                            if (!level2.isClientSide()) {
//                                level2.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                            } else {
//                                level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                            }
//                        }
//
//                        ((<undefinedtype>)(new Object() {
//                            private int ticks;
//                            private float waitTicks;
//                            private LevelAccessor world;
//
//                            {
//                                this.this$1 = 0;
//                                this.ticks = 0;
//                            }
//
//                            public void start(LevelAccessor levelaccessor3, int i) {
//                                this.waitTicks = (float)i;
//                                MinecraftForge.EVENT_BUS.register(this);
//                                this.world = levelaccessor3;
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
//                                LevelAccessor levelaccessor3 = this.world;
//
//                                if (levelaccessor3 instanceof Level) {
//                                    Level level3 = (Level)levelaccessor3;
//
//                                    if (!level3.isClientSide()) {
//                                        level3.playSound((Player)null, new BlockPos(this.this$1.this$0.val$x, this.this$1.this$0.val$y, this.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                    } else {
//                                        level3.playLocalSound(this.this$1.this$0.val$x, this.this$1.this$0.val$y, this.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                    }
//                                }
//
//                                ((<undefinedtype>)(new Object() {
//                                    private int ticks;
//                                    private float waitTicks;
//                                    private LevelAccessor world;
//
//                                    {
//                                        this.this$2 = 0;
//                                        this.ticks = 0;
//                                    }
//
//                                    public void start(LevelAccessor levelaccessor4, int i) {
//                                        this.waitTicks = (float)i;
//                                        MinecraftForge.EVENT_BUS.register(this);
//                                        this.world = levelaccessor4;
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
//                                        LevelAccessor levelaccessor4 = this.world;
//
//                                        if (levelaccessor4 instanceof Level) {
//                                            Level level4 = (Level)levelaccessor4;
//
//                                            if (!level4.isClientSide()) {
//                                                level4.playSound((Player)null, new BlockPos(this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                            } else {
//                                                level4.playLocalSound(this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                            }
//                                        }
//
//                                        ((<undefinedtype>)(new Object() {
//                                            private int ticks;
//                                            private float waitTicks;
//                                            private LevelAccessor world;
//
//                                            {
//                                                this.this$3 = 0;
//                                                this.ticks = 0;
//                                            }
//
//                                            public void start(LevelAccessor levelaccessor5, int i) {
//                                                this.waitTicks = (float)i;
//                                                MinecraftForge.EVENT_BUS.register(this);
//                                                this.world = levelaccessor5;
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
//                                                LevelAccessor levelaccessor5 = this.world;
//
//                                                if (levelaccessor5 instanceof Level) {
//                                                    Level level5 = (Level)levelaccessor5;
//
//                                                    if (!level5.isClientSide()) {
//                                                        level5.playSound((Player)null, new BlockPos(this.this$3.this$2.this$1.this$0.val$x, this.this$3.this$2.this$1.this$0.val$y, this.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                                    } else {
//                                                        level5.playLocalSound(this.this$3.this$2.this$1.this$0.val$x, this.this$3.this$2.this$1.this$0.val$y, this.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                                    }
//                                                }
//
//                                                ((<undefinedtype>)(new Object() {
//                                                    private int ticks;
//                                                    private float waitTicks;
//                                                    private LevelAccessor world;
//
//                                                    {
//                                                        this.this$4 = 0;
//                                                        this.ticks = 0;
//                                                    }
//
//                                                    public void start(LevelAccessor levelaccessor6, int i) {
//                                                        this.waitTicks = (float)i;
//                                                        MinecraftForge.EVENT_BUS.register(this);
//                                                        this.world = levelaccessor6;
//                                                    }
//
//                                                    @SubscribeEvent
//                                                    public void tick(ServerTickEvent servertickevent) {
//                                                        if (servertickevent.phase == Phase.END) {
//                                                            ++this.ticks;
//                                                            if ((float)this.ticks >= this.waitTicks) {
//                                                                this.run();
//                                                            }
//                                                        }
//
//                                                    }
//
//                                                    private void run() {
//                                                        LevelAccessor levelaccessor6 = this.world;
//
//                                                        if (levelaccessor6 instanceof Level) {
//                                                            Level level6 = (Level)levelaccessor6;
//
//                                                            if (!level6.isClientSide()) {
//                                                                level6.playSound((Player)null, new BlockPos(this.this$4.this$3.this$2.this$1.this$0.val$x, this.this$4.this$3.this$2.this$1.this$0.val$y, this.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                                            } else {
//                                                                level6.playLocalSound(this.this$4.this$3.this$2.this$1.this$0.val$x, this.this$4.this$3.this$2.this$1.this$0.val$y, this.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                                            }
//                                                        }
//
//                                                        ((<undefinedtype>)(new Object() {
//                                                            private int ticks;
//                                                            private float waitTicks;
//                                                            private LevelAccessor world;
//
//                                                            {
//                                                                this.this$5 = 0;
//                                                                this.ticks = 0;
//                                                            }
//
//                                                            public void start(LevelAccessor levelaccessor7, int i) {
//                                                                this.waitTicks = (float)i;
//                                                                MinecraftForge.EVENT_BUS.register(this);
//                                                                this.world = levelaccessor7;
//                                                            }
//
//                                                            @SubscribeEvent
//                                                            public void tick(ServerTickEvent servertickevent) {
//                                                                if (servertickevent.phase == Phase.END) {
//                                                                    ++this.ticks;
//                                                                    if ((float)this.ticks >= this.waitTicks) {
//                                                                        this.run();
//                                                                    }
//                                                                }
//
//                                                            }
//
//                                                            private void run() {
//                                                                LevelAccessor levelaccessor7 = this.world;
//
//                                                                if (levelaccessor7 instanceof Level) {
//                                                                    Level level7 = (Level)levelaccessor7;
//
//                                                                    if (!level7.isClientSide()) {
//                                                                        level7.playSound((Player)null, new BlockPos(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                                                    } else {
//                                                                        level7.playLocalSound(this.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                                                    }
//                                                                }
//
//                                                                ((<undefinedtype>)(new Object() {
//                                                                    private int ticks;
//                                                                    private float waitTicks;
//                                                                    private LevelAccessor world;
//
//                                                                    {
//                                                                        this.this$6 = 0;
//                                                                        this.ticks = 0;
//                                                                    }
//
//                                                                    public void start(LevelAccessor levelaccessor8, int i) {
//                                                                        this.waitTicks = (float)i;
//                                                                        MinecraftForge.EVENT_BUS.register(this);
//                                                                        this.world = levelaccessor8;
//                                                                    }
//
//                                                                    @SubscribeEvent
//                                                                    public void tick(ServerTickEvent servertickevent) {
//                                                                        if (servertickevent.phase == Phase.END) {
//                                                                            ++this.ticks;
//                                                                            if ((float)this.ticks >= this.waitTicks) {
//                                                                                this.run();
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    private void run() {
//                                                                        LevelAccessor levelaccessor8 = this.world;
//
//                                                                        if (levelaccessor8 instanceof Level) {
//                                                                            Level level8 = (Level)levelaccessor8;
//
//                                                                            if (!level8.isClientSide()) {
//                                                                                level8.playSound((Player)null, new BlockPos(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                                                            } else {
//                                                                                level8.playLocalSound(this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                                                            }
//                                                                        }
//
//                                                                        ((<undefinedtype>)(new Object() {
//                                                                            private int ticks;
//                                                                            private float waitTicks;
//                                                                            private LevelAccessor world;
//
//                                                                            {
//                                                                                this.this$7 = 0;
//                                                                                this.ticks = 0;
//                                                                            }
//
//                                                                            public void start(LevelAccessor levelaccessor9, int i) {
//                                                                                this.waitTicks = (float)i;
//                                                                                MinecraftForge.EVENT_BUS.register(this);
//                                                                                this.world = levelaccessor9;
//                                                                            }
//
//                                                                            @SubscribeEvent
//                                                                            public void tick(ServerTickEvent servertickevent) {
//                                                                                if (servertickevent.phase == Phase.END) {
//                                                                                    ++this.ticks;
//                                                                                    if ((float)this.ticks >= this.waitTicks) {
//                                                                                        this.run();
//                                                                                    }
//                                                                                }
//
//                                                                            }
//
//                                                                            private void run() {
//                                                                                LevelAccessor levelaccessor9 = this.world;
//
//                                                                                if (levelaccessor9 instanceof Level) {
//                                                                                    Level level9 = (Level)levelaccessor9;
//
//                                                                                    if (!level9.isClientSide()) {
//                                                                                        level9.playSound((Player)null, new BlockPos(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                                                                    } else {
//                                                                                        level9.playLocalSound(this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                                                                    }
//                                                                                }
//
//                                                                                ((<undefinedtype>)(new Object() {
//                                                                                    private int ticks;
//                                                                                    private float waitTicks;
//                                                                                    private LevelAccessor world;
//
//                                                                                    {
//                                                                                        this.this$8 = 0;
//                                                                                        this.ticks = 0;
//                                                                                    }
//
//                                                                                    public void start(LevelAccessor levelaccessor10, int i) {
//                                                                                        this.waitTicks = (float)i;
//                                                                                        MinecraftForge.EVENT_BUS.register(this);
//                                                                                        this.world = levelaccessor10;
//                                                                                    }
//
//                                                                                    @SubscribeEvent
//                                                                                    public void tick(ServerTickEvent servertickevent) {
//                                                                                        if (servertickevent.phase == Phase.END) {
//                                                                                            ++this.ticks;
//                                                                                            if ((float)this.ticks >= this.waitTicks) {
//                                                                                                this.run();
//                                                                                            }
//                                                                                        }
//
//                                                                                    }
//
//                                                                                    private void run() {
//                                                                                        LevelAccessor levelaccessor10 = this.world;
//
//                                                                                        if (levelaccessor10 instanceof Level) {
//                                                                                            Level level10 = (Level)levelaccessor10;
//
//                                                                                            if (!level10.isClientSide()) {
//                                                                                                level10.playSound((Player)null, new BlockPos(this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                                                                                            } else {
//                                                                                                level10.playLocalSound(this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                                                                                            }
//                                                                                        }
//
//                                                                                        ((<undefinedtype>)(new Object() {
//                                                                                            private int ticks;
//                                                                                            private float waitTicks;
//                                                                                            private LevelAccessor world;
//
//                                                                                            {
//                                                                                                this.this$9 = 0;
//                                                                                                this.ticks = 0;
//                                                                                            }
//
//                                                                                            public void start(LevelAccessor levelaccessor11, int i) {
//                                                                                                this.waitTicks = (float)i;
//                                                                                                MinecraftForge.EVENT_BUS.register(this);
//                                                                                                this.world = levelaccessor11;
//                                                                                            }
//
//                                                                                            @SubscribeEvent
//                                                                                            public void tick(ServerTickEvent servertickevent) {
//                                                                                                if (servertickevent.phase == Phase.END) {
//                                                                                                    ++this.ticks;
//                                                                                                    if ((float)this.ticks >= this.waitTicks) {
//                                                                                                        this.run();
//                                                                                                    }
//                                                                                                }
//
//                                                                                            }
//
//                                                                                            private void run() {
//                                                                                                LevelAccessor levelaccessor11;
//                                                                                                Level level11;
//
//                                                                                                if (this.world.getBlockState(new BlockPos(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z)).getBlock() == AnnoyingVillagersModBlocks.C_4.get()) {
//                                                                                                    this.world.setBlock(new BlockPos(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), Blocks.AIR.defaultBlockState(), 3);
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.playSound((Player)null, new BlockPos(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 1.0F);
//                                                                                                        } else {
//                                                                                                            level11.playLocalSound(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.explode((Entity)null, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, 25.0F, BlockInteraction.DESTROY);
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.explode((Entity)null, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, 50.0F, BlockInteraction.NONE);
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.explode((Entity)null, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, 50.0F, BlockInteraction.NONE);
//                                                                                                        }
//                                                                                                    }
//                                                                                                } else {
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.playSound((Player)null, new BlockPos(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 0.2F);
//                                                                                                        } else {
//                                                                                                            level11.playLocalSound(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 0.2F, false);
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.playSound((Player)null, new BlockPos(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 3.0F);
//                                                                                                        } else {
//                                                                                                            level11.playLocalSound(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 3.0F, false);
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    levelaccessor11 = this.world;
//                                                                                                    if (levelaccessor11 instanceof Level) {
//                                                                                                        level11 = (Level)levelaccessor11;
//                                                                                                        if (!level11.isClientSide()) {
//                                                                                                            level11.playSound((Player)null, new BlockPos(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.use")), SoundSource.NEUTRAL, 1.0F, 0.2F);
//                                                                                                        } else {
//                                                                                                            level11.playLocalSound(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.use")), SoundSource.NEUTRAL, 1.0F, 0.2F, false);
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    Entity entity2 = (Entity)this.world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z), 32.0D, 32.0D, 32.0D), (player2) -> {
//                                                                                                        return true;
//                                                                                                    }).stream().sorted(((<undefinedtype>)(new Object() {
//                                                                                                        {
//                                                                                                            this.this$10 = 0;
//                                                                                                        }
//
//                                                                                                        Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
//                                                                                                            return Comparator.comparingDouble((entity3) -> {
//                                                                                                                return entity3.distanceToSqr(d6, d7, d8);
//                                                                                                            });
//                                                                                                        }
//                                                                                                    })).compareDistOf(this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$x, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$y, this.this$9.this$8.this$7.this$6.this$5.this$4.this$3.this$2.this$1.this$0.val$z)).findFirst().orElse((Object)null);
//
//                                                                                                    if (entity2 instanceof Player) {
//                                                                                                        Player player2 = (Player)entity2;
//
//                                                                                                        if (!player2.level.isClientSide()) {
//                                                                                                            player2.displayClientMessage(new TextComponent("C4\u88ab\u7834\u574f\uff0c\u7206\u70b8\u5931\u8d25"), true);
//                                                                                                        }
//                                                                                                    }
//                                                                                                }
//
//                                                                                                MinecraftForge.EVENT_BUS.unregister(this);
//                                                                                            }
//                                                                                        })).start(this.world, 1);
//                                                                                        MinecraftForge.EVENT_BUS.unregister(this);
//                                                                                    }
//                                                                                })).start(this.world, 2);
//                                                                                MinecraftForge.EVENT_BUS.unregister(this);
//                                                                            }
//                                                                        })).start(this.world, 3);
//                                                                        MinecraftForge.EVENT_BUS.unregister(this);
//                                                                    }
//                                                                })).start(this.world, 4);
//                                                                MinecraftForge.EVENT_BUS.unregister(this);
//                                                            }
//                                                        })).start(this.world, 5);
//                                                        MinecraftForge.EVENT_BUS.unregister(this);
//                                                    }
//                                                })).start(this.world, 6);
//                                                MinecraftForge.EVENT_BUS.unregister(this);
//                                            }
//                                        })).start(this.world, 7);
//                                        MinecraftForge.EVENT_BUS.unregister(this);
//                                    }
//                                })).start(this.world, 8);
//                                MinecraftForge.EVENT_BUS.unregister(this);
//                            }
//                        })).start(this.world, 9);
//                        MinecraftForge.EVENT_BUS.unregister(this);
//                    }
//                })).start(this.world, 10);
//                MinecraftForge.EVENT_BUS.unregister(this);
//            }
//        })).start(levelaccessor, 820);
//        if (levelaccessor instanceof Level) {
//            level = (Level)levelaccessor;
//            if (!level.isClientSide()) {
//                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":c4planted")), SoundSource.NEUTRAL, 1.0F, 1.0F);
//            } else {
//                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":c4planted")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
//            }
//        }
//
//    }
//}
