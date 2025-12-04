package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.shelmarow.combat_evolution.ai.BehaviorUtils;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Random;

public class PlayerNpcCommon {
    public static boolean canAttackWhileNotHealing(MobPatch<?> mobpatch) {
        PlayerNpcEntity playerNpcEntity = (PlayerNpcEntity) mobpatch.getOriginal();
        return !playerNpcEntity.isHealing();
    }

    public static boolean canPerformEating(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return false;
        }
        if (playerNpcEntity.getGapCooldown() > 0) {
            return false;
        }
        return !playerNpcEntity.isHealing();
    }

    public static boolean canPerformGuarding(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return false;
        }
        return !playerNpcEntity.isHealing();
    }

    public static boolean canThrowEnderPearl(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return false;
        }
        LivingEntity target = mobpatch.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (playerNpcEntity.isHealing()) {
            return false;
        }
        return playerNpcEntity.getEnderPearlCooldown() == 0;
    }

    public static boolean canSwapToBow(MobPatch<?> mobpatch) {
        if (!(mobpatch.getOriginal() instanceof PlayerNpcEntity npc)) {
            return false;
        }

        if (npc.getSwapToBowCooldown() > 0) {
            return false;
        }

        LivingEntity target = mobpatch.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        return true;
    }

    public static void performEnderPearlToTarget(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }
        LivingEntity target = mobpatch.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        double dx = target.getX() - playerNpcEntity.getX();
        double dz = target.getZ() - playerNpcEntity.getZ();
        double dy = target.getEyeY() - playerNpcEntity.getEyeY();
        double horiz = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float)(Mth.atan2(dz, dx) * (180F / (float)Math.PI)) - 90.0F;
        float pitch = (float)(-(Mth.atan2(dy, horiz) * (180F / (float)Math.PI)));

        playerNpcEntity.setYRot(yaw);
        playerNpcEntity.setXRot(pitch);
        playerNpcEntity.setYBodyRot(yaw);
        playerNpcEntity.setYHeadRot(yaw);

        playerNpcEntity.yRotO = yaw;
        playerNpcEntity.xRotO = pitch;
        playerNpcEntity.yBodyRotO = yaw;
        playerNpcEntity.yHeadRotO = yaw;

        playerNpcEntity.setEnderPearlCooldown();
        CombatBehaviour.throwEnderPearl(playerNpcEntity, 0.0F);
    }

    public static void performEnderPearlAway(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }

        LivingEntity target = mobpatch.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        double dx = playerNpcEntity.getX() - target.getX();
        double dz = playerNpcEntity.getZ() - target.getZ();
        double horiz = Math.sqrt(dx * dx + dz * dz);

        if (horiz < 1.0E-3D) {
            CombatBehaviour.throwEnderPearl(playerNpcEntity, 0.0F);
            playerNpcEntity.setEnderPearlCooldown();
            return;
        }

        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;

        float basePitch = -15.0F;
        float randomPitchOffset = (playerNpcEntity.getRandom().nextFloat() - 0.5F) * 10.0F;
        float randomYawOffset   = (playerNpcEntity.getRandom().nextFloat() - 0.5F) * 30.0F;

        float pitch = basePitch + randomPitchOffset;
        yaw += randomYawOffset;

        playerNpcEntity.setYRot(yaw);
        playerNpcEntity.setXRot(pitch);
        playerNpcEntity.setYBodyRot(yaw);
        playerNpcEntity.setYHeadRot(yaw);

        playerNpcEntity.yRotO = yaw;
        playerNpcEntity.xRotO = pitch;
        playerNpcEntity.yBodyRotO = yaw;
        playerNpcEntity.yHeadRotO = yaw;

        playerNpcEntity.setEnderPearlCooldown();
        CombatBehaviour.throwEnderPearl(playerNpcEntity, 0.0F);
    }

    public static void performEatingAnimation(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }
        BehaviorUtils.stopCurrentBehavior(playerNpcEntity);
        playerNpcEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_APPLE));
        if (new Random().nextBoolean()) {
            CombatBehaviour.throwEnderPearl(entity, new Random().nextFloat(0.0F, 180.0F));
            playerNpcEntity.setEnderPearlCooldown();
        }
        playerNpcEntity.setGapCooldown();
        CombatBehaviour.eatingGoldenApple(
                playerNpcEntity,
                playerNpcEntity.level(),
                20.0D
        );
    }

    public static void swapToBow(MobPatch<?> mobpatch) {
        if (!(mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }

        BehaviorUtils.stopCurrentBehavior(playerNpcEntity);
        ItemStack bow = new ItemStack(Items.BOW);
        playerNpcEntity.setItemInHand(InteractionHand.MAIN_HAND, bow.copy());
        playerNpcEntity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
    }

    public static void swapToMelee(MobPatch<?> mobpatch) {
        if (!(mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }
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


}
