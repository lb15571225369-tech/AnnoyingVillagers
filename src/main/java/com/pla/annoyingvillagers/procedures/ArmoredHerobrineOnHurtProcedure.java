package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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

import java.util.Objects;

public class ArmoredHerobrineOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            Level level;
            Level level1;
            Projectile projectile;
            LivingEntity livingentity;
            ItemStack itemstack;
            Player player;

            if (Math.random() <= 0.8D) {
                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }

            if (Math.random() <= 0.42D) {
                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }

                level1 = entity.level();
                if (!level1.isClientSide()) {
                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                    stealthAttackEntity.setOwner(entity);
                    stealthAttackEntity.setBaseDamage((double) 19.0F);
                    stealthAttackEntity.setKnockback(5);
                    stealthAttackEntity.setSilent(true);
                    stealthAttackEntity.setPierceLevel((byte) 3);
                    stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                    stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                    level1.addFreshEntity(stealthAttackEntity);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                    itemstack.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    if (livingentity instanceof Player) {
                        player = (Player) livingentity;
                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                    itemstack.setCount(1);
                    livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                    if (livingentity instanceof Player) {
                        player = (Player) livingentity;
                        player.getInventory().setChanged();
                    }
                }
                new DelayedTask(5) {
                    public void run() {
                        Entity entity1 = entity;
                        Level level2 = entity1.level();

                        if (!level2.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level2);
                            stealthAttackEntity.setOwner(entity1);
                            stealthAttackEntity.setBaseDamage((double) 18.0F);
                            stealthAttackEntity.setKnockback(4);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPierceLevel((byte) 3);
                            stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                            stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                            level2.addFreshEntity(stealthAttackEntity);
                        }

                        LivingEntity livingentity1;
                        ItemStack itemstack1;
                        Player player1;

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                            itemstack1.setCount(1);
                            livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                            if (livingentity1 instanceof Player) {
                                player1 = (Player) livingentity1;
                                player1.getInventory().setChanged();
                            }
                        }

                        entity1 = entity;
                        level2 = entity1.level();
                        if (!level2.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level2);
                            stealthAttackEntity.setOwner(entity1);
                            stealthAttackEntity.setBaseDamage((double) 24.0F);
                            stealthAttackEntity.setKnockback(4);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPierceLevel((byte) 3);
                            stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                            stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                            level2.addFreshEntity(stealthAttackEntity);
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                            itemstack1.setCount(1);
                            livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                            if (livingentity1 instanceof Player) {
                                player1 = (Player) livingentity1;
                                player1.getInventory().setChanged();
                            }
                        }

                        LevelAccessor levelaccessor1 = levelaccessor;

                        if (levelaccessor1 instanceof Level) {
                            Level level3 = (Level) levelaccessor1;

                            if (!level3.isClientSide()) {
                                level3.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            }
                        }
                        new DelayedTask(4) {
                            public void run() {
                                LivingEntity livingentity2;
                                ItemStack itemstack2;
                                Player player2;

                                if (entity instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity) entity;
                                    itemstack2 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                                    itemstack2.setCount(1);
                                    livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                    if (livingentity2 instanceof Player) {
                                        player2 = (Player) livingentity2;
                                        player2.getInventory().setChanged();
                                    }
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity) entity;
                                    itemstack2 = new ItemStack(Blocks.AIR);
                                    itemstack2.setCount(1);
                                    livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                    if (livingentity2 instanceof Player) {
                                        player2 = (Player) livingentity2;
                                        player2.getInventory().setChanged();
                                    }
                                }
                            }
                        };
                    }
                };
            } else if (Math.random() <= 0.4D) {
                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep"))), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep"))), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place"))), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place"))), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }

                level1 = entity.level();
                if (!level1.isClientSide()) {
                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                    stealthAttackEntity.setOwner(entity);
                    stealthAttackEntity.setBaseDamage((double) 20.0F);
                    stealthAttackEntity.setKnockback(5);
                    stealthAttackEntity.setSilent(true);
                    stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                    stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                    level1.addFreshEntity(stealthAttackEntity);
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                    itemstack.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    if (livingentity instanceof Player) {
                        player = (Player) livingentity;
                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity) entity;
                    itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                    itemstack.setCount(1);
                    livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                    if (livingentity instanceof Player) {
                        player = (Player) livingentity;
                        player.getInventory().setChanged();
                    }
                }
                new DelayedTask(1) {
                    public void run() {
                        LivingEntity livingentity1;
                        ItemStack itemstack1;
                        Player player1;

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                            itemstack1.setCount(1);
                            livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                            if (livingentity1 instanceof Player) {
                                player1 = (Player) livingentity1;
                                player1.getInventory().setChanged();
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity;
                            itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                            itemstack1.setCount(1);
                            livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                            if (livingentity1 instanceof Player) {
                                player1 = (Player) livingentity1;
                                player1.getInventory().setChanged();
                            }
                        }

                        Entity entity1 = entity;
                        Level level2 = entity1.level();

                        if (!level2.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level2);
                            stealthAttackEntity.setOwner(entity1);
                            stealthAttackEntity.setBaseDamage((double) 18.0F);
                            stealthAttackEntity.setKnockback(4);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                            stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                            level2.addFreshEntity(stealthAttackEntity);
                        }

                        LevelAccessor levelaccessor1 = levelaccessor;

                        if (levelaccessor1 instanceof Level) {
                            Level level3 = (Level) levelaccessor1;

                            if (!level3.isClientSide()) {
                                level3.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            }
                        }
                        new DelayedTask(1) {
                            public void run() {
                                LivingEntity livingentity2;
                                ItemStack itemstack2;
                                Player player2;

                                if (entity instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity) entity;
                                    itemstack2 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                    itemstack2.setCount(1);
                                    livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                    if (livingentity2 instanceof Player) {
                                        player2 = (Player) livingentity2;
                                        player2.getInventory().setChanged();
                                    }
                                }

                                if (entity instanceof LivingEntity) {
                                    livingentity2 = (LivingEntity) entity;
                                    itemstack2 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                    itemstack2.setCount(1);
                                    livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                    if (livingentity2 instanceof Player) {
                                        player2 = (Player) livingentity2;
                                        player2.getInventory().setChanged();
                                    }
                                }

                                Entity entity2 = entity;
                                Level level4 = entity2.level();

                                if (!level4.isClientSide()) {
                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level4);
                                    stealthAttackEntity.setOwner(entity2);
                                    stealthAttackEntity.setBaseDamage((double) 19.0F);
                                    stealthAttackEntity.setKnockback(4);
                                    stealthAttackEntity.setSilent(true);
                                    stealthAttackEntity.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    stealthAttackEntity.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                    level4.addFreshEntity(stealthAttackEntity);
                                }
                                new DelayedTask(1) {
                                    public void run() {
                                        Entity entity3 = entity;
                                        Level level5 = entity3.level();

                                        if (!level5.isClientSide()) {
                                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level5);
                                            stealthAttackEntity.setOwner(entity3);
                                            stealthAttackEntity.setBaseDamage((double) 27.0F);
                                            stealthAttackEntity.setKnockback(4);
                                            stealthAttackEntity.setSilent(true);
                                            stealthAttackEntity.setPierceLevel((byte) 5);
                                            stealthAttackEntity.setPos(entity3.getX(), entity3.getEyeY() - 0.1D, entity3.getZ());
                                            stealthAttackEntity.shoot(entity3.getLookAngle().x, entity3.getLookAngle().y, entity3.getLookAngle().z, 1.0F, 0.0F);
                                            level5.addFreshEntity(stealthAttackEntity);
                                        }

                                        LivingEntity livingentity3;
                                        ItemStack itemstack3;
                                        Player player3;

                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity) entity;
                                            itemstack3 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                            itemstack3.setCount(1);
                                            livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack3);
                                            if (livingentity3 instanceof Player) {
                                                player3 = (Player) livingentity3;
                                                player3.getInventory().setChanged();
                                            }
                                        }

                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity) entity;
                                            itemstack3 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                            itemstack3.setCount(1);
                                            livingentity3.setItemInHand(InteractionHand.OFF_HAND, itemstack3);
                                            if (livingentity3 instanceof Player) {
                                                player3 = (Player) livingentity3;
                                                player3.getInventory().setChanged();
                                            }
                                        }
                                        new DelayedTask(1) {
                                            public void run() {
                                                Entity entity4 = entity;
                                                Level level6 = entity4.level();

                                                if (!level6.isClientSide()) {
                                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level6);
                                                    stealthAttackEntity.setOwner(entity4);
                                                    stealthAttackEntity.setBaseDamage((double) 17.0F);
                                                    stealthAttackEntity.setKnockback(4);
                                                    stealthAttackEntity.setSilent(true);
                                                    stealthAttackEntity.setPos(entity4.getX(), entity4.getEyeY() - 0.1D, entity4.getZ());
                                                    stealthAttackEntity.shoot(entity4.getLookAngle().x, entity4.getLookAngle().y, entity4.getLookAngle().z, 1.0F, 0.0F);
                                                    level6.addFreshEntity(stealthAttackEntity);
                                                }

                                                LivingEntity livingentity4;
                                                ItemStack itemstack4;
                                                Player player4;

                                                if (entity instanceof LivingEntity) {
                                                    livingentity4 = (LivingEntity) entity;
                                                    itemstack4 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                                    itemstack4.setCount(1);
                                                    livingentity4.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                                    if (livingentity4 instanceof Player) {
                                                        player4 = (Player) livingentity4;
                                                        player4.getInventory().setChanged();
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity4 = (LivingEntity) entity;
                                                    itemstack4 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                                    itemstack4.setCount(1);
                                                    livingentity4.setItemInHand(InteractionHand.OFF_HAND, itemstack4);
                                                    if (livingentity4 instanceof Player) {
                                                        player4 = (Player) livingentity4;
                                                        player4.getInventory().setChanged();
                                                    }
                                                }

                                                LevelAccessor levelaccessor2 = levelaccessor;

                                                if (levelaccessor2 instanceof Level) {
                                                    Level level7 = (Level) levelaccessor2;

                                                    if (!level7.isClientSide()) {
                                                        level7.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair"))), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                    }
                                                }
                                                new DelayedTask(1) {
                                                    public void run() {
                                                        Entity entity5 = entity;
                                                        Level level8 = entity5.level();

                                                        if (!level8.isClientSide()) {
                                                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level8);
                                                            stealthAttackEntity.setOwner(entity5);
                                                            stealthAttackEntity.setBaseDamage((double) 17.0F);
                                                            stealthAttackEntity.setKnockback(4);
                                                            stealthAttackEntity.setSilent(true);
                                                            stealthAttackEntity.setPos(entity5.getX(), entity5.getEyeY() - 0.1D, entity5.getZ());
                                                            stealthAttackEntity.shoot(entity5.getLookAngle().x, entity5.getLookAngle().y, entity5.getLookAngle().z, 1.0F, 0.0F);
                                                            level8.addFreshEntity(stealthAttackEntity);
                                                        }

                                                        LivingEntity livingentity5;
                                                        ItemStack itemstack5;
                                                        Player player5;

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity5 = (LivingEntity) entity;
                                                            itemstack5 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                                                            itemstack5.setCount(1);
                                                            livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack5);
                                                            if (livingentity5 instanceof Player) {
                                                                player5 = (Player) livingentity5;
                                                                player5.getInventory().setChanged();
                                                            }
                                                        }

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity5 = (LivingEntity) entity;
                                                            itemstack5 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                                                            itemstack5.setCount(1);
                                                            livingentity5.setItemInHand(InteractionHand.OFF_HAND, itemstack5);
                                                            if (livingentity5 instanceof Player) {
                                                                player5 = (Player) livingentity5;
                                                                player5.getInventory().setChanged();
                                                            }
                                                        }
                                                        new DelayedTask(4) {
                                                            public void run() {
                                                                Entity entity6 = entity;
                                                                Level level9 = entity6.level();

                                                                if (!level9.isClientSide()) {
                                                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level9);
                                                                    stealthAttackEntity.setOwner(entity6);
                                                                    stealthAttackEntity.setBaseDamage((double) 17.0F);
                                                                    stealthAttackEntity.setKnockback(4);
                                                                    stealthAttackEntity.setSilent(true);
                                                                    stealthAttackEntity.setPos(entity6.getX(), entity6.getEyeY() - 0.1D, entity6.getZ());
                                                                    stealthAttackEntity.shoot(entity6.getLookAngle().x, entity6.getLookAngle().y, entity6.getLookAngle().z, 1.0F, 0.0F);
                                                                    level9.addFreshEntity(stealthAttackEntity);
                                                                }

                                                                LevelAccessor levelaccessor3 = levelaccessor;

                                                                if (levelaccessor3 instanceof Level) {
                                                                    Level level10 = (Level) levelaccessor3;

                                                                    if (!level10.isClientSide()) {
                                                                        level10.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair"))), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                                    }
                                                                }

                                                                LivingEntity livingentity6;
                                                                ItemStack itemstack6;
                                                                Player player6;

                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity6 = (LivingEntity) entity;
                                                                    itemstack6 = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                                                                    itemstack6.setCount(1);
                                                                    livingentity6.setItemInHand(InteractionHand.MAIN_HAND, itemstack6);
                                                                    if (livingentity6 instanceof Player) {
                                                                        player6 = (Player) livingentity6;
                                                                        player6.getInventory().setChanged();
                                                                    }
                                                                }

                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity6 = (LivingEntity) entity;
                                                                    itemstack6 = new ItemStack(Blocks.AIR);
                                                                    itemstack6.setCount(1);
                                                                    livingentity6.setItemInHand(InteractionHand.OFF_HAND, itemstack6);
                                                                    if (livingentity6 instanceof Player) {
                                                                        player6 = (Player) livingentity6;
                                                                        player6.getInventory().setChanged();
                                                                    }
                                                                }
                                                            }
                                                        };
                                                        LevelAccessor levelaccessor3 = levelaccessor;

                                                        if (levelaccessor3 instanceof Level) {
                                                            Level level9 = (Level) levelaccessor3;

                                                            if (!level9.isClientSide()) {
                                                                level9.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair"))), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                            }
                                                        }
                                                    }
                                                };
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                };
            }

            if (Math.random() == 0.04D && levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "soul_legend"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "soul_legend"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            if (Math.random() == 0.04D && levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_attack_1"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_attack_1"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
