package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class BlueVillagerGeneralOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!entity1.getPersistentData().getBoolean("kick_x")) {
                LivingEntity livingentity;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity = mob.getTarget();
                } else {
                    livingentity = null;
                }

                if (entity1 == livingentity && entity.isAlive()) {
                    LivingEntity livingentity1;
                    Level level;
                    Projectile projectile;

                    if (Math.random() <= 0.09D) {
                        entity.setYRot(0.0F);
                        entity.setXRot((float)Mth.nextDouble(AnnoyingVillagers.randomSource, 90.0D, 180.0D));
                        entity.setYBodyRot(entity.getYRot());
                        entity.setYHeadRot(entity.getYRot());
                        entity.yRotO = entity.getYRot();
                        entity.xRotO = entity.getXRot();
                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity;
                            livingentity1.yBodyRotO = livingentity1.getYRot();
                            livingentity1.yHeadRotO = livingentity1.getYRot();
                        }

                        level = entity.level;
                        if (!level.isClientSide()) {
                            projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                            projectile.setOwner(entity);
                            projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                            projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.7F, 0.0F);
                            level.addFreshEntity(projectile);
                        }

                        if (Math.random() <= 0.5D) {
                            new DelayedTask(40) {
                                public void run() {
                                    Entity entity2 = entity;
                                    Level level1 = entity2.level;

                                    if (!level1.isClientSide()) {
                                        Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                        projectile1.setOwner(entity2);
                                        projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                        projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.7F, 0.0F);
                                        level1.addFreshEntity(projectile1);
                                    }
                                }
                            };
                        }
                    }

                    entity.setSprinting(true);
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            entity.setSprinting(false);
                        }
                    };
                    LivingEntity livingentity2;

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());

                        itemstack.setCount(1);
                        livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity2 instanceof Player) {
                            Player player = (Player)livingentity2;

                            player.getInventory().setChanged();
                        }
                    }

                    ItemStack itemstack1;

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        itemstack1 = livingentity2.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.SHARPNESS, 5);
                    new DelayedTask(20) {
                        public void run() {
                            LivingEntity livingentity3;
                            ItemStack itemstack2;
                            Player player1;

                            if (Math.random() <= 0.4D) {
                                ItemStack itemstack3;

                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity)entity;
                                    itemstack3 = livingentity3.getOffhandItem();
                                } else {
                                    itemstack3 = ItemStack.EMPTY;
                                }

                                itemstack3.enchant(Enchantments.PIERCING, 5);
                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity)entity;
                                    itemstack3 = livingentity3.getOffhandItem();
                                } else {
                                    itemstack3 = ItemStack.EMPTY;
                                }

                                itemstack3.enchant(Enchantments.PUNCH_ARROWS, 2);
                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity)entity;
                                    itemstack2 = new ItemStack(Items.BOW);
                                    itemstack2.setCount(1);
                                    livingentity3.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                    if (livingentity3 instanceof Player) {
                                        player1 = (Player)livingentity3;
                                        player1.getInventory().setChanged();
                                    }
                                }
                            } else if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity) entity;
                                itemstack2 = new ItemStack(Items.LAVA_BUCKET);
                                itemstack2.setCount(1);
                                livingentity3.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                if (livingentity3 instanceof Player) {
                                    player1 = (Player) livingentity3;
                                    player1.getInventory().setChanged();
                                }
                            }

                            new DelayedTask(150) {
                                @Override
                                public void run() {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingentity4 = (LivingEntity)entity;
                                        ItemStack itemstack4 = new ItemStack(Items.ENDER_PEARL);

                                        itemstack4.setCount(1);
                                        livingentity4.setItemInHand(InteractionHand.OFF_HAND, itemstack4);
                                        if (livingentity4 instanceof Player) {
                                            Player player2 = (Player)livingentity4;

                                            player2.getInventory().setChanged();
                                        }
                                    }
                                }
                            };
                        }
                    };
                    if (Math.random() <= 0.3D && entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        if (!livingentity2.level.isClientSide()) {
                            livingentity2.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                        }
                    }

                    if (Math.random() <= 0.09D) {
                        entity.setYRot(0.0F);
                        entity.setXRot(180.0F);
                        entity.setYBodyRot(entity.getYRot());
                        entity.setYHeadRot(entity.getYRot());
                        entity.yRotO = entity.getYRot();
                        entity.xRotO = entity.getXRot();
                        if (entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity;
                            livingentity1.yBodyRotO = livingentity1.getYRot();
                            livingentity1.yHeadRotO = livingentity1.getYRot();
                        }

                        level = entity.level;
                        if (!level.isClientSide()) {
                            projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                            projectile.setOwner(entity);
                            projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                            projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                            level.addFreshEntity(projectile);
                        }
                    }

                    if (Math.random() <= 0.05D) {
                        new DelayedTask(20) {
                            public void run() {
                                Entity entity2 = entity;

                                entity2.setYRot(0.0F);
                                entity2.setXRot(90.0F);
                                entity2.setYBodyRot(entity2.getYRot());
                                entity2.setYHeadRot(entity2.getYRot());
                                entity2.yRotO = entity2.getYRot();
                                entity2.xRotO = entity2.getXRot();
                                if (entity2 instanceof LivingEntity) {
                                    LivingEntity livingentity3 = (LivingEntity)entity2;

                                    livingentity3.yBodyRotO = livingentity3.getYRot();
                                    livingentity3.yHeadRotO = livingentity3.getYRot();
                                }

                                entity2 = entity;
                                Level level1 = entity2.level;

                                if (!level1.isClientSide()) {
                                    Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                    projectile1.setOwner(entity2);
                                    projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.0F, 0.0F);
                                    level1.addFreshEntity(projectile1);
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
                                    LivingEntity livingentity3 = (LivingEntity)entity2;

                                    livingentity3.yBodyRotO = livingentity3.getYRot();
                                    livingentity3.yHeadRotO = livingentity3.getYRot();
                                }

                                entity2 = entity;
                                Level level1 = entity2.level;

                                if (!level1.isClientSide()) {
                                    Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                    projectile1.setOwner(entity2);
                                    projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                    projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 2.0F, 0.0F);
                                    level1.addFreshEntity(projectile1);
                                }

                                LivingEntity livingentity4;

                                if (entity instanceof LivingEntity) {
                                    livingentity4 = (LivingEntity)entity;
                                    ItemStack itemstack2 = new ItemStack(Items.BOW);

                                    itemstack2.setCount(1);
                                    livingentity4.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                    if (livingentity4 instanceof Player) {
                                        Player player1 = (Player)livingentity4;

                                        player1.getInventory().setChanged();
                                    }
                                }

                                ItemStack itemstack3;

                                if (entity instanceof LivingEntity) {
                                    livingentity4 = (LivingEntity)entity;
                                    itemstack3 = livingentity4.getMainHandItem();
                                } else {
                                    itemstack3 = ItemStack.EMPTY;
                                }

                                itemstack3.enchant(Enchantments.POWER_ARROWS, 3);
                                if (entity instanceof LivingEntity) {
                                    livingentity4 = (LivingEntity)entity;
                                    itemstack3 = livingentity4.getMainHandItem();
                                } else {
                                    itemstack3 = ItemStack.EMPTY;
                                }

                                itemstack3.enchant(Enchantments.PUNCH_ARROWS, 3);
                                new DelayedTask(80) {
                                    public void run() {
                                        Entity entity3 = entity;
                                        Level level2 = entity3.level;

                                        if (!level2.isClientSide()) {
                                            Projectile projectile2 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level2);
                                            projectile2.setOwner(entity3);
                                            projectile2.setPos(entity3.getX(), entity3.getEyeY() - 0.1D, entity3.getZ());
                                            projectile2.shoot(entity3.getLookAngle().x, entity3.getLookAngle().y, entity3.getLookAngle().z, 2.0F, 0.0F);
                                            level2.addFreshEntity(projectile2);
                                        }

                                        LivingEntity livingentity5;
                                        ItemStack itemstack4;
                                        Player player2;
                                        ItemStack itemstack5;

                                        if (Math.random() <= 0.2D) {
                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack4 = new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_BLADE.get());
                                                itemstack4.setCount(1);
                                                livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                                if (livingentity5 instanceof Player) {
                                                    player2 = (Player)livingentity5;
                                                    player2.getInventory().setChanged();
                                                }
                                            }

                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack5 = livingentity5.getMainHandItem();
                                            } else {
                                                itemstack5 = ItemStack.EMPTY;
                                            }

                                            itemstack5.enchant(Enchantments.SHARPNESS, 5);
                                        } else {
                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack4 = new ItemStack(Items.DIAMOND_SWORD);
                                                itemstack4.setCount(1);
                                                livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                                if (livingentity5 instanceof Player) {
                                                    player2 = (Player)livingentity5;
                                                    player2.getInventory().setChanged();
                                                }
                                            }

                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack5 = livingentity5.getMainHandItem();
                                            } else {
                                                itemstack5 = ItemStack.EMPTY;
                                            }

                                            itemstack5.enchant(Enchantments.SHARPNESS, 3);
                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack5 = livingentity5.getMainHandItem();
                                            } else {
                                                itemstack5 = ItemStack.EMPTY;
                                            }

                                            itemstack5.enchant(Enchantments.KNOCKBACK, 2);
                                            if (Math.random() <= 0.2D) {
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack4 = new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_DAGGER.get());
                                                    itemstack4.setCount(1);
                                                    livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                                    if (livingentity5 instanceof Player) {
                                                        player2 = (Player)livingentity5;
                                                        player2.getInventory().setChanged();
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack5 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack5 = ItemStack.EMPTY;
                                                }

                                                itemstack5.enchant(Enchantments.SHARPNESS, 3);
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack5 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack5 = ItemStack.EMPTY;
                                                }

                                                itemstack5.enchant(Enchantments.KNOCKBACK, 2);
                                            }

                                            if (Math.random() <= 0.1D) {
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack4 = new ItemStack(Items.DIAMOND_SWORD);
                                                    itemstack4.setCount(1);
                                                    livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                                    if (livingentity5 instanceof Player) {
                                                        player2 = (Player)livingentity5;
                                                        player2.getInventory().setChanged();
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack5 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack5 = ItemStack.EMPTY;
                                                }

                                                itemstack5.enchant(Enchantments.SHARPNESS, 3);
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack5 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack5 = ItemStack.EMPTY;
                                                }

                                                itemstack5.enchant(Enchantments.KNOCKBACK, 2);
                                            }
                                        }
                                    }
                                };
                            }
                        };
                    }

                    float f;

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        f = livingentity2.getHealth();
                    } else {
                        f = -1.0F;
                    }

                    if (f <= 7.0F && levelaccessor instanceof Level) {
                        level = (Level)levelaccessor;
                        if (!level.isClientSide()) {
                            level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.item.break")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.item.break")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        }
                    }
                }
            }

        }
    }
}
