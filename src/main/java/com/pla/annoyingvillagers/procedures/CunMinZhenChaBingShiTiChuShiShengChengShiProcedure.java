package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CunMinZhenChaBingShiTiChuShiShengChengShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("av_npc", true);
            LivingEntity livingentity;
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 1);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SHARPNESS, 2);
            if (Math.random() <= 0.4D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
            }

            ItemStack itemstack1;
            Player player;

            if (Math.random() <= 0.2D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = new ItemStack(Items.BOW);
                    itemstack1.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity instanceof Player) {
                        player = (Player)livingentity;
                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.POWER_ARROWS, 4);
            }

            ServerLevel serverlevel;
            Mob mob;
            Entity entity1;

            if (Math.random() <= 0.15D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = new ItemStack(Items.BOW);
                    itemstack1.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity instanceof Player) {
                        player = (Player)livingentity;
                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.POWER_ARROWS, 4);
                if (levelaccessor instanceof ServerLevel) {
                    serverlevel = (ServerLevel)levelaccessor;
                    Sheep sheep = new Sheep(EntityType.SHEEP, serverlevel);

                    sheep.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (sheep instanceof Mob) {
                        mob = (Mob)sheep;
                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(sheep.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    levelaccessor.addFreshEntity(sheep);
                }

                Entity nearestSheep = levelaccessor.getEntitiesOfClass(Sheep.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                sheep -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (nearestSheep != null) {
                    entity.startRiding(nearestSheep);

                    // Apply damage resistance to the sheep
                    if (nearestSheep instanceof LivingEntity livingSheep && !livingSheep.level.isClientSide()) {
                        livingSheep.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(Sheep.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                sheep -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity livingEntity && !livingEntity.level.isClientSide()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
                }
            }

            if (Math.random() <= 0.1D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = new ItemStack(Items.BOW);
                    itemstack1.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity instanceof Player) {
                        player = (Player)livingentity;
                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.POWER_ARROWS, 4);
                if (levelaccessor instanceof ServerLevel) {
                    serverlevel = (ServerLevel)levelaccessor;
                    Chicken chicken = new Chicken(EntityType.CHICKEN, serverlevel);

                    chicken.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (chicken instanceof Mob) {
                        mob = (Mob)chicken;
                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(chicken.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    levelaccessor.addFreshEntity(chicken);
                }

                Entity nearestChicken = levelaccessor.getEntitiesOfClass(Chicken.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                chicken -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (nearestChicken != null) {
                    entity.startRiding(nearestChicken);

                    // Apply resistance effect to the chicken
                    if (nearestChicken instanceof LivingEntity livingChicken && !livingChicken.level.isClientSide()) {
                        livingChicken.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(
                                Chicken.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                chicken -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(entity2 -> entity2.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
                    }
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                livingentity.removeEffect(MobEffects.REGENERATION);
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
