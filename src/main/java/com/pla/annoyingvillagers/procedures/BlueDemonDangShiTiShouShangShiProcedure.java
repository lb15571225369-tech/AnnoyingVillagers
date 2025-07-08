package com.pla.annoyingvillagers.procedures;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class BlueDemonDangShiTiShouShangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 11, 0, false, false));
                    }
                }

                LivingEntity livingentity1;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity1 = mob.getTarget();
                } else {
                    livingentity1 = null;
                }

                if (livingentity1 == entity1) {
                    if (Math.random() <= 0.2D && !entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s annoying_villagersbychentu:gedang 1 0 true");
                    }

                    if (Math.random() <= 0.01D) {
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
                                if (!this.world.isClientSide() && this.world.getServer() != null) {
                                    this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u4f60\u4e5f\u592a\u597d\u9884\u6d4b\u4e86"), ChatType.SYSTEM, Util.NIL_UUID);
                                }

                                LevelAccessor levelaccessor1 = this.world;

                                if (levelaccessor1 instanceof Level) {
                                    Level level = (Level)levelaccessor1;

                                    if (!level.isClientSide()) {
                                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsayyc")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    } else {
                                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemonsayyc")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                    }
                                }

                                LivingEntity livingentity2;

                                if (entity1 instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity)entity1;
                                    if (!livingentity2.level.isClientSide()) {
                                        livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 3, 0, false, false));
                                    }
                                }

                                Entity entity2 = entity;

                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                                }

                                if (entity1 instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity)entity1;
                                    if (!livingentity2.level.isClientSide()) {
                                        livingentity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 3, false, false));
                                    }
                                }

                                if (entity1 instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity)entity1;
                                    if (!livingentity2.level.isClientSide()) {
                                        livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2, false, false));
                                    }
                                }

                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 20);
                    } else if (Math.random() <= 0.01D) {
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
                                if (entity1 instanceof LivingEntity) {
                                    LivingEntity livingentity2 = (LivingEntity)entity1;

                                    if (!livingentity2.level.isClientSide()) {
                                        livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 10, 0, false, false));
                                    }
                                }

                                Entity entity2 = entity;

                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                                }

                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 20);
                    } else if (Math.random() <= 0.01D) {
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
                                if (!this.world.isClientSide() && this.world.getServer() != null) {
                                    this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u4e0d\u8981\u81ea\u5927"), ChatType.SYSTEM, Util.NIL_UUID);
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
                                        LivingEntity livingentity2;

                                        if (entity1 instanceof LivingEntity) {
                                            livingentity2 = (LivingEntity)entity1;
                                            if (!livingentity2.level.isClientSide()) {
                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 40, 0, false, false));
                                            }
                                        }

                                        Entity entity2 = entity;

                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                                        }

                                        if (entity1 instanceof LivingEntity) {
                                            livingentity2 = (LivingEntity)entity1;
                                            if (!livingentity2.level.isClientSide()) {
                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2, false, false));
                                            }
                                        }

                                        MinecraftForge.EVENT_BUS.unregister(this);
                                    }
                                })).start(this.world, 20);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 20);
                    } else if (Math.random() <= 0.01D) {
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
                                        if (!this.world.isClientSide() && this.world.getServer() != null) {
                                            this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u4e00\u5473\u5730\u5c0f\u770b\u6211\u4eec\u8fd9\u4e9b\u4ea1\u7075\u751f\u7269\u53ea\u4f1a\u7a81\u51fa\u4f60\u5230\u5e95\u662f\u6709\u591a\u4e48\u7684\u65e0\u77e5"), ChatType.SYSTEM, Util.NIL_UUID);
                                        }

                                        LevelAccessor levelaccessor1 = this.world;

                                        if (levelaccessor1 instanceof Level) {
                                            Level level = (Level)levelaccessor1;

                                            if (!level.isClientSide()) {
                                                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemon_say_you_no_know")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                            } else {
                                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemon_say_you_no_know")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                            }
                                        }

                                        if (entity1 instanceof LivingEntity) {
                                            LivingEntity livingentity2 = (LivingEntity)entity1;

                                            if (!livingentity2.level.isClientSide()) {
                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 10, 0, false, false));
                                            }
                                        }

                                        Entity entity2 = entity;

                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                                        }

                                        entity2 = entity1;
                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect clear @s annoying_villagersbychentu:gedang");
                                        }

                                        MinecraftForge.EVENT_BUS.unregister(this);
                                    }
                                })).start(this.world, 20);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 20);
                    } else if (Math.random() <= 0.01D) {
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
                                        if (!this.world.isClientSide() && this.world.getServer() != null) {
                                            this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("<\u84dd\u6076\u9b54> \u771f\u6709\u610f\u601d\uff0c\u4e0d\u8fc7\u6211\u5f88\u60f3\u77e5\u9053\u4f60\u7684\u52a8\u673a\u662f\u4ec0\u4e48"), ChatType.SYSTEM, Util.NIL_UUID);
                                        }

                                        LevelAccessor levelaccessor1 = this.world;
                                        Level level;

                                        if (levelaccessor1 instanceof Level) {
                                            level = (Level)levelaccessor1;
                                            if (!level.isClientSide()) {
                                                level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemon_say_player_interesting")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                            } else {
                                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoying_villagers:bluedemon_say_player_interesting")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                            }
                                        }

                                        Entity entity2 = entity;

                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute at @s run particle annoying_villagersbychentu:dianhu_2 ^ ^ ^ 5 1.5 5 0 10");
                                        }

                                        LivingEntity livingentity2;

                                        if (entity1 instanceof LivingEntity) {
                                            livingentity2 = (LivingEntity)entity1;
                                            if (!livingentity2.level.isClientSide()) {
                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 5, 0, false, false));
                                            }
                                        }

                                        if (entity1 instanceof LivingEntity) {
                                            livingentity2 = (LivingEntity)entity1;
                                            if (!livingentity2.level.isClientSide()) {
                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2, false, false));
                                            }
                                        }

                                        levelaccessor1 = this.world;
                                        if (levelaccessor1 instanceof Level) {
                                            level = (Level)levelaccessor1;
                                            if (!level.isClientSide()) {
                                                level.explode((Entity)null, entity1.getX(), entity1.getY(), entity1.getZ(), 2.0F, BlockInteraction.DESTROY);
                                            }
                                        }

                                        MinecraftForge.EVENT_BUS.unregister(this);
                                    }
                                })).start(this.world, 20);
                                MinecraftForge.EVENT_BUS.unregister(this);
                            }
                        })).start(levelaccessor, 30);
                    }
                }
            }

        }
    }
}
