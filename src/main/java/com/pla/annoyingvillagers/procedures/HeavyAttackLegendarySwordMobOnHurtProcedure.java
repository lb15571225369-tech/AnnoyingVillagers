package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.RandomSource;
public class HeavyAttackLegendarySwordMobOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            levelaccessor.addParticle(ParticleTypes.EXPLOSION, d0, d1, d2, 0.0D, 1.0D, 0.0D);
            LivingEntity livingentity;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            ItemStack itemstack1 = itemstack;

            if (itemstack1.hurt(260, RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt(260, RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt(260, RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack1 = itemstack;
            if (itemstack1.hurt(260, RandomSource.create(), (ServerPlayer) null)) {
                itemstack1.shrink(1);
                itemstack1.setDamageValue(0);
            }

            Level level;

            if (Math.random() < 0.3D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(700, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

            if (Math.random() < 0.3D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(700, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

            if (Math.random() < 0.3D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(700, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

            if (Math.random() < 0.3D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(700, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

            if (Math.random() < 0.1D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(900, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(900, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(900, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt(900, RandomSource.create(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.item.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.item.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.glass.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

        }
    }
}
