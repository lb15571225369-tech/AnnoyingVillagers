package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.Random;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LanCunQiShiTiChuShiShengChengShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("av_npc", true);
            LivingEntity livingentity;
            ItemStack itemstack;
            Player player;

            if (Math.random() < 0.2D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.LAN_CUN_QI_JIAN.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player)livingentity;
                    player.getInventory().setChanged();
                }
            }

            if (Math.random() < 0.2D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.ZUAN_SHI_DAO_PIAN.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player)livingentity;
                    player.getInventory().setChanged();
                }
            }

            if (Math.random() < 0.2D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.ZUAN_SHI_BI_SHOU.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player)livingentity;
                    player.getInventory().setChanged();
                }
            }

            if (Math.random() < 0.2D && entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = new ItemStack(Items.DIAMOND_SWORD);
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player)livingentity;
                    player.getInventory().setChanged();
                }
            }

            ItemStack itemstack1;

            if (Math.random() < 0.8D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.UNBREAKING, 2);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getMainHandItem();
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.SHARPNESS, Mth.nextInt(new Random(), 1, 4));
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Mth.nextInt(new Random(), 1, 4));
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Mth.nextInt(new Random(), 1, 4));
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Mth.nextInt(new Random(), 1, 4));
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Mth.nextInt(new Random(), 1, 4));
            }

            if (Math.random() <= 0.1D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = new ItemStack(Items.BOW);
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

                itemstack1.enchant(Enchantments.POWER_ARROWS, Mth.nextInt(new Random(), 1, 4));
            }

            ServerLevel serverlevel;
            Mob mob;
            Entity entity1;

            if (Math.random() <= 0.25D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = new ItemStack(Items.BOW);
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

                itemstack1.enchant(Enchantments.POWER_ARROWS, Mth.nextInt(new Random(), 1, 4));
                if (levelaccessor instanceof ServerLevel) {
                    serverlevel = (ServerLevel)levelaccessor;
                    Cow cow = new Cow(EntityType.COW, serverlevel);

                    cow.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (cow instanceof Mob) {
                        mob = (Mob)cow;
                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(cow.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    levelaccessor.addFreshEntity(cow);
                }

                Entity nearestCow = levelaccessor.getEntitiesOfClass(Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                cow -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(cow -> cow.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (nearestCow != null) {
                    entity.startRiding(nearestCow);
                }

                entity1 = levelaccessor.getEntitiesOfClass(Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                cow -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(cow -> cow.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                cow -> true) // or use specific filter if needed
                        .stream()
                        .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                cow -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(entity2 -> entity2.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1, false, false));
                    }
                }
            }

            if (Math.random() <= 0.1D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = new ItemStack(Items.BOW);
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

                itemstack1.enchant(Enchantments.POWER_ARROWS, Mth.nextInt(new Random(), 1, 4));
                if (levelaccessor instanceof ServerLevel) {
                    serverlevel = (ServerLevel)levelaccessor;
                    PolarBear polarbear = new PolarBear(EntityType.POLAR_BEAR, serverlevel);

                    polarbear.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (polarbear instanceof Mob) {
                        mob = (Mob)polarbear;
                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(polarbear.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    levelaccessor.addFreshEntity(polarbear);
                }

                entity.startRiding(
                        levelaccessor.getEntitiesOfClass(PolarBear.class,
                                        AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                        polarbear -> true)
                                .stream()
                                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                                .findFirst()
                                .orElse(null)
                );

                entity1 = levelaccessor.getEntitiesOfClass(PolarBear.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                polarbear -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(PolarBear.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                polarbear1 -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(PolarBear.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                                polarBear -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1, false, false));
                    }
                }
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team add villagers");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team modify villagers friendlyFire false");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team join villagers @s");
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team join villagers @e[type=minecraft:iron_golem]");
            }

        }
    }
}

