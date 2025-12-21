package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.shelmarow.combat_evolution.ai.BehaviorUtils;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;

public class CombatCommon {
    public static boolean canJump(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().onGround() && !mobpatch.getOriginal().isPassenger();
    }

    public static boolean canAttackWhileNotHealing(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            return !pathfinderMobInventory.isHealing();
        }
        return false;
    }

    public static boolean canPerformEating(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            if (playerNpcEntity.getGapCooldown() > 0) {
                return false;
            }
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            if (pathfinderMobInventory instanceof SteveEntity steveEntity && steveEntity.getState() == 2) {
                return false;
            }
            if (pathfinderMobInventory.getGapCooldown() > 0) {
                return false;
            }
            return !pathfinderMobInventory.isHealing();
        }
        return false;
    }

    public static boolean canPerformGuarding(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            return !pathfinderMobInventory.isHealing();
        }
        return false;
    }

    public static boolean canThrowEnderPearl(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal().isPassenger()) return false;

        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            if (playerNpcEntity.isHealing()) {
                return false;
            }
            return playerNpcEntity.getEnderPearlCooldown() == 0;
        }
        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            if (pathfinderMobInventory.isHealing()) {
                return false;
            }
            return pathfinderMobInventory.getEnderPearlCooldown() == 0;
        }
        return false;
    }

    public static boolean canSwapToBow(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return playerNpcEntity.isUseBow() && playerNpcEntity.getSwapToBowCooldown() == 0;
        }

        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            if (pathfinderMobInventory instanceof SteveEntity steveEntity) {
                if (steveEntity.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING) || steveEntity.getState() == 2) return false;
            }
            return pathfinderMobInventory.isUseBow() && pathfinderMobInventory.getSwapToBowCooldown() == 0;
        }

        return false;
    }

    public static boolean canSwitchWeapon(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof SteveEntity steveEntity) {
            return steveEntity.getSwapWeaponCooldown() == 0 && steveEntity.getState() != 2
                    || (steveEntity.getState() == 0 && steveEntity.getHealth() <= 20 && !steveEntity.getMainHandItem().getItem().equals(Items.DIAMOND_SWORD));
        }

        return false;
    }

    public static void performEnderPearlToTarget(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        LivingEntity entity = mobpatch.getOriginal();

        double dx = target.getX() - entity.getX();
        double dz = target.getZ() - entity.getZ();
        double dy = target.getEyeY() - entity.getEyeY();
        double horiz = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, horiz) * (180F / (float) Math.PI)));

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);

        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setEnderPearlCooldown();
        }

        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setEnderPearlCooldown();
        }

        CombatBehaviour.throwEnderPearl(entity, 0.0F);
    }

    public static void performEnderPearlAway(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        LivingEntity entity = mobpatch.getOriginal();

        double dx = entity.getX() - target.getX();
        double dz = entity.getZ() - target.getZ();
        double horiz = Math.sqrt(dx * dx + dz * dz);

        if (horiz < 1.0E-3D) {
            CombatBehaviour.throwEnderPearl(entity, 0.0F);
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                playerNpcEntity.setEnderPearlCooldown();
            }
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                pathfinderMobInventory.setEnderPearlCooldown();
            }
            return;
        }

        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;

        float basePitch = -15.0F;
        float randomPitchOffset = (entity.getRandom().nextFloat() - 0.5F) * 10.0F;
        float randomYawOffset = (entity.getRandom().nextFloat() - 0.5F) * 30.0F;

        float pitch = basePitch + randomPitchOffset;
        yaw += randomYawOffset;

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);

        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setEnderPearlCooldown();
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setEnderPearlCooldown();
        }
        CombatBehaviour.throwEnderPearl(entity, 0.0F);
    }

    public static void performEatingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();

        BehaviorUtils.stopCurrentBehavior(entity);
        entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_APPLE));
        if (new Random().nextBoolean()) {
            CombatBehaviour.throwEnderPearl(entity, new Random().nextFloat(0.0F, 180.0F));
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                playerNpcEntity.setEnderPearlCooldown();
            }
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                pathfinderMobInventory.setEnderPearlCooldown();
            }
        }

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setGapCooldown();
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setGapCooldown();
        }

        CombatBehaviour.eatingGoldenApple(
                entity,
                entity.level(),
                20.0D
        );
    }

    public static void performDrinkingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();

        BehaviorUtils.stopCurrentBehavior(entity);
        if (!entity.level().isClientSide) {
            ItemStack stack = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.setGapCooldown();
        }

        CombatBehaviour.drinkingHealingPotion(
                entity,
                entity.level(),
                false,
                20.0D
        );
    }

    public static void swapToBow(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        BehaviorUtils.stopCurrentBehavior(entity);
        ItemStack bow = new ItemStack(Items.BOW);

        if (entity instanceof VillagerScoutCaptainEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.PUNCH_ARROWS, 1);
        }
        if (entity instanceof RedVillagerGeneralEntity) {
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
        }
        if (entity instanceof BlueVillagerGeneralEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
        }
        if (entity instanceof GreenVillagerGeneralEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (entity instanceof PurpleVillagerGeneralEntity) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }
        if (entity instanceof SteveEntity steveEntity && steveEntity.getState() == 1) {
            bow.enchant(Enchantments.POWER_ARROWS, 5);
            bow.enchant(Enchantments.PUNCH_ARROWS, 5);
        }
        if (entity instanceof AlexEntity alexEntity && alexEntity.getState() == 1) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 3);
            bow.enchant(Enchantments.POWER_ARROWS, 3);
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
        }
        if (entity instanceof ChrisEntity chrisEntity && chrisEntity.getState() == 1) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }

        entity.setItemInHand(InteractionHand.MAIN_HAND, bow.copy());
        entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);

        if (entity instanceof VillagerScoutEntity
                && !entity.level().isClientSide() && entity.level().getServer() != null) {
            Objects.requireNonNull(entity.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Fire!"), false);
        }
    }

    public static void switchWeapon(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        BehaviorUtils.stopCurrentBehavior(entity);
        if (entity instanceof SteveEntity steveEntity) {
            steveEntity.rollItem();
        }
    }

    public static void swapToMelee(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            ItemStack mainWeaponItem = playerNpcEntity.getMainWeaponItem();
            ItemStack offWeaponItem = playerNpcEntity.getOffWeaponItem();
            BehaviorUtils.stopCurrentBehavior(playerNpcEntity);
            if (!mainWeaponItem.isEmpty()) {
                playerNpcEntity.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
            }
            if (!offWeaponItem.isEmpty()) {
                playerNpcEntity.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
            }
            playerNpcEntity.setSwapToBowCooldown();
        }

        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            ItemStack mainWeaponItem = pathfinderMobInventory.getMainWeaponItem();
            ItemStack offWeaponItem = pathfinderMobInventory.getOffWeaponItem();
            BehaviorUtils.stopCurrentBehavior(pathfinderMobInventory);
            if (pathfinderMobInventory instanceof SteveEntity) {
                if (canSwitchWeapon(mobpatch)) {
                    switchWeapon(mobpatch);
                } else {
                    if (!mainWeaponItem.isEmpty() && !(mainWeaponItem.getItem() instanceof BowItem)) {
                        pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
                    }
                    if (!offWeaponItem.isEmpty()) {
                        pathfinderMobInventory.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
                    }
                }
            } else {
                if (!mainWeaponItem.isEmpty() && !(mainWeaponItem.getItem() instanceof BowItem)) {
                    pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
                } else if (pathfinderMobInventory instanceof VillagerScoutEntity villagerScoutEntity) {
                    villagerScoutEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
                }
                if (!offWeaponItem.isEmpty()) {
                    pathfinderMobInventory.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
                }
            }
            pathfinderMobInventory.setSwapToBowCooldown();
        }
    }

    public static void jump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.jump();
        }
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
            pathfinderMobInventory.jump();
        }
    }
}
