package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class Herobrine7OnPlayerTouchProcedure {

    public static void execute(final LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            new DelayedTask(20) {
                public void run() {
                    if (Math.random() <= 0.09D) {
                        LevelAccessor levelaccessor1 = levelaccessor;
                        Level level;

                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                            }
                        }

                        Entity entity1 = entity;
                        Level level1 = entity1.level();

                        if (!level1.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                            stealthAttackEntity.setOwner(entity);
                            stealthAttackEntity.setBaseDamage((double)18.0F);
                            stealthAttackEntity.setKnockback(5);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPierceLevel((byte)2);
                            stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                            stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                            level1.addFreshEntity(stealthAttackEntity);
                        }

                        LivingEntity livingentity;
                        ItemStack itemstack;
                        Player player;

                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                            itemstack.setCount(1);
                            livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                            if (livingentity instanceof Player) {
                                player = (Player)livingentity;
                                player.getInventory().setChanged();
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                            itemstack.setCount(1);
                            livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                            if (livingentity instanceof Player) {
                                player = (Player)livingentity;
                                player.getInventory().setChanged();
                            }
                        }
                        new DelayedTask(5) {
                            public void run() {
                                LevelAccessor levelaccessor2 = levelaccessor;
                                Level level2;

                                if (levelaccessor2 instanceof Level) {
                                    level2 = (Level)levelaccessor2;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                Entity entity2 = entity;
                                Level level3 = entity2.level();
                                Projectile projectile1;

                                if (!level3.isClientSide()) {
                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level3);
                                    stealthAttackEntity.setOwner(entity);
                                    stealthAttackEntity.setBaseDamage((double)19.0F);
                                    stealthAttackEntity.setKnockback(4);
                                    stealthAttackEntity.setSilent(true);
                                    stealthAttackEntity.setPierceLevel((byte)3);
                                    stealthAttackEntity.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    stealthAttackEntity.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                    level3.addFreshEntity(stealthAttackEntity);
                                }

                                LivingEntity livingentity1;
                                ItemStack itemstack1;
                                Player player1;

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        player1 = (Player)livingentity1;
                                        player1.getInventory().setChanged();
                                    }
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        player1 = (Player)livingentity1;
                                        player1.getInventory().setChanged();
                                    }
                                }

                                entity2 = entity;
                                level3 = entity2.level();
                                if (!level3.isClientSide()) {
                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level3);
                                    stealthAttackEntity.setOwner(entity);
                                    stealthAttackEntity.setBaseDamage((double)18.0F);
                                    stealthAttackEntity.setKnockback(4);
                                    stealthAttackEntity.setSilent(true);
                                    stealthAttackEntity.setPierceLevel((byte)2);
                                    stealthAttackEntity.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    stealthAttackEntity.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                    level3.addFreshEntity(stealthAttackEntity);
                                }

                                levelaccessor2 = levelaccessor;
                                if (levelaccessor2 instanceof Level) {
                                    level2 = (Level)levelaccessor2;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }
                            }
                        };
                        new DelayedTask(10) {
                            public void run() {
                                LevelAccessor levelaccessor2 = levelaccessor;
                                Level level2;

                                if (levelaccessor2 instanceof Level) {
                                    level2 = (Level)levelaccessor2;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                levelaccessor2 = levelaccessor;
                                if (levelaccessor2 instanceof Level) {
                                    level2 = (Level)levelaccessor2;
                                    if (!level2.isClientSide()) {
                                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    } else {
                                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                    }
                                }

                                LivingEntity livingentity1;
                                ItemStack itemstack1;
                                Player player1;

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        player1 = (Player)livingentity1;
                                        player1.getInventory().setChanged();
                                    }
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack(Blocks.AIR);
                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        player1 = (Player)livingentity1;
                                        player1.getInventory().setChanged();
                                    }
                                }
                            }
                        };
                    }
                }
            };
        }
    }
}
