package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlueDemonTridentOnRangedItemUseProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() == AnnoyingVillagersModItems.BLUEDEMONTRIDENT.get()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    itemstack = livingentity1.getOffhandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() == AnnoyingVillagersModItems.BLUEDEMONTRIDENT.get() && entity.isShiftKeyDown()) {
                    Vec3 vec3 = new Vec3(d0, d1, d2);
                    List<Entity> list = (List) levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(4.0D), (entity1) -> {
                        return true;
                    }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                        return entity1.distanceToSqr(vec3);
                    })).collect(Collectors.toList());
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        Entity entity1 = (Entity) iterator.next();
                        boolean flag;

                        if (entity1 instanceof LivingEntity) {
                            LivingEntity livingentity2 = (LivingEntity) entity1;

                            flag = livingentity2.getMobType() == MobType.UNDEFINED;
                        } else {
                            flag = false;
                        }

                        if (flag) {
                            if (entity1 instanceof LivingEntity) {
                                LivingEntity livingentity3 = (LivingEntity) entity1;

                                flag = livingentity3.getMobType() == MobType.UNDEAD;
                            } else {
                                flag = false;
                            }

                            if (flag) {
                                if (entity1 instanceof LivingEntity) {
                                    LivingEntity livingentity4 = (LivingEntity) entity1;

                                    flag = livingentity4.getMobType() == MobType.ILLAGER;
                                } else {
                                    flag = false;
                                }

                                if (flag) {
                                    entity1.hurt(DamageSource.GENERIC, 3.0F);
                                    entity1.hurt(DamageSource.ON_FIRE, 1.0F);
                                    entity1.hurt(DamageSource.LIGHTNING_BOLT, 1.0F);
                                }
                            }
                        }
                    }

                    ServerLevel serverlevel;
                    LightningBolt lightningbolt;

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double) Mth.nextInt(new Random(), 30, 50), d1, d2)));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 - (double) Mth.nextInt(new Random(), 30, 50), d1, d2)));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double) Mth.nextInt(new Random(), 15, 20), d1, d2)));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 - (double) Mth.nextInt(new Random(), 15, 20), d1, d2)));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2 + (double) Mth.nextInt(new Random(), 30, 50))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2 + (double) Mth.nextInt(new Random(), 15, 20))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2 - (double) Mth.nextInt(new Random(), 30, 50))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0, d1, d2 - (double) Mth.nextInt(new Random(), 15, 20))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double) Mth.nextInt(new Random(), 30, 50), d1, d2 + (double) Mth.nextInt(new Random(), 30, 50))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double) Mth.nextInt(new Random(), 20, 30), d1, d2 + (double) Mth.nextInt(new Random(), 20, 30))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 - (double) Mth.nextInt(new Random(), 30, 50), d1, d2 - (double) Mth.nextInt(new Random(), 30, 50))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 - (double) Mth.nextInt(new Random(), 20, 30), d1, d2 - (double) Mth.nextInt(new Random(), 20, 30))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double) Mth.nextInt(new Random(), 30, 50), d1, d2 - (double) Mth.nextInt(new Random(), 30, 50))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 + (double) Mth.nextInt(new Random(), 20, 30), d1, d2 - (double) Mth.nextInt(new Random(), 20, 30))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 - (double) Mth.nextInt(new Random(), 30, 50), d1, d2 + (double) Mth.nextInt(new Random(), 30, 50))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (levelaccessor instanceof ServerLevel) {
                        serverlevel = (ServerLevel) levelaccessor;
                        lightningbolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(serverlevel);
                        lightningbolt.moveTo(Vec3.atBottomCenterOf(new BlockPos(d0 - (double) Mth.nextInt(new Random(), 20, 30), d1, d2 + (double) Mth.nextInt(new Random(), 20, 30))));
                        lightningbolt.setVisualOnly(false);
                        serverlevel.addFreshEntity(lightningbolt);
                    }

                    if (entity instanceof Player) {
                        Player player = (Player) entity;

                        player.getCooldowns().addCooldown((Item) AnnoyingVillagersModItems.BLUEDEMONTRIDENT.get(), 200);
                    }
                }
            }

        }
    }
}
