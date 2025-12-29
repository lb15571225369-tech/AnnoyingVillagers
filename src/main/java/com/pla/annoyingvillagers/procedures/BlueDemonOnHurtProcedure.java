package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class BlueDemonOnHurtProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity entity1) {
        if (entity == null || entity1 == null || world == null) return;

        if (entity instanceof Mob mob && mob.getTarget() == entity1) {
            if (Math.random() <= 0.01) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        broadcast(world, "<" + entity.getDisplayName().getString() + "> You're way too predictable.");
                        playSound(world, x, y, z, AnnoyingVillagers.MODID + ":bluedemonsayyc");
                        applyEffect(entity1, AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 3);
                        applyEffect(entity1, MobEffects.BLINDNESS, 80, 3);
                        applyEffect(entity1, MobEffects.MOVEMENT_SLOWDOWN, 80, 2);
                        runParticle(entity);
                    }
                };
            } else if (Math.random() <= 0.01) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        applyEffect(entity1, AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 10);
                        runParticle(entity);
                    }
                };
            } else if (Math.random() <= 0.01) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        broadcast(world, "<" + entity.getDisplayName().getString() + "> Don't be arrogant.");

                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                applyEffect(entity1, AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 40);
                                applyEffect(entity1, MobEffects.MOVEMENT_SLOWDOWN, 80, 2);
                                runParticle(entity);
                            }
                        };
                    }
                };
            } else if (Math.random() <= 0.01) {
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                broadcast(world, "<" + entity.getDisplayName().getString() + "> Looking down on us undead creatures only highlights how ignorant you are.");
                                playSound(world, x, y, z, AnnoyingVillagers.MODID + ":bluedemon_say_you_no_know");
                                applyEffect(entity1, AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 10);
                                runParticle(entity);
                            }
                        };
                    }
                };
            } else if (Math.random() <= 0.01) {
                new DelayedTask(30) {
                    @Override
                    public void run() {
                        new DelayedTask(20) {
                            @Override
                            public void run() {
                                broadcast(world, "<" + entity.getDisplayName().getString() + "> How interesting. But I really want to know what is your motive?");
                                playSound(world, x, y, z, AnnoyingVillagers.MODID + ":bluedemon_say_player_interesting");
                                runParticle(entity);
                                applyEffect(entity1, AnnoyingVillagersModMobEffects.BLUE_DEMON_SKILL_LIGHTING_EFFECT.get(), 5);
                                applyEffect(entity1, MobEffects.MOVEMENT_SLOWDOWN, 80, 2);

                                if (world instanceof Level level && !level.isClientSide()) {
                                    level.explode(null, entity1.getX(), entity1.getY(), entity1.getZ(), 2.0F, Level.ExplosionInteraction.BLOCK);
                                }
                            }
                        };
                    }
                };
            }
        }
    }

    private static void applyEffect(Entity entity, net.minecraft.world.effect.MobEffect effect, int duration) {
        if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
            living.addEffect(new MobEffectInstance(effect, duration, 0, false, false));
        }
    }

    private static void applyEffect(Entity entity, net.minecraft.world.effect.MobEffect effect, int duration, int amplifier) {
        if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
            living.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
        }
    }

    private static void broadcast(LevelAccessor world, String message) {
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(message), false);
        }
    }

    private static void playSound(LevelAccessor world, double x, double y, double z, String soundName) {
        if (world instanceof Level level) {
            String[] parts = soundName.split(":", 2);
            String namespace = parts[0];
            String path = parts[1];
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(namespace, path));
            if (!level.isClientSide()) {
                level.playSound(null, new BlockPos((int) x, (int) y, (int) z), sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }
    }

    private static void runParticle(Entity entity) {
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            try {
                entity.getServer().getCommands().getDispatcher().execute(
                        "execute at @s run particle annoyingvillagers:electric_spark_2 ^ ^ ^ 5 1.5 5 0 10",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {
            }
        }
    }

    private static void runCommand(Entity entity, String command) {
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            try {
                entity.getServer().getCommands().getDispatcher().execute(
                        command,
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {
            }
        }
    }
}
