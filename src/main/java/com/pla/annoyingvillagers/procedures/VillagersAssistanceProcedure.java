package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class VillagersAssistanceProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity().level, livinghurtevent.getEntity().getX(), livinghurtevent.getEntity().getY(), livinghurtevent.getEntity().getZ(), livinghurtevent.getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:villager") && levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)levelaccessor;

                if (serverlevel.isRaided(new BlockPos(d0, d1, d2)) && Math.random() <= 0.2D) {
                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                        levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager> Help !"), ChatType.SYSTEM, Util.NIL_UUID);
                    }

                    new DelayedTask(11) {
                        private void summonFirework(Entity entity) {
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                        "/summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}"
                                );
                            }

                            playSound(levelaccessor, d0, d1, d2, "entity.firework_rocket.launch");
                        }

                        private void playSound(LevelAccessor level, double x, double y, double z, String soundId) {
                            if (level instanceof Level lvl) {
                                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundId));
                                if (!lvl.isClientSide()) {
                                    lvl.playSound(null, new BlockPos(x, y, z), sound, SoundSource.NEUTRAL, 1.0F, 2.0F);
                                } else {
                                    lvl.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1.0F, 2.0F, false);
                                }
                            }
                        }

                        private void broadcast(String speaker, String message) {
                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                levelaccessor.getServer().getPlayerList().broadcastMessage(
                                        new TextComponent("<" + speaker + "> " + message),
                                        ChatType.SYSTEM,
                                        Util.NIL_UUID
                                );
                            }
                        }

                        private void summon(Entity entity, String type, double dx, double dy, double dz) {
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                entity.getServer().getCommands().performCommand(
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                        String.format("/summon annoyingvillagers:%s ~%.1f ~%.1f ~%.1f", type, dx, dy, dz)
                                );
                            }
                        }
                        @Override
                        public void run() {
                            if (entity instanceof LivingEntity living) {
                                if (living.hasEffect(MobEffects.MOVEMENT_SPEED)) {
                                    broadcast("Villager", "Help me!");
                                    return;
                                }
                            }

                            if (entity instanceof LivingEntity living && !living.level.isClientSide()) {
                                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
                            }

                            if (entity.isAlive() && levelaccessor instanceof ServerLevel serverLevel) {
                                serverLevel.sendParticles(ParticleTypes.FIREWORK, d0, d1, d2, 40, 0.0D, 3.0D, 0.0D, 1.0D);

                                summonFirework(entity);

                                new DelayedTask(50) {
                                    @Override
                                    public void run() {
                                        playSound(levelaccessor, d0, d1, d2, "entity.experience_orb.pickup");

                                        if (Math.random() <= 0.6) {
                                            broadcast("Villager Scout", "What the matter?");
                                            summon(entity, "cun_min_zhen_cha_bing", 0, 5, 10);
                                            summon(entity, "cun_min_zhen_cha_bing", 10, 5, -5);
                                            summon(entity, "lan_cun_qi", -10, 5, 20);
                                        } else if (Math.random() <= 0.1) {
                                            broadcast("Villager Blue General", "What the matter?");
                                            summon(entity, "lan_cun_qi", 10, 5, -20);
                                            summon(entity, "lan_cun_qi", -5, 5, 20);
                                            summon(entity, "cun_min_zhen_cha_bing", 0, 5, 10);
                                        } else if (Math.random() <= 0.1) {
                                            broadcast("Villager Purple General", "What the matter?");
                                            summon(entity, "zi_cun_qi", -5, 5, 20);
                                            summon(entity, "zi_cun_qi", 10, 5, -20);
                                        } else if (Math.random() <= 0.1) {
                                            broadcast("Villager Red General", "What the matter?");
                                            summon(entity, "hong_cun_qi", 10, 5, 20);
                                            summon(entity, "hong_cun_qi", 5, 5, -20);
                                            summon(entity, "cun_min_zhen_cha_bing", 0, 5, -10);
                                        } else {
                                            broadcast("Villager Green General", "What the matter?");
                                            summon(entity, "cun_min_zhen_cha_bing", 0, 5, -10);
                                            summon(entity, "lu_cun_qi", 10, 5, 20);
                                            summon(entity, "lu_cun_qi", -5, 5, 20);
                                        }
                                    }
                                };
                            }
                        }
                    };
                }
            }

        }
    }
}
