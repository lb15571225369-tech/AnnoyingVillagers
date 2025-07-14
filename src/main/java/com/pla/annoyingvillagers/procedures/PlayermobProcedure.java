package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

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
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class PlayermobProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level, livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!entity1.getPersistentData().getBoolean("kick_x")) {
                LivingEntity livingentity;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity = mob.getTarget();
                } else {
                    livingentity = null;
                }

                if (entity1 == livingentity) {
                    LivingEntity livingentity1;
                    LivingEntity livingentity2;
                    Level level;
                    Projectile projectile;

                    if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("player_mobs:player_mob")) {
                        if (Math.random() <= 0.3D && entity instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity;
                            if (!livingentity1.level.isClientSide()) {
                                livingentity1.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.GEDANG.get(), 1, 1, false, false));
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
                                livingentity2 = (LivingEntity)entity;
                                livingentity2.yBodyRotO = livingentity2.getYRot();
                                livingentity2.yHeadRotO = livingentity2.getYRot();
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
                                @Override
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

                                @Override
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
                                        ItemStack itemstack = new ItemStack(Items.BOW);

                                        itemstack.setCount(1);
                                        livingentity4.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                                        if (livingentity4 instanceof Player) {
                                            Player player = (Player)livingentity4;

                                            player.getInventory().setChanged();
                                        }
                                    }

                                    ItemStack itemstack1;

                                    if (entity instanceof LivingEntity) {
                                        livingentity4 = (LivingEntity)entity;
                                        itemstack1 = livingentity4.getMainHandItem();
                                    } else {
                                        itemstack1 = ItemStack.EMPTY;
                                    }

                                    itemstack1.enchant(Enchantments.POWER_ARROWS, 5);
                                    new DelayedTask(20) {
                                        @Override
                                        public void run() {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity livingentity5 = (LivingEntity)entity;

                                                if (!livingentity5.level.isClientSide()) {
                                                    livingentity5.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2, false, false));
                                                }
                                            }
                                        }
                                    };

                                    if (entity instanceof LivingEntity) {
                                        livingentity4 = (LivingEntity)entity;
                                        itemstack1 = livingentity4.getMainHandItem();
                                    } else {
                                        itemstack1 = ItemStack.EMPTY;
                                    }

                                    itemstack1.enchant(Enchantments.PUNCH_ARROWS, 5);

                                    new DelayedTask(80) {
                                        @Override
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
                                            ItemStack itemstack2;
                                            Player player1;
                                            ItemStack itemstack3;

                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack2 = new ItemStack(Items.DIAMOND_SWORD);
                                                itemstack2.setCount(1);
                                                livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                                if (livingentity5 instanceof Player) {
                                                    player1 = (Player)livingentity5;
                                                    player1.getInventory().setChanged();
                                                }
                                            }

                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack3 = livingentity5.getMainHandItem();
                                            } else {
                                                itemstack3 = ItemStack.EMPTY;
                                            }

                                            itemstack3.enchant(Enchantments.SHARPNESS, 5);
                                            if (entity instanceof LivingEntity) {
                                                livingentity5 = (LivingEntity)entity;
                                                itemstack3 = livingentity5.getMainHandItem();
                                            } else {
                                                itemstack3 = ItemStack.EMPTY;
                                            }

                                            itemstack3.enchant(Enchantments.MENDING, 5);
                                            if (Math.random() <= 0.2D) {
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack2 = new ItemStack((ItemLike) AnnoyingVillagersModItems.ZUAN_SHI_CHANG_MAO.get());
                                                    itemstack2.setCount(1);
                                                    livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                                    if (livingentity5 instanceof Player) {
                                                        player1 = (Player)livingentity5;
                                                        player1.getInventory().setChanged();
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack3 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack3 = ItemStack.EMPTY;
                                                }

                                                itemstack3.enchant(Enchantments.SHARPNESS, 5);
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack3 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack3 = ItemStack.EMPTY;
                                                }

                                                itemstack3.enchant(Enchantments.MENDING, 5);
                                            }

                                            if (Math.random() <= 0.1D) {
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack2 = new ItemStack(Items.STONE_SWORD);
                                                    itemstack2.setCount(1);
                                                    livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                                    if (livingentity5 instanceof Player) {
                                                        player1 = (Player)livingentity5;
                                                        player1.getInventory().setChanged();
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack3 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack3 = ItemStack.EMPTY;
                                                }

                                                itemstack3.enchant(Enchantments.SHARPNESS, 5);
                                                if (entity instanceof LivingEntity) {
                                                    livingentity5 = (LivingEntity)entity;
                                                    itemstack3 = livingentity5.getMainHandItem();
                                                } else {
                                                    itemstack3 = ItemStack.EMPTY;
                                                }

                                                itemstack3.enchant(Enchantments.MENDING, 5);
                                            }
                                        }
                                    };
                                }
                            };
                        }
                    }
                }
            }

        }
    }
}

