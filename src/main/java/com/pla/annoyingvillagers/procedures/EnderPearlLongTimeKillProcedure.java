package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class EnderPearlLongTimeKillProcedure {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent entityjoinworldevent) {
        execute(entityjoinworldevent, entityjoinworldevent.getWorld(), entityjoinworldevent.getEntity());
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            double d0;

            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile)entity;

                d0 = projectile.getDeltaMovement().length();
            } else {
                d0 = 0.0D;
            }

            if (d0 != 0.0D) {
                if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("annoying_villagersbychentu:projectile_fumomoyingzhenzhu")) {
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
                            if (entity.isAlive() && !entity.level.isClientSide()) {
                                entity.discard();
                            }

                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(levelaccessor, 200);
                } else if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:ender_pearl")) {
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
                            if (entity.isAlive() && !entity.level.isClientSide()) {
                                entity.discard();
                            }

                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    })).start(levelaccessor, 200);
                }
            }

        }
    }
}
