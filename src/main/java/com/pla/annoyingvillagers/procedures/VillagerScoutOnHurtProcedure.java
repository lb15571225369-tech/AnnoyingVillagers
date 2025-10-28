package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class VillagerScoutOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final PathfinderMobInventory entity, Entity attacker, double amount) {
        if (entity != null && attacker != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (entity.getEnderPearlCooldown() == 0) {
                    CombatBehaviour.throwEnderPearl(entity, (float) new Random().nextDouble(90.0D, 180.0D));

                    entity.setSprinting(true);
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setSprinting(false);
                            }
                        }
                    };

                    if (Math.random() <= 0.22D) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (!livingEntity.level().isClientSide()) {
                            livingEntity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                        }
                    }

                    if (Math.random() <= 0.5D) {
                        if (entity.isAlive()) {
                            CombatBehaviour.throwEnderPearl(entity, 180.0F);
                        }
                    }

                    entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.IRON_SWORD));
                    new DelayedTask(120) {
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.ENDER_PEARL));
                            }
                        }
                    };

                    if (Math.random() <= 0.3D) {
                        new DelayedTask(20) {
                            public void run() {
                                if (entity.isAlive()) {
                                    CombatBehaviour.throwEnderPearl(entity, 90.0F);
                                }
                            }
                        };
                    }

                    if (Math.random() <= 0.3D) {
                        new DelayedTask(20) {
                            public void run() {
                                if (entity.isAlive()) {
                                    CombatBehaviour.throwEnderPearl(entity, 180.0F);
                                    new DelayedTask(20) {
                                        @Override
                                        public void run() {
                                            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Fire!"), false);
                                            }
                                        }
                                    };
                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                    if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 3);
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 3);
                                    }
                                    new DelayedTask(80) {
                                        @Override
                                        public void run() {
                                            if (Math.random() <= 0.2D) {
                                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                                if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                                }
                                            } else {
                                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
                                                if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 3);
                                                }
                                            }
                                        }
                                    };
                                }
                            }
                        };
                    }
                    if (entity.getHealth() <= 7.0F && levelaccessor instanceof Level) {
                        Level level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.glass.break"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.glass.break"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                    entity.setEnderPearlCooldown();
                }

                if (entity.getGapCooldown() == 0 && entity.getHealth() <= ((float) 2/3 * entity.getMaxHealth())) {
                    CombatBehaviour.eatingGoldenApple(entity, levelaccessor, amount);
                    entity.setGapCooldown();
                }
            }
        }
    }
}

