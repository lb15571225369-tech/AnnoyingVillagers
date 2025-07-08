package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class Bluedemon2SkillEffectProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;

            if (entity instanceof Mob) {
                Mob mob = (Mob)entity;

                livingentity = mob.getTarget();
            } else {
                livingentity = null;
            }

            LivingEntity livingentity1 = livingentity;

            if (livingentity1 instanceof LivingEntity) {
                LivingEntity livingentity2 = (LivingEntity)livingentity1;

                if (!livingentity2.level.isClientSide()) {
                    livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 1, 0, false, false));
                }
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
                    LivingEntity livingentity3;

                    if (entity instanceof Mob) {
                        Mob mob1 = (Mob)entity;

                        livingentity3 = mob1.getTarget();
                    } else {
                        livingentity3 = null;
                    }

                    LivingEntity livingentity4 = livingentity3;

                    if (livingentity4 instanceof LivingEntity) {
                        LivingEntity livingentity5 = (LivingEntity)livingentity4;

                        if (!livingentity5.level.isClientSide()) {
                            livingentity5.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 1, 0, false, false));
                        }
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
                            LivingEntity livingentity6;

                            if (entity instanceof Mob) {
                                Mob mob2 = (Mob)entity;

                                livingentity6 = mob2.getTarget();
                            } else {
                                livingentity6 = null;
                            }

                            LivingEntity livingentity7 = livingentity6;

                            if (livingentity7 instanceof LivingEntity) {
                                LivingEntity livingentity8 = (LivingEntity)livingentity7;

                                if (!livingentity8.level.isClientSide()) {
                                    livingentity8.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 1, 0, false, false));
                                }
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
                                    LivingEntity livingentity9;

                                    if (this.this$1.this$0.val$entity instanceof Mob) {
                                        Mob mob3 = (Mob)this.this$1.this$0.val$entity;

                                        livingentity9 = mob3.getTarget();
                                    } else {
                                        livingentity9 = null;
                                    }

                                    LivingEntity livingentity10 = livingentity9;

                                    if (livingentity10 instanceof LivingEntity) {
                                        LivingEntity livingentity11 = (LivingEntity)livingentity10;

                                        if (!livingentity11.level.isClientSide()) {
                                            livingentity11.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 1, 0, false, false));
                                        }
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
                                            LivingEntity livingentity12;

                                            if (this.this$2.this$1.this$0.val$entity instanceof Mob) {
                                                Mob mob4 = (Mob)this.this$2.this$1.this$0.val$entity;

                                                livingentity12 = mob4.getTarget();
                                            } else {
                                                livingentity12 = null;
                                            }

                                            LivingEntity livingentity13 = livingentity12;

                                            if (livingentity13 instanceof LivingEntity) {
                                                LivingEntity livingentity14 = (LivingEntity)livingentity13;

                                                if (!livingentity14.level.isClientSide()) {
                                                    livingentity14.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 20, 0, false, false));
                                                }
                                            }

                                            LevelAccessor levelaccessor1 = this.world;
                                            Level level;

                                            if (levelaccessor1 instanceof Level) {
                                                level = (Level)levelaccessor1;
                                                if (!level.isClientSide()) {
                                                    level.explode((Entity)null, this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z, 2.0F, BlockInteraction.NONE);
                                                }
                                            }

                                            levelaccessor1 = this.world;
                                            if (levelaccessor1 instanceof Level) {
                                                level = (Level)levelaccessor1;
                                                if (!level.isClientSide()) {
                                                    level.explode((Entity)null, this.this$2.this$1.this$0.val$x, this.this$2.this$1.this$0.val$y, this.this$2.this$1.this$0.val$z, 2.0F, BlockInteraction.DESTROY);
                                                }
                                            }

                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(this.world, 5);
                                    MinecraftForge.EVENT_BUS.unregister(this);
                                }
                            })).start(this.world, 4);
                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(this.world, 3);
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            })).start(levelaccessor, 2);
        }
    }
}
