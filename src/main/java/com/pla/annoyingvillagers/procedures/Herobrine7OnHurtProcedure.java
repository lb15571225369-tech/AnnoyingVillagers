package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class Herobrine7OnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;

            if (entity instanceof Mob) {
                Mob mob = (Mob)entity;

                livingentity = mob.getTarget();
            } else {
                livingentity = null;
            }

            if (entity1 == livingentity) {
                Level level;

                if (Math.random() <= 0.8D) {
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
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
                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity2 = entity;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:shadow_obsidian",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }
                            new DelayedTask(1) {
                                @Override
                                public void run() {
                                    Entity entity3 = entity;

                                    if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                        try {
                                            entity3.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:shadow_obsidian",
                                                    entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }
                                    new DelayedTask(1) {
                                        public void run() {
                                            Entity entity4 = entity;

                                            if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                try {
                                                    entity4.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:shadow_obsidian",
                                                            entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                }
                                            }
                                            new DelayedTask(1) {
                                                public void run() {
                                                    Entity entity5 = entity;

                                                    if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                        try {
                                                            entity5.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:shadow_obsidian",
                                                                    entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                        }
                                                    }
                                                    new DelayedTask(1) {
                                                        public void run() {
                                                            Entity entity6 = entity;

                                                            if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                try {
                                                                    entity6.getServer().getCommands().getDispatcher().execute(
                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:shadow_obsidian",
                                                                            entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                }
                                                            }
                                                            new DelayedTask(1) {
                                                                public void run() {
                                                                    Entity entity7 = entity;

                                                                    if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                        try {
                                                                            entity7.getServer().getCommands().getDispatcher().execute(
                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:shadow_obsidian",
                                                                                    entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                        } catch (CommandSyntaxException e) {
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
                    };
                }

                entity.setSprinting(true);
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        entity.setSprinting(false);
                    }
                };
                LivingEntity livingentity1;

                if (Math.random() <= 0.41D) {
                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                        }
                    }

                    Level level1 = entity.level();

                    if (!level1.isClientSide()) {
                        StealthAttackEntity stealthAttackEntity = new StealthAttackEntity((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level1);
                        stealthAttackEntity.setOwner(entity);
                        stealthAttackEntity.setBaseDamage((double)19.0F);
                        stealthAttackEntity.setKnockback(5);
                        stealthAttackEntity.setSilent(true);
                        stealthAttackEntity.setPierceLevel((byte)3);
                        stealthAttackEntity.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                        stealthAttackEntity.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 2.0F, 0.0F);
                        level1.addFreshEntity(stealthAttackEntity);
                    }

                    ItemStack itemstack;
                    Player player;

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                        itemstack.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity1 instanceof Player) {
                            player = (Player)livingentity1;
                            player.getInventory().setChanged();
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity)entity;
                        itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                        itemstack.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                        if (livingentity1 instanceof Player) {
                            player = (Player)livingentity1;
                            player.getInventory().setChanged();
                        }
                    }
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level2 = (Level)levelaccessor1;

                                if (!level2.isClientSide()) {
                                    level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                } else {
                                    level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                                }
                            }

                            LivingEntity livingentity2;
                            ItemStack itemstack1;
                            Player player1;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                                itemstack1.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                if (livingentity2 instanceof Player) {
                                    player1 = (Player)livingentity2;
                                    player1.getInventory().setChanged();
                                }
                            }

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                itemstack1 = new ItemStack(Blocks.AIR);
                                itemstack1.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                                if (livingentity2 instanceof Player) {
                                    player1 = (Player)livingentity2;
                                    player1.getInventory().setChanged();
                                }
                            }
                        }
                    };
                }

                if (Math.random() <= 0.04D && levelaccessor instanceof Level) {
                    level = (Level)levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "soul_legend")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "soul_legend")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (Math.random() <= 0.04D) {
                            LevelAccessor levelaccessor1 = levelaccessor;

                            if (levelaccessor1 instanceof Level) {
                                Level level2 = (Level)levelaccessor1;

                                if (!level2.isClientSide()) {
                                    level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_attack_1")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_attack_1")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }
                        }
                    }
                };
            }
        }
    }
}
