package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class Steve2OnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                Level level;

                if (!entity.getPersistentData().getBoolean("steve_l_g_h_a")) {
                    if (Math.random() <= 0.16D) {
                        new DelayedTask(100) {
                            public void run() {
                                if (Math.random() <= 0.12D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.LEGENDARY_SWORD_MOB.get());

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SMITE, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                } else if (Math.random() <= 0.14D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.CRAFTING_TABLE.get());

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SMITE, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                } else if (Math.random() <= 0.1D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack(Items.DIAMOND_SWORD);

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 7);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                } else if (Math.random() <= 0.1D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get());

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 7);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                } else if (Math.random() <= 0.1D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SMITE, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                } else if (Math.random() <= 0.3D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack(Items.DIAMOND_SWORD);

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 7);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                } else if (Math.random() <= 0.7D) {
                                    new DelayedTask(20) {
                                        public void run() {
                                            LivingEntity livingentity;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                                                itemstack.setCount(1);
                                                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                                if (livingentity instanceof Player) {
                                                    Player player = (Player)livingentity;

                                                    player.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack1;

                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SMITE, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SHARPNESS, 4);
                                            if (entity instanceof LivingEntity) {
                                                livingentity = (LivingEntity)entity;
                                                itemstack1 = livingentity.getMainHandItem();
                                            } else {
                                                itemstack1 = ItemStack.EMPTY;
                                            }

                                            itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                                        }
                                    };
                                }
                            }
                        };
                    }

                    if (Math.random() <= 0.0098D) {
                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> Why?"), false);
                        }

                        if (levelaccessor instanceof Level) {
                            level = (Level)levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                    }

                    if (Math.random() <= 0.09D) {
                        new DelayedTask(20) {
                            public void run() {
                                Entity entity2 = entity;

                                entity2.setYRot(0.0F);
                                entity2.setXRot((float)Mth.nextDouble(AnnoyingVillagers.randomSource, -90.0D, -180.0D));
                                entity2.setYBodyRot(entity2.getYRot());
                                entity2.setYHeadRot(entity2.getYRot());
                                entity2.yRotO = entity2.getYRot();
                                entity2.xRotO = entity2.getXRot();
                                if (entity2 instanceof LivingEntity) {
                                    LivingEntity livingentity = (LivingEntity)entity2;

                                    livingentity.yBodyRotO = livingentity.getYRot();
                                    livingentity.yHeadRotO = livingentity.getYRot();
                                }

                                entity2 = entity;
                                Level level1 = entity2.level();

                                if (!level1.isClientSide()) {
                                    Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                    projectile.setOwner(entity2);
                                    projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 2.0F, 0.0F);
                                    level1.addFreshEntity(projectile);
                                }

                                if (Math.random() <= 0.2D) {
                                    new DelayedTask(40) {
                                        public void run() {
                                            Entity entity3 = entity;
                                            Level level2 = entity3.level();

                                            if (!level2.isClientSide()) {
                                                Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level2);
                                                projectile1.setOwner(entity3);
                                                projectile1.setPos(entity3.getX(), entity3.getEyeY() - 0.1D, entity3.getZ());
                                                projectile1.shoot(entity3.getLookAngle().x, entity3.getLookAngle().y, entity3.getLookAngle().z, 1.8F, 0.0F);
                                                level2.addFreshEntity(projectile1);
                                            }

                                            LevelAccessor levelaccessor1 = levelaccessor;

                                            if (levelaccessor1 instanceof Level) {
                                                Level level3 = (Level)levelaccessor1;

                                                if (!level3.isClientSide()) {
                                                    level3.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F);
                                                } else {
                                                    level3.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                                                }
                                            }
                                        }
                                    };
                                }
                            }
                        };
                    }

                    if (Math.random() <= 0.09D) {
                        new DelayedTask(20) {
                            public void run() {
                                Entity entity2 = entity;

                                entity2.setYRot(0.0F);
                                entity2.setXRot(180.0F);
                                entity2.setYBodyRot(entity2.getYRot());
                                entity2.setYHeadRot(entity2.getYRot());
                                entity2.yRotO = entity2.getYRot();
                                entity2.xRotO = entity2.getXRot();
                                if (entity2 instanceof LivingEntity) {
                                    LivingEntity livingentity = (LivingEntity)entity2;

                                    livingentity.yBodyRotO = livingentity.getYRot();
                                    livingentity.yHeadRotO = livingentity.getYRot();
                                }

                                entity2 = entity;
                                Level level1 = entity2.level();

                                if (!level1.isClientSide()) {
                                    Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                    projectile.setOwner(entity2);
                                    projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 2.0F, 0.0F);
                                    level1.addFreshEntity(projectile);
                                }

                                LivingEntity livingentity1;

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    ItemStack itemstack = new ItemStack(Items.BOW);

                                    itemstack.setCount(1);
                                    livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                    if (livingentity1 instanceof Player) {
                                        Player player = (Player)livingentity1;

                                        player.getInventory().setChanged();
                                    }
                                }

                                ItemStack itemstack1;

                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getMainHandItem();
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack1.enchant(Enchantments.POWER_ARROWS, 5);
                                if (entity instanceof LivingEntity) {
                                    livingentity1 = (LivingEntity)entity;
                                    itemstack1 = livingentity1.getMainHandItem();
                                } else {
                                    itemstack1 = ItemStack.EMPTY;
                                }

                                itemstack1.enchant(Enchantments.PUNCH_ARROWS, 5);
                                new DelayedTask(20) {
                                    public void run() {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity livingentity2 = (LivingEntity)entity;

                                            if (!livingentity2.level().isClientSide()) {
                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2, false, false));
                                            }
                                        }
                                    }
                                };
                                new DelayedTask(80) {
                                    public void run() {
                                        Entity entity3 = entity;

                                        entity3.setYRot(0.0F);
                                        entity3.setXRot(180.0F);
                                        entity3.setYBodyRot(entity3.getYRot());
                                        entity3.setYHeadRot(entity3.getYRot());
                                        entity3.yRotO = entity3.getYRot();
                                        entity3.xRotO = entity3.getXRot();
                                        if (entity3 instanceof LivingEntity) {
                                            LivingEntity livingentity2 = (LivingEntity)entity3;

                                            livingentity2.yBodyRotO = livingentity2.getYRot();
                                            livingentity2.yHeadRotO = livingentity2.getYRot();
                                        }

                                        LevelAccessor levelaccessor1 = levelaccessor;

                                        if (levelaccessor1 instanceof Level) {
                                            Level level2 = (Level)levelaccessor1;

                                            if (!level2.isClientSide()) {
                                                level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F);
                                            } else {
                                                level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 0.4F, 1.0F, false);
                                            }
                                        }

                                        entity3 = entity;
                                        Level level3 = entity3.level();

                                        if (!level3.isClientSide()) {
                                            Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level3);
                                            projectile1.setOwner(entity3);
                                            projectile1.setPos(entity3.getX(), entity3.getEyeY() - 0.1D, entity3.getZ());
                                            projectile1.shoot(entity3.getLookAngle().x, entity3.getLookAngle().y, entity3.getLookAngle().z, 2.0F, 0.0F);
                                            level3.addFreshEntity(projectile1);
                                        }

                                        LivingEntity livingentity3;

                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity)entity;
                                            ItemStack itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                                            itemstack2.setCount(1);
                                            livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                            if (livingentity3 instanceof Player) {
                                                Player player1 = (Player)livingentity3;

                                                player1.getInventory().setChanged();
                                            }
                                        }

                                        ItemStack itemstack3;

                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity)entity;
                                            itemstack3 = livingentity3.getMainHandItem();
                                        } else {
                                            itemstack3 = ItemStack.EMPTY;
                                        }

                                        itemstack3.enchant(Enchantments.SHARPNESS, 5);
                                        if (entity instanceof LivingEntity) {
                                            livingentity3 = (LivingEntity)entity;
                                            itemstack3 = livingentity3.getMainHandItem();
                                        } else {
                                            itemstack3 = ItemStack.EMPTY;
                                        }

                                        itemstack3.enchant(Enchantments.MENDING, 5);
                                    }
                                };
                            }
                        };
                    }

                    if (Math.random() <= 0.009D && levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevechuanqi")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }

                    if (Math.random() <= 0.01D) {
                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Steve> Why?"), false);
                        }

                        if (levelaccessor instanceof Level) {
                            level = (Level)levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "stevesaywhy")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                    }
                    new DelayedTask(80) {
                        public void run() {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingentity = (LivingEntity)entity;
                                ItemStack itemstack = new ItemStack(Items.ENDER_PEARL);

                                itemstack.setCount(1);
                                livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                                if (livingentity instanceof Player) {
                                    Player player = (Player)livingentity;

                                    player.getInventory().setChanged();
                                }
                            }
                        }
                    };
                }

                LivingEntity livingentity;

                if (Math.random() <= 0.39D && entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.ESCAPE.get(), 1, 1, false, false));
                    }
                }

                if (Math.random() <= 0.57D && entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                    }
                }

                if (Math.random() <= 0.05D && !entity.getPersistentData().getBoolean("steve_l_g_h_a")) {
                    entity.getPersistentData().putBoolean("steve_l_g_h_a", true);
                    new DelayedTask(2) {
                        @Override
                        public void run() {
                            entity.setDeltaMovement(new Vec3(0.0D, 1.3D, 0.0D));
                        }
                    };
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        ItemStack itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get());

                        itemstack.setCount(1);
                        livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity instanceof Player) {
                            Player player = (Player)livingentity;

                            player.getInventory().setChanged();
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "playsound epicfight:sfx.entity_move neutral @p",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"epicfight:biped/skill/demolition_leap\" 0 1",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                    }

                    if (levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "steveattack")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                    new DelayedTask(8) {
                        public void run() {
                            LivingEntity livingentity1;

                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                ItemStack itemstack1 = new ItemStack((ItemLike)AnnoyingVillagersModItems.AXE_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED.get());

                                itemstack1.setCount(1);
                                livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                if (livingentity1 instanceof Player) {
                                    Player player1 = (Player)livingentity1;

                                    player1.getInventory().setChanged();
                                }
                            }

                            LevelAccessor levelaccessor1 = levelaccessor;
                            Level level1;

                            if (levelaccessor1 instanceof Level) {
                                level1 = (Level)levelaccessor1;
                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "l_g_w_u")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "l_g_w_u")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level1 = (Level)levelaccessor1;
                                if (!level1.isClientSide()) {
                                    level1.playSound((Player)null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                } else {
                                    level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "zhanshenzhirenjuexing")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof ServerLevel) {
                                ServerLevel serverlevel = (ServerLevel)levelaccessor1;

                                serverlevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getZ(), entity.getY(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                            }

                            entity.setDeltaMovement(new Vec3(entity1.getX(), entity1.getY(), entity1.getZ()));
                            Entity entity2 = entity;

                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "playsound annoyingvillagers:heavy_attack_start neutral @p",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }

                            if (entity instanceof LivingEntity) {
                                livingentity1 = (LivingEntity)entity;
                                if (!livingentity1.level().isClientSide()) {
                                    livingentity1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 2, false, false));
                                }
                            }

                            entity2 = entity;
                            if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "indestructible @s play \"annoyingvillagers:biped/combat/legendary_sword_heavy_attack\" 0 1",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                }
                            }
                            new DelayedTask(25) {
                                public void run() {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingentity2 = (LivingEntity)entity;
                                        ItemStack itemstack2 = new ItemStack((ItemLike)AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD_MOB.get());

                                        itemstack2.setCount(1);
                                        livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                        if (livingentity2 instanceof Player) {
                                            Player player2 = (Player)livingentity2;

                                            player2.getInventory().setChanged();
                                        }
                                    }
                                    new DelayedTask(20) {
                                        public void run() {
                                            entity.getPersistentData().putBoolean("steve_l_g_h_a", false);
                                            LivingEntity livingentity3;

                                            if (entity instanceof LivingEntity) {
                                                livingentity3 = (LivingEntity) entity;
                                                ItemStack itemstack3 = new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                                                itemstack3.setCount(1);
                                                livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack3);
                                                if (livingentity3 instanceof Player) {
                                                    Player player3 = (Player) livingentity3;

                                                    player3.getInventory().setChanged();
                                                }
                                            }

                                            ItemStack itemstack4;

                                            if (entity instanceof LivingEntity) {
                                                livingentity3 = (LivingEntity) entity;
                                                itemstack4 = livingentity3.getMainHandItem();
                                            } else {
                                                itemstack4 = ItemStack.EMPTY;
                                            }

                                            itemstack4.enchant(Enchantments.SHARPNESS, 5);
                                        }
                                    };
                                }
                            };
                        }
                    };
                }
            }

        }
    }
}
