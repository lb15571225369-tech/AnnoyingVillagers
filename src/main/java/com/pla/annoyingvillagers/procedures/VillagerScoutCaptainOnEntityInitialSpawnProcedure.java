package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import java.util.List;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
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
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VillagerScoutCaptainOnEntityInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
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

            itemstack.enchant(Enchantments.SMITE, 3);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getMainHandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SHARPNESS, 3);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getOffhandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.UNBREAKING, 3);
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                itemstack = livingentity.getOffhandItem();
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.SHARPNESS, 3);
            if (Math.random() <= 0.7D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
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
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
            }

            if (Math.random() <= 0.2D) {
                new DelayedTask(1) {
                    public void run() {
                        Entity entity1 = entity;
                        Player player;
                        LivingEntity livingentity1;

                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            player.getInventory().armor.set(1, new ItemStack(Blocks.AIR));
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            livingentity1.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
                        }

                        entity1 = entity;
                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            player.getInventory().armor.set(2, new ItemStack(Items.DIAMOND_CHESTPLATE));
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            livingentity1.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
                        }

                        entity1 = entity;
                        if (entity1 instanceof Player) {
                            player = (Player)entity1;
                            player.getInventory().armor.set(0, new ItemStack(Items.DIAMOND_BOOTS));
                            player.getInventory().setChanged();
                        } else if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity)entity1;
                            livingentity1.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
                        }

                        LivingEntity livingentity2;
                        ItemStack itemstack1;

                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.CHEST);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
                        if (entity instanceof LivingEntity) {
                            livingentity2 = (LivingEntity)entity;
                            itemstack1 = livingentity2.getItemBySlot(EquipmentSlot.FEET);
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemstack1.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
                    }
                };
            }

            if (Math.random() <= 0.32D) {
                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    ItemStack itemstack1 = new ItemStack(Items.BOW);

                    itemstack1.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                    if (livingentity instanceof Player) {
                        Player player = (Player)livingentity;

                        player.getInventory().setChanged();
                    }
                }

                if (entity instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                itemstack.enchant(Enchantments.POWER_ARROWS, 3);
                if (levelaccessor instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)levelaccessor;
                    Sheep sheep = new Sheep(EntityType.SHEEP, serverlevel);

                    sheep.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (sheep instanceof Mob) {
                        Mob mob = (Mob)sheep;

                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(sheep.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    }

                    levelaccessor.addFreshEntity(sheep);
                }

                Comparator<Entity> compareDistOf = Comparator.comparingDouble((Entity e) -> e.distanceToSqr(d0, d1, d2));

                List<Sheep> nearbySheep = levelaccessor.getEntitiesOfClass(Sheep.class,
                        AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
                        sheep1 -> true);

                Sheep closestSheepToRide = nearbySheep.stream()
                        .sorted(compareDistOf)
                        .findFirst()
                        .orElse(null);

                if (closestSheepToRide != null) {
                    entity.startRiding(closestSheepToRide);
                }

                Entity entity1 = levelaccessor.getEntitiesOfClass(Sheep.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 3.0D, 3.0D, 3.0D),
                                sheep1 -> true).stream()
                        .sorted(compareDistOf)
                        .findFirst()
                        .orElse(null);

                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(Sheep.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 3.0D, 3.0D, 3.0D),
                                sheep -> true // or any condition you want
                        ).stream()
                        .sorted(compareDistOf)
                        .findFirst()
                        .orElse(null);
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1, false, false));
                    }
                }

                entity1 = levelaccessor.getEntitiesOfClass(Sheep.class,
                                AABB.ofSize(new Vec3(d0, d1, d2), 3.0D, 3.0D, 3.0D),
                                sheep -> true
                        ).stream()
                        .sorted(compareDistOf)
                        .findFirst()
                        .orElse(null);
                if (entity1 instanceof LivingEntity) {
                    livingentity = (LivingEntity)entity1;
                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 9, false, false));
                    }
                }
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add villagers",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team modify villagers friendlyFire false",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join villagers @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join villagers @e[type=minecraft:iron_golem]",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }
        }
    }
}
