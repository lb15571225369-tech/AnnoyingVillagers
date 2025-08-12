package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.util.RandomSource;
@EventBusSubscriber
public class GuardBreakProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level(), livingattackevent.getEntity().getX(), livingattackevent.getEntity().getY(), livingattackevent.getEntity().getZ(), livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity, entity1);
    }

    private static boolean isCreativeMode(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
        } else if (entity instanceof Player player && player.level().isClientSide()) {
            var info = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            return info != null && info.getGameMode() == GameType.CREATIVE;
        }
        return false;
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:player")) {
                float f;
                LivingEntity livingentity1;

                if (ForgeRegistries.ENTITY_TYPES.getKey(entity1.getType()).toString().equals(AnnoyingVillagers.MODID + ":herobrine")) {
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        f = livingentity.getHealth();
                    } else {
                        f = -1.0F;
                    }
                } else if (ForgeRegistries.ENTITY_TYPES.getKey(entity1.getType()).toString().equals(AnnoyingVillagers.MODID + ":herobrine_2")) {
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        f = livingentity.getHealth();
                    } else {
                        f = -1.0F;
                    }
                }
            }

            if (ForgeRegistries.ENTITY_TYPES.getKey(entity1.getType()).toString().equals("minecraft:player")) {
                LivingEntity livingentity2;

                if (entity instanceof TamableAnimal) {
                    TamableAnimal tamableanimal = (TamableAnimal)entity;

                    livingentity2 = tamableanimal.getOwner();
                } else {
                    livingentity2 = null;
                }

                if (livingentity2 == entity1) {
                    entity.clearFire();
                } else if (!isCreativeMode(entity1) && entity instanceof Mob) {
                    Mob mob = (Mob)entity;
                    if (entity1 instanceof LivingEntity livingTarget) {
                        mob.setTarget(livingTarget);
                    }
                }
            }

            if (entity.getPersistentData().getBoolean("s_g")) {
                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }

                entity.getPersistentData().putBoolean("s_g", false);
                if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                    try {
                        entity1.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 1", entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                        entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/tachi_auto2\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                        entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                        entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
                }

                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "s_g")), SoundSource.NEUTRAL, 2.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "s_g")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "s_g_hit")), SoundSource.NEUTRAL, 3.0F, (float)Mth.nextDouble(RandomSource.create(), 0.7D, 1.2D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "s_g_hit")), SoundSource.NEUTRAL, 3.0F, (float)Mth.nextDouble(RandomSource.create(), 0.7D, 1.2D), false);
                    }
                }

                entity.getPersistentData().putBoolean("s_g_suss", true);
                new DelayedTask(40) {
                    @Override
                    public void run() {
                        entity.getPersistentData().putBoolean("s_g_suss", false);
                    }
                };
            }

            if (entity1.getPersistentData().getBoolean("s_g_suss")) {
                entity.getPersistentData().putBoolean("s_g_suss", false);
                boolean flag;

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    flag = livingentity.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                if (!flag) {
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (entity1 instanceof LivingEntity) {
                                LivingEntity livingentity4 = (LivingEntity)entity1;

                                if (!livingentity4.level().isClientSide()) {
                                    livingentity4.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2, false, false));
                                }
                            }

                            Entity entity2 = entity1;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/skill/grasping_spire_second\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                    
                                }
                            }
                        }
                    };
                }
            }

        }
    }
}
