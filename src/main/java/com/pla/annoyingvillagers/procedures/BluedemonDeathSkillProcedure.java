package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class BluedemonDeathSkillProcedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity().level, livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("annoying_villagers:blue_demon")) {
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
                        Player player;

                        if (((<undefinedtype>)(new Object() {
                            public boolean checkGamemode(Entity entity2) {
                                if (entity2 instanceof ServerPlayer) {
                                    ServerPlayer serverplayer = (ServerPlayer)entity2;

                                    return serverplayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                                } else if (entity2.level.isClientSide() && entity2 instanceof Player) {
                                    Player player1 = (Player)entity2;

                                    return Minecraft.getInstance().getConnection().getPlayerInfo(player1.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player1.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                                } else {
                                    return false;
                                }
                            }
                        })).checkGamemode(entity1)) {
                            if (entity instanceof Player) {
                                player = (Player)entity;
                                if (!player.level.isClientSide()) {
                                    player.displayClientMessage(new TextComponent("\u65e0\u76ee\u6807"), true);
                                }
                            }
                        } else if (((<undefinedtype>)(new Object() {
                            public boolean checkGamemode(Entity entity2) {
                                if (entity2 instanceof ServerPlayer) {
                                    ServerPlayer serverplayer = (ServerPlayer)entity2;

                                    return serverplayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
                                } else if (entity2.level.isClientSide() && entity2 instanceof Player) {
                                    Player player1 = (Player)entity2;

                                    return Minecraft.getInstance().getConnection().getPlayerInfo(player1.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player1.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
                                } else {
                                    return false;
                                }
                            }
                        })).checkGamemode(entity1)) {
                            if (entity instanceof Player) {
                                player = (Player)entity;
                                if (!player.level.isClientSide()) {
                                    player.displayClientMessage(new TextComponent("\u65e0\u76ee\u6807"), true);
                                }
                            }
                        } else {
                            if (entity1 instanceof Player) {
                                player = (Player)entity1;
                                player.causeFoodExhaustion(6.0F);
                            }

                            LevelAccessor levelaccessor1 = this.world;
                            Level level;

                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    level.explode((Entity)null, entity1.getX(), entity1.getY(), entity1.getZ(), 10.0F, BlockInteraction.NONE);
                                }
                            }

                            LivingEntity livingentity;

                            if (entity1 instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity1;
                                if (!livingentity.level.isClientSide()) {
                                    livingentity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 2, false, false));
                                }
                            }

                            if (entity1 instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity1;
                                if (!livingentity.level.isClientSide()) {
                                    livingentity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 70, 2, false, false));
                                }
                            }

                            if (entity1 instanceof LivingEntity) {
                                livingentity = (LivingEntity)entity1;
                                if (!livingentity.level.isClientSide()) {
                                    livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 70, 2, false, false));
                                }
                            }

                            levelaccessor1 = this.world;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    level.explode((Entity)null, entity1.getX(), entity1.getY(), entity1.getZ(), 8.0F, BlockInteraction.DESTROY);
                                }
                            }

                            levelaccessor1 = this.world;
                            ServerLevel serverlevel;
                            LightningBolt lightningbolt;

                            if (levelaccessor1 instanceof ServerLevel) {
                                serverlevel = (ServerLevel)levelaccessor1;
                                lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel);
                                lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(entity1.getX(), entity1.getY(), entity1.getZ())));
                                lightningbolt.setVisualOnly(false);
                                serverlevel.addFreshEntity(lightningbolt);
                            }

                            levelaccessor1 = this.world;
                            if (levelaccessor1 instanceof ServerLevel) {
                                serverlevel = (ServerLevel)levelaccessor1;
                                lightningbolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(serverlevel);
                                lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(entity1.getX(), entity1.getY(), entity1.getZ())));
                                lightningbolt.setVisualOnly(false);
                                serverlevel.addFreshEntity(lightningbolt);
                            }

                            levelaccessor1 = this.world;
                            if (levelaccessor1 instanceof ServerLevel) {
                                serverlevel = (ServerLevel)levelaccessor1;
                                ThrownTrident throwntrident = new ThrownTrident(EntityType.TRIDENT, serverlevel);

                                throwntrident.moveTo(entity1.getX(), entity1.getY() + 5.0D, entity1.getZ(), this.world.getRandom().nextFloat() * 360.0F, 0.0F);
                                if (throwntrident instanceof Mob) {
                                    Mob mob = (Mob)throwntrident;

                                    mob.finalizeSpawn(serverlevel, this.world.getCurrentDifficultyAt(throwntrident.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                                }

                                this.world.addFreshEntity(throwntrident);
                            }
                        }

                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                })).start(levelaccessor, 90);
            }

        }
    }
}
