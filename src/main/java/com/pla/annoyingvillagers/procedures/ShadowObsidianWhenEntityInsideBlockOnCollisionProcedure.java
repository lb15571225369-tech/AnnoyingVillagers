package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class ShadowObsidianWhenEntityInsideBlockOnCollisionProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            boolean flag = false;

//            if (entity instanceof LivingEntity) {
//                LivingEntity livingentity = (LivingEntity)entity;
//
//                flag = livingentity.getMobType() == MobType.UNDEAD;
//            } else {
//                flag = false;
//            }

            if (!flag) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1");
                }

                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(new Random(), 0.5D, 1.0D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(new Random(), 0.5D, 1.0D), false);
                    }
                }

                LivingEntity livingentity1;
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                ItemStack itemstack1 = itemstack;

                if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 1.0D, 10.0D), new Random(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getOffhandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 1.0D, 10.0D), new Random(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 1.0D, 10.0D), new Random(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 1.0D, 10.0D), new Random(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 1.0D, 10.0D), new Random(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                if (entity instanceof LivingEntity) {
                    livingentity1 = (LivingEntity) entity;
                    itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack1 = itemstack;
                if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 1.0D, 10.0D), new Random(), (ServerPlayer) null)) {
                    itemstack1.shrink(1);
                    itemstack1.setDamageValue(0);
                }

                entity.hurt(DamageSource.GENERIC, 11.5F);
                entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -2.0D, 0.4D, entity.getLookAngle().z * -2.0D));
                if (Math.random() == 0.5D) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity2 = (LivingEntity) entity;

                        if (!livingentity2.level.isClientSide()) {
                            livingentity2.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 2, false, true));
                        }
                    }

                    entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -4.0D, 0.5D, entity.getLookAngle().z * -4.0D));
                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    itemstack1 = itemstack;
                    if (itemstack1.hurt(300, new Random(), (ServerPlayer) null)) {
                        itemstack1.shrink(1);
                        itemstack1.setDamageValue(0);
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                    } else {
                        itemstack = ItemStack.EMPTY;
                    }

                    itemstack1 = itemstack;
                    if (itemstack1.hurt(300, new Random(), (ServerPlayer) null)) {
                        itemstack1.shrink(1);
                        itemstack1.setDamageValue(0);
                    }

                    entity.hurt(DamageSource.GENERIC, 15.5F);
                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity1 = entity;

                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10");
                            }
                        }
                    };

                    if (Math.random() == 0.09D) {
                        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -6.0D, 0.4D, entity.getLookAngle().z * -6.0D));
                        if (levelaccessor instanceof Level) {
                            level = (Level) levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:heavy_hit")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(new Random(), 0.5D, 1.2D));
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:heavy_hit")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(new Random(), 0.5D, 1.2D), false);
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack = livingentity1.getItemBySlot(EquipmentSlot.HEAD);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        itemstack1 = itemstack;
                        if (itemstack1.hurt(1000, new Random(), (ServerPlayer) null)) {
                            itemstack1.shrink(1);
                            itemstack1.setDamageValue(0);
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack = livingentity1.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        itemstack1 = itemstack;
                        if (itemstack1.hurt(1000, new Random(), (ServerPlayer) null)) {
                            itemstack1.shrink(1);
                            itemstack1.setDamageValue(0);
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack = livingentity1.getMainHandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        itemstack1 = itemstack;
                        if (itemstack1.hurt((int) Mth.nextDouble(new Random(), 200.0D, 1002.0D), new Random(), (ServerPlayer) null)) {
                            itemstack1.shrink(1);
                            itemstack1.setDamageValue(0);
                        }

                        entity.hurt(DamageSource.GENERIC, 17.5F);
                        new DelayedTask(1) {
                            @Override
                            public void run() {
                                Entity entity1 = entity;

                                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/knockdown\" 0 10");
                                }
                            }
                        };
                    }
                }
            }

        }
    }
}
