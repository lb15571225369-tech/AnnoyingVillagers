package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChrisOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                LivingEntity livingentity;
                ItemStack itemstack;
                Player player;
                ItemStack itemstack1;

                if (Math.random() <= 0.05D) {
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack = new ItemStack(Items.DIAMOND_SWORD);
                        itemstack.setCount(1);
                        livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity instanceof Player) {
                            player = (Player)livingentity;
                            player.getInventory().setChanged();
                        }
                    }

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

                    itemstack1.enchant(Enchantments.SHARPNESS, 6);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack1 = livingentity.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.SWEEPING_EDGE, 4);
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack1 = livingentity.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.SHARPNESS, 4);
                }

                if (Math.random() <= 0.05D) {
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());
                        itemstack.setCount(1);
                        livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                        if (livingentity instanceof Player) {
                            player = (Player)livingentity;
                            player.getInventory().setChanged();
                        }
                    }

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
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity)entity;
                        itemstack1 = livingentity.getMainHandItem();
                    } else {
                        itemstack1 = ItemStack.EMPTY;
                    }

                    itemstack1.enchant(Enchantments.SHARPNESS, 4);
                }

                if (Math.random() <= 0.095D) {
                    new DelayedTask(20) {
                        public void run() {
                            Entity entity1 = entity;

                            entity1.setYRot(0.0F);
                            entity1.setXRot(180.0F);
                            entity1.setYBodyRot(entity1.getYRot());
                            entity1.setYHeadRot(entity1.getYRot());
                            entity1.yRotO = entity1.getYRot();
                            entity1.xRotO = entity1.getXRot();
                            if (entity1 instanceof LivingEntity) {
                                LivingEntity livingentity1 = (LivingEntity)entity1;

                                livingentity1.yBodyRotO = livingentity1.getYRot();
                                livingentity1.yHeadRotO = livingentity1.getYRot();
                            }

                            entity1 = entity;
                            Level level = entity1.level();

                            if (!level.isClientSide()) {
                                Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                                projectile.setOwner(entity1);
                                projectile.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                                projectile.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 2.0F, 0.0F);
                                level.addFreshEntity(projectile);
                            }

                            LivingEntity livingentity2;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                ItemStack itemstack2 = new ItemStack(Items.BOW);

                                itemstack2.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                if (livingentity2 instanceof Player) {
                                    Player player1 = (Player)livingentity2;

                                    player1.getInventory().setChanged();
                                }
                            }

                            ItemStack itemstack3;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                itemstack3 = livingentity2.getMainHandItem();
                            } else {
                                itemstack3 = ItemStack.EMPTY;
                            }

                            itemstack3.enchant(Enchantments.POWER_ARROWS, 5);
                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity)entity;
                                itemstack3 = livingentity2.getMainHandItem();
                            } else {
                                itemstack3 = ItemStack.EMPTY;
                            }

                            itemstack3.enchant(Enchantments.PUNCH_ARROWS, 5);
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingentity3 = (LivingEntity)entity;

                                        if (!livingentity3.level().isClientSide()) {
                                            livingentity3.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2, false, false));
                                        }
                                    }
                                }
                            };
                            new DelayedTask(80) {
                                public void run() {
                                    Entity entity2 = entity;
                                    Level level1 = entity2.level();

                                    if (!level1.isClientSide()) {
                                        Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                        projectile1.setOwner(entity2);
                                        projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1D, entity2.getZ());
                                        projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 2.0F, 0.0F);
                                        level1.addFreshEntity(projectile1);
                                    }

                                    LivingEntity livingentity3;

                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        ItemStack itemstack4 = new ItemStack(Items.DIAMOND_SWORD);

                                        itemstack4.setCount(1);
                                        livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                        if (livingentity3 instanceof Player) {
                                            Player player2 = (Player)livingentity3;

                                            player2.getInventory().setChanged();
                                        }
                                    }

                                    ItemStack itemstack5;

                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        itemstack5 = livingentity3.getMainHandItem();
                                    } else {
                                        itemstack5 = ItemStack.EMPTY;
                                    }

                                    itemstack5.enchant(Enchantments.SHARPNESS, 5);
                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity)entity;
                                        itemstack5 = livingentity3.getMainHandItem();
                                    } else {
                                        itemstack5 = ItemStack.EMPTY;
                                    }

                                    itemstack5.enchant(Enchantments.MENDING, 5);
                                }
                            };
                        }
                    };
                }

                if (Math.random() <= 0.07D) {
                    new DelayedTask(20) {
                        @Override
                        public void run() {
                            Entity entity1 = entity;
                            Level level = entity1.level();

                            if (!level.isClientSide()) {
                                Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                                projectile.setOwner(entity1);
                                projectile.setPos(entity1.getX(), entity1.getEyeY() - 0.1D, entity1.getZ());
                                projectile.shoot(entity1.getLookAngle().x, entity1.getLookAngle().y, entity1.getLookAngle().z, 1.0F, 0.0F);
                                level.addFreshEntity(projectile);
                            }
                        }
                    };
                }

                if (Math.random() <= 0.05D) {
                    entity.setYRot(0.0F);
                    entity.setXRot(180.0F);
                    entity.setYBodyRot(entity.getYRot());
                    entity.setYHeadRot(entity.getYRot());
                    entity.yRotO = entity.getYRot();
                    entity.xRotO = entity.getXRot();
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity;

                        livingentity1.yBodyRotO = livingentity1.getYRot();
                        livingentity1.yHeadRotO = livingentity1.getYRot();
                    }
                }

                entity.setSprinting(true);
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        entity.setSprinting(false);
                    }
                };
                if (Math.random() <= 0.38D) {
                    Vec3 vec3 = new Vec3(d0, d1, d2);
                    List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(45.0D), (entity1) -> {
                        return true;
                    }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                        return entity1.distanceToSqr(vec3);
                    })).collect(Collectors.toList());
                    Iterator iterator = list.iterator();

                    while(iterator.hasNext()) {
                        Entity entity1 = (Entity)iterator.next();
                        LivingEntity livingentity2;

                        if (entity instanceof Mob) {
                            Mob mob = (Mob)entity;

                            livingentity2 = mob.getTarget();
                        } else {
                            livingentity2 = null;
                        }

                        if (entity1 == livingentity2 && entity instanceof LivingEntity) {
                            LivingEntity livingentity3 = (LivingEntity)entity;

                            if (!livingentity3.level().isClientSide()) {
                                livingentity3.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                            }
                        }
                    }
                }

                if (Math.random() <= 0.32D && entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.TAOTUOBUFF.get(), 1, 1, false, false));
                    }
                }
            }

        }
    }
}
