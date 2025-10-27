package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class VillagerGeneralOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final PathfinderMobInventory entity, Entity attacker, double amount) {
        if (entity != null && attacker != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (entity.getEnderPearlCooldown() == 0) {
                    CombatBehaviour.throwEnderPearl(entity, (float) new Random().nextDouble(90.0D, 180.0D));

                    if (Math.random() <= 0.5D) {
                        new DelayedTask(40) {
                            public void run() {
                                if (entity.isAlive()) {
                                    CombatBehaviour.throwEnderPearl(entity, 0.0F);
                                }
                            }
                        };
                    }

                    entity.setSprinting(true);
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setSprinting(false);
                            }
                        }
                    };

                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()));
                    if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 5);
                    }

                    if (Math.random() <= 0.4D) {
                        entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.LAVA_BUCKET));
                    }

                    new DelayedTask(150) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.ENDER_PEARL));
                            }
                        }
                    };

                    if (Math.random() <= 0.3D) {
                        if (!entity.level().isClientSide()) {
                            entity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                        }
                    }

                    if (Math.random() <= 0.2D) {
                        CombatBehaviour.throwEnderPearl(entity, 180.0F);
                    }

                    if (Math.random() <= 0.1D) {
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
                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                    if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 3);
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 3);
                                    }
                                }
                                new DelayedTask(80) {
                                    public void run() {
                                        if (entity.isAlive()) {
                                            CombatBehaviour.throwEnderPearl(entity, 0.0F);

                                            if (Math.random() <= 0.2D) {
                                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.DIAMOND_BLADE.get()));
                                                if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                                }
                                            } else {
                                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                                if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 2);
                                                }
                                                if (Math.random() <= 0.2D) {
                                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.DIAMOND_DAGGER.get()));
                                                    if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 2);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                };
                            }
                        };
                    }

                    if (entity.getHealth() <= 7.0F && levelaccessor instanceof Level) {
                        Level level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.item.break"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.item.break"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
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
