package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.entity.VillagerScoutEntity;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.shelmarow.combat_evolution.ai.BehaviorUtils;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;

public class CombatCommon {
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
            return playerNpcEntity.getSwapToBowCooldown() == 0;
        }

        if (mobpatch.getOriginal() instanceof PathfinderMobInventory pathfinderMobInventory) {
            return pathfinderMobInventory.getSwapToBowCooldown() == 0;
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

    public static void swapToBow(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        BehaviorUtils.stopCurrentBehavior(entity);
        ItemStack bow = new ItemStack(Items.BOW);
        entity.setItemInHand(InteractionHand.MAIN_HAND, bow.copy());
        entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);

        if (entity instanceof VillagerScoutEntity
                && !entity.level().isClientSide() && entity.level().getServer() != null) {
            Objects.requireNonNull(entity.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Fire!"), false);
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
            if (!mainWeaponItem.isEmpty() && !(mainWeaponItem.getItem() instanceof BowItem)) {
                pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
            } else if (pathfinderMobInventory instanceof VillagerScoutEntity villagerScoutEntity) {
                villagerScoutEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
            }
            if (!offWeaponItem.isEmpty()) {
                pathfinderMobInventory.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
            }
            pathfinderMobInventory.setSwapToBowCooldown();
        }
    }
}
