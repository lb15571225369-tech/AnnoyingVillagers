package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
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
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RedVillageGeneralOnEntityInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            LivingEntity livingentity;
            ItemStack itemstack;
            Player player;
            ItemStack itemstack1;

            if (Math.random() < 0.2D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
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

                itemstack1.enchant(Enchantments.SHARPNESS, 4);
            }

            if (Math.random() < 0.2D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_BLADE.get());
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

                itemstack1.enchant(Enchantments.SHARPNESS, 4);
            }

            if (Math.random() < 0.2D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = new ItemStack((ItemLike)AnnoyingVillagersModItems.DIAMOND_DAGGER.get());
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

                itemstack1.enchant(Enchantments.SHARPNESS, 4);
            }

            if (Math.random() < 0.2D) {
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

                itemstack1.enchant(Enchantments.SHARPNESS, 4);
            }

            if (Math.random() <= 0.4D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack1 = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack1 = ItemStack.EMPTY;
                }

                itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
            }

            if (Math.random() <= 0.12D) {
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

                itemstack1.enchant(Enchantments.SHARPNESS, 4);
                if (levelaccessor instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)levelaccessor;
                    Cow cow = new Cow(EntityType.COW, serverlevel);

                    cow.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (cow instanceof Mob) {
                        Mob mob = (Mob)cow;

                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(cow.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    levelaccessor.addFreshEntity(cow);
                }

                entity.startRiding(
                        levelaccessor.getEntitiesOfClass(
                                Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                cow -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null)
                );
                Entity entity1 = levelaccessor.getEntitiesOfClass(
                                Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                cow -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(
                                Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                cow -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1, false, false));
                    }
                }
                entity1 = levelaccessor.getEntitiesOfClass(
                                Cow.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 4.0D, 4.0D, 4.0D),
                                cow -> true
                        ).stream()
                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level().isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
                    }
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add villagers",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team modify villagers friendlyFire false",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join villagers @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join villagers @e[type=minecraft:iron_golem]",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }
        }
    }
}
