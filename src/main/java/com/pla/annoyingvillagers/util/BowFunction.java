package com.pla.annoyingvillagers.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BowFunction {
    public static void bowShoot(LivingEntityPatch<?> livingEntityPatch) {
        LivingEntity shooter = livingEntityPatch.getOriginal();
        Level level = shooter.level();

        if (level.isClientSide) return;

        ItemStack bowStack = shooter.getItemInHand(InteractionHand.MAIN_HAND);
        if (!(bowStack.getItem() instanceof BowItem bowItem)) {
            return;
        }

        if (shooter instanceof Player && !BowFunction.hasArrowOrInfinity(shooter, bowStack)) {
            return;
        }

        if (!bowStack.isEmpty() && bowStack.getTag() != null) {
            bowStack.getTag().putFloat("Pulling", 0.65F);
        }

        ItemStack arrowStack = ItemStack.EMPTY;
        boolean creativeOrInfinity = false;

        if (shooter instanceof Player player) {
            arrowStack = player.getProjectile(bowStack);
            creativeOrInfinity = player.getAbilities().instabuild ||
                    EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack) > 0;
        } else {
            arrowStack = new ItemStack(Items.ARROW);
            creativeOrInfinity = true;
        }

        if (arrowStack.isEmpty() && !creativeOrInfinity) {
            return;
        }

        if (arrowStack.isEmpty()) {
            arrowStack = new ItemStack(Items.ARROW);
        }

        int charge = BowItem.MAX_DRAW_DURATION;
        float power = BowItem.getPowerForTime(charge);
        if (power < 0.1F) {
            return;
        }

        ArrowItem arrowItem = arrowStack.getItem() instanceof ArrowItem ai ? ai : (ArrowItem) Items.ARROW;
        AbstractArrow abstractArrow = arrowItem.createArrow(level, arrowStack, shooter);
        abstractArrow = bowItem.customArrow(abstractArrow);

        float xRot;
        float yRot;

        if (!(shooter instanceof Player)) {
            LivingEntity target = livingEntityPatch.getTarget();

            if (target != null && target.isAlive()) {
                double dx = target.getX() - shooter.getX();
                double dz = target.getZ() - shooter.getZ();
                double dy = target.getEyeY() - shooter.getEyeY();
                double horiz = Math.sqrt(dx * dx + dz * dz);

                yRot = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                xRot = (float) (-(Mth.atan2(dy, horiz) * (180F / Math.PI)));

                xRot = Mth.clamp(xRot, -89.9F, 89.9F);

                shooter.setYRot(yRot);
                shooter.setXRot(xRot);
                shooter.setYBodyRot(yRot);
                shooter.setYHeadRot(yRot);
            } else {
                xRot = shooter.getXRot();
                yRot = shooter.getYRot();
            }
        } else {
            xRot = shooter.getXRot();
            yRot = shooter.getYRot();
        }
        abstractArrow.shootFromRotation(shooter, xRot, yRot, 0.0F, power * 3.0F, 1.0F);

        if (!bowStack.isEmpty() && bowStack.getTag() != null) {
            bowStack.getTag().remove("Pulling");
        }


        if (power == 1.0F) {
            abstractArrow.setCritArrow(true);
        }

        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bowStack);
        if (powerLevel > 0) {
            abstractArrow.setBaseDamage(
                    abstractArrow.getBaseDamage() + powerLevel * 0.5D + 0.5D
            );
        }

        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bowStack);
        if (punchLevel > 0) {
            abstractArrow.setKnockback(punchLevel);
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bowStack) > 0) {
            abstractArrow.setSecondsOnFire(100);
        }

        level.addFreshEntity(abstractArrow);
        level.playSound(
                null,
                shooter.getX(), shooter.getY(), shooter.getZ(),
                SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
        );

        if (shooter instanceof Player player && !player.getAbilities().instabuild) {
            boolean infiniteArrow = creativeOrInfinity ||
                    (arrowItem.isInfinite(arrowStack, bowStack, player)
                            && arrowStack.is(Items.ARROW));

            if (!infiniteArrow) {
                arrowStack.shrink(1);
                if (arrowStack.isEmpty()) {
                    player.getInventory().removeItem(arrowStack);
                }
            }

            bowStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            player.awardStat(Stats.ITEM_USED.get(bowItem));
        }
    }

    public static boolean hasArrowOrInfinity(LivingEntity entity, ItemStack bowStack) {
        if (!(entity instanceof Player player)) {
            return true;
        } else if (player.getAbilities().instabuild) {
            return true;
        }

        if (!(bowStack.getItem() instanceof BowItem)) {
            return false;
        }
        int infinityLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack);
        boolean hasInfinity = infinityLevel > 0;

        ItemStack projectile = player.getProjectile(bowStack);
        boolean hasArrow = !projectile.isEmpty();

        return hasArrow || hasInfinity;
    }
}