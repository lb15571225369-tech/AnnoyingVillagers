package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class DarkHerobrineOnPlayerTouchProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.009D) {
                if (levelaccessor instanceof Level) {
                    Level level = (Level)levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.anvil.place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.anvil.place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:shadow_obsidian",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            }
            new DelayedTask(2) {
                public void run() {
                    if (Math.random() <= 0.09D) {
                        LevelAccessor levelaccessor1 = levelaccessor;
                        Level level1;

                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level1 = (Level)levelaccessor1;
                            if (!level1.isClientSide()) {
                                level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                            }
                        }

                        Entity entity1 = entity;
                        Level level2 = entity1.level();

                        if (!level2.isClientSide()) {
                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level2);
                            stealthAttackEntity.setOwner(entity1);
                            stealthAttackEntity.setBaseDamage((double)24.0F);
                            stealthAttackEntity.setKnockback(5);
                            stealthAttackEntity.setSilent(true);
                            stealthAttackEntity.setPierceLevel((byte)2);
                            stealthAttackEntity.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                            stealthAttackEntity.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                            level2.addFreshEntity(stealthAttackEntity);
                        }

                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity = (LivingEntity)entity;
                            ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());

                            itemstack.setCount(1);
                            livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                            if (livingentity instanceof Player) {
                                Player player = (Player)livingentity;

                                player.getInventory().setChanged();
                            }
                        }
                        new DelayedTask(5) {
                            public void run() {
                                Entity entity2 = entity;
                                Level level3 = entity2.level();

                                if (!level3.isClientSide()) {
                                    StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level3);
                                    stealthAttackEntity.setOwner(entity2);
                                    stealthAttackEntity.setBaseDamage((double)20.0F);
                                    stealthAttackEntity.setKnockback(4);
                                    stealthAttackEntity.setSilent(true);
                                    stealthAttackEntity.setPierceLevel((byte)4);
                                    stealthAttackEntity.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    stealthAttackEntity.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                    level3.addFreshEntity(stealthAttackEntity);
                                }

                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingentity1 = (LivingEntity)entity;
                                    ItemStack itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());

                                    itemstack1.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                    if (livingentity1 instanceof Player) {
                                        Player player1 = (Player)livingentity1;

                                        player1.getInventory().setChanged();
                                    }
                                }

                                LevelAccessor levelaccessor2 = levelaccessor;

                                if (levelaccessor2 instanceof Level) {
                                    Level level4 = (Level)levelaccessor2;

                                    if (!level4.isClientSide()) {
                                        level4.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                    }
                                }
                                new DelayedTask(4) {
                                    public void run() {
                                        Entity entity3 = entity;
                                        Level level5 = entity3.level();

                                        if (!level5.isClientSide()) {
                                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level5);
                                            stealthAttackEntity.setOwner(entity3);
                                            stealthAttackEntity.setBaseDamage((double)26.0F);
                                            stealthAttackEntity.setKnockback(7);
                                            stealthAttackEntity.setSilent(true);
                                            stealthAttackEntity.setPierceLevel((byte)4);
                                            stealthAttackEntity.setPos(entity3.getX(), entity3.getEyeY() - 0.1D, entity3.getZ());
                                            stealthAttackEntity.shoot(entity3.getLookAngle().x, entity3.getLookAngle().y, entity3.getLookAngle().z, 1.0F, 0.0F);
                                            level5.addFreshEntity(stealthAttackEntity);
                                        }

                                        if (entity instanceof LivingEntity) {
                                            LivingEntity livingentity2 = (LivingEntity)entity;
                                            ItemStack itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());

                                            itemstack2.setCount(1);
                                            livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                            if (livingentity2 instanceof Player) {
                                                Player player2 = (Player)livingentity2;

                                                player2.getInventory().setChanged();
                                            }
                                        }

                                        entity3 = entity;
                                        level5 = entity3.level();
                                        if (!level5.isClientSide()) {
                                            StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level5);
                                            stealthAttackEntity.setOwner(entity3);
                                            stealthAttackEntity.setBaseDamage((double)20.0F);
                                            stealthAttackEntity.setKnockback(4);
                                            stealthAttackEntity.setSilent(true);
                                            stealthAttackEntity.setPierceLevel((byte)3);
                                            stealthAttackEntity.setPos(entity3.getX(), entity3.getEyeY() - 0.1D, entity3.getZ());
                                            stealthAttackEntity.shoot(entity3.getLookAngle().x, entity3.getLookAngle().y, entity3.getLookAngle().z, 1.0F, 0.0F);
                                            level5.addFreshEntity(stealthAttackEntity);
                                        }
                                        new DelayedTask(5) {
                                            public void run() {
                                                LevelAccessor levelaccessor3 = levelaccessor;

                                                if (levelaccessor3 instanceof Level) {
                                                    Level level6 = (Level)levelaccessor3;

                                                    if (!level6.isClientSide()) {
                                                        level6.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    LivingEntity livingentity3 = (LivingEntity)entity;
                                                    ItemStack itemstack3 = new ItemStack(Blocks.AIR);

                                                    itemstack3.setCount(1);
                                                    livingentity3.setItemInHand(InteractionHand.OFF_HAND, itemstack3);
                                                    if (livingentity3 instanceof Player) {
                                                        Player player3 = (Player)livingentity3;

                                                        player3.getInventory().setChanged();
                                                    }
                                                }
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                }
            };
        }
    }
}
